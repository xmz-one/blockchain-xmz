package xmz.block.chain.blockchainxmz.core;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmz.block.chain.blockchainxmz.block.po.BlockPO;
import xmz.block.chain.blockchainxmz.block.service.BlockService;
import xmz.block.chain.blockchainxmz.calculation.CalculationService;
import xmz.block.chain.blockchainxmz.common.crypto.Keys;
import xmz.block.chain.blockchainxmz.common.crypto.Sign;
import xmz.block.chain.blockchainxmz.common.enums.TransactionStatusEnum;
import xmz.block.chain.blockchainxmz.common.utils.Numeric;
import xmz.block.chain.blockchainxmz.config.XMZConfig;
import xmz.block.chain.blockchainxmz.dbs.BlockDBs;
import xmz.block.chain.blockchainxmz.dbs.TransactionDBs;
import xmz.block.chain.blockchainxmz.dbs.AccountDBs;
import xmz.block.chain.blockchainxmz.transaction.po.TransactionPO;
import xmz.block.chain.blockchainxmz.transaction.service.TransactionService;
import xmz.block.chain.blockchainxmz.wallet.po.AccountPO;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 交易执行器
 */
@Component
public class TransactionExecutor {
    static Logger logger = LoggerFactory.getLogger(TransactionExecutor.class);
    @Autowired
    private AccountDBs accountDB;
    @Autowired
    private BlockDBs blockDB;
    @Autowired
    private TransactionDBs transactionDB;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CalculationService calculationService;
    /**
     * 执行区块中的交易
     *
     * @param blockPO
     */
    public void runs(BlockPO blockPO) throws Exception {
        BigDecimal rate=BigDecimal.ZERO;
        long transactionIndex=0;
        for (TransactionPO transaction : blockPO.getBody().getTransactions()) {
            transactionIndex=transactionIndex+1;
            transaction.setTransactionIndex(transactionIndex);
            transaction.setBlockNumber(blockPO.getHeader().getIndex());
            transaction.setBlockTxHash(blockPO.getHeader().getHash());
            transaction.setNonce(blockPO.getHeader().getNonce());
            Optional<AccountPO> recipient = accountDB.getAccount(transaction.getTo());
            if (!recipient.isPresent()) {
                recipient = Optional.of(new AccountPO(transaction.getTo(), BigDecimal.ZERO));
            }
            if (transaction.getFrom().equals("coinBase")) {
                transaction.setStatus(TransactionStatusEnum.SUCCESS);
                rate=rate.add(transaction.getRate());
                transaction.setTimestamp(System.currentTimeMillis());
                recipient.get().setBalance(recipient.get().getBalance().add(transaction.getAmount()));
                accountDB.putAccount(recipient.get());
                continue;
            }
            Optional<AccountPO> sender = accountDB.getAccount(transaction.getFrom());
            boolean verify = Sign.verify(
                    Numeric.hexStringToByteArray(XMZConfig.SIGNATURE_VERIFICATION_VECTOR + transaction.getPublicKey()),
                    transaction.getSign(),
                    transaction.toSignString());
            if (!verify) {
                transaction.setStatus(TransactionStatusEnum.FAIL);
                transaction.setTimestamp(System.currentTimeMillis());
                transaction.setErrorMessage("Transaction signature error");
                continue;
            }
            if (sender.get().getBalance().compareTo(transaction.getAmount().add(transaction.getRate())) == -1) {
                transaction.setStatus(TransactionStatusEnum.FAIL);
                transaction.setTimestamp(System.currentTimeMillis());
                transaction.setErrorMessage("Insufficient account balance");
                continue;
            }
            transaction.setTimestamp(System.currentTimeMillis());
            transaction.setStatus(TransactionStatusEnum.SUCCESS);
            rate=rate.add(transaction.getRate());
            sender.get().setBalance(sender.get().getBalance().subtract(transaction.getAmount().add(transaction.getRate())));
            recipient.get().setBalance(recipient.get().getBalance().add(transaction.getAmount()));
            accountDB.putAccount(sender.get());
            accountDB.putAccount(recipient.get());
        }
        Optional<AccountPO> recipient = accountDB.getAccount(XMZConfig.BLACK_HOLE_ACCOUNT);
        if (!recipient.isPresent()) {
            recipient = Optional.of(new AccountPO(XMZConfig.BLACK_HOLE_ACCOUNT, BigDecimal.ZERO));
        }
        recipient.get().setBalance(recipient.get().getBalance().add(rate));
        accountDB.putAccount(recipient.get());
        blockPO.getHeader().setRate(rate);
        blockPO.getHeader().setTransactions(blockPO.getBody().getTransactions().size());
        //在创建线程的方式下无法使用
//        blockDB.putBlockLast(blockPO);
        blockDB.putBlockTxIndex(blockPO);
        calculation(blockPO);
        logger.info("Find a update Block, {}", blockPO);
    }


    /**
     * 进行订单各类数据统计
     * @param blockPO
     */
    public void calculation(BlockPO blockPO){
        XMZConfig.TRANSACTION_RATE=XMZConfig.TRANSACTION_LOW_RATE.add(XMZConfig.TRANSACTION_SPREAD_RATE.multiply(new BigDecimal((blockPO.getBody().getTransactions().size()-1)+"")));
        for (TransactionPO transaction : blockPO.getBody().getTransactions()) {
            transactionDB.putNewestTransactionList(transaction);
            if (transaction.getFrom().equals("coinBase")) {
                calculationService.updateTOTAL_MINIER_NUM(transaction.getAmount());
                transactionService.putAccountTransaction(transaction.getTo(),transaction);
                transactionDB.putTransaction(transaction);
            }else{
                transactionService.putAccountTransaction(transaction.getTo(),transaction);
                transactionService.putAccountTransaction(transaction.getFrom(),transaction);
                transactionDB.putTransaction(transaction);
            }
        }
        calculationService.updateTOTAL_TRANSACTION_NUM(new BigInteger(blockPO.getBody().getTransactions().size()+""));
    }

}
