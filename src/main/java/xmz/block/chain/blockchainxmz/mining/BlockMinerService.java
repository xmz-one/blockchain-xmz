package xmz.block.chain.blockchainxmz.mining;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmz.block.chain.blockchainxmz.block.po.BlockBody;
import xmz.block.chain.blockchainxmz.block.po.BlockHeader;
import xmz.block.chain.blockchainxmz.block.po.BlockPO;
import xmz.block.chain.blockchainxmz.config.XMZConfig;
import xmz.block.chain.blockchainxmz.common.enums.TransactionStatusEnum;
import xmz.block.chain.blockchainxmz.dbs.AccountDBs;
import xmz.block.chain.blockchainxmz.mining.pow.PowResult;
import xmz.block.chain.blockchainxmz.mining.pow.ProofOfWork;
import xmz.block.chain.blockchainxmz.transaction.po.TransactionPO;
import xmz.block.chain.blockchainxmz.wallet.po.AccountPO;

import com.google.common.base.Optional;

import java.math.BigDecimal;

@Service
public class BlockMinerService implements BlockMinerServiceImpl {

    @Autowired
    private AccountDBs accountDB;

    static Logger logger = LoggerFactory.getLogger(BlockMinerService.class);


    @Override
    public BlockPO createBlock(Optional<BlockPO> blockPO) {
        //获取挖矿账户
        Optional<AccountPO> minierAccount=accountDB.getMinierAccount();
        if(!minierAccount.isPresent()){
            logger.error("挖矿账户不存在,请先创建挖矿账户");
            return null;
        }
        BlockPO newblock;
        if(blockPO.isPresent()){
            BlockPO prev = blockPO.get();
            BlockHeader header = new BlockHeader(prev.getHeader().getIndex()+1, prev.getHeader().getHash());
            BlockBody body = new BlockBody();
            newblock = new BlockPO(header, body);
        }else{
            newblock = createGenesisBlock();
        }
        TransactionPO transactionPO=new TransactionPO();
        AccountPO accountPO=minierAccount.get();
        transactionPO.setFrom("coinBase");
        transactionPO.setTo(accountPO.getAddress());
        transactionPO.setAmount(XMZConfig.MINING_REWARD.setScale(0, BigDecimal.ROUND_DOWN));
        transactionPO.setData("Miner Reward.");
        transactionPO.setStatus(TransactionStatusEnum.APPENDING);
        transactionPO.setRate(XMZConfig.TRANSACTION_RATE.setScale(0, BigDecimal.ROUND_DOWN));
        transactionPO.setTimestamp(System.currentTimeMillis());
        transactionPO.setTxHash(transactionPO.hash());
        transactionPO.setInput("0x");
        transactionPO.setLowRate(XMZConfig.TRANSACTION_LOW_RATE.setScale(0, BigDecimal.ROUND_DOWN));
        newblock.getHeader().setMinierAddress(transactionPO.getTo());
        newblock.getHeader().setBlockAward(transactionPO.getAmount());
        if(blockPO.isPresent()){
            ProofOfWork proofOfWork = ProofOfWork.newProofOfWork(newblock);
            PowResult result = proofOfWork.run();
            newblock.getHeader().setDifficulty(result.getTarget());
            newblock.getHeader().setNonce(result.getNonce());
            newblock.getHeader().setHash(result.getHash());
        }
        newblock.getBody().addTransaction(transactionPO);
//        ProofOfWork.updateTargetBits();
        return newblock;
    }

    /**
     * 创建创世区块
     * @return
     */
    private BlockPO createGenesisBlock() {
        BlockHeader header = new BlockHeader(1, null);
        header.setNonce(XMZConfig.GENESIS_BLOCK_NONCE);
        header.setDifficulty(ProofOfWork.getTarget());
        header.setHash(header.hash());
        BlockBody body = new BlockBody();
        return new BlockPO(header, body);
    }
}
