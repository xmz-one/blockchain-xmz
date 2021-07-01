package xmz.block.chain.blockchainxmz.transaction.service;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmz.block.chain.blockchainxmz.common.crypto.Address;
import xmz.block.chain.blockchainxmz.common.crypto.Keys;
import xmz.block.chain.blockchainxmz.common.crypto.Sign;
import xmz.block.chain.blockchainxmz.common.utils.Convert;
import xmz.block.chain.blockchainxmz.common.utils.JsonVo;
import xmz.block.chain.blockchainxmz.common.utils.Numeric;
import xmz.block.chain.blockchainxmz.common.utils.PageUtil;
import xmz.block.chain.blockchainxmz.config.XMZConfig;
import xmz.block.chain.blockchainxmz.common.enums.TransactionStatusEnum;
import xmz.block.chain.blockchainxmz.dbs.TransactionDBs;
import xmz.block.chain.blockchainxmz.dbs.AccountDBs;
import xmz.block.chain.blockchainxmz.page.PagePO;
import xmz.block.chain.blockchainxmz.page.PageVO;
import xmz.block.chain.blockchainxmz.transaction.po.AccountTransactionPO;
import xmz.block.chain.blockchainxmz.transaction.po.TransactionPO;
import xmz.block.chain.blockchainxmz.transaction.vo.TransactionVO;
import xmz.block.chain.blockchainxmz.wallet.po.AccountPO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private AccountDBs accountDB;
    @Autowired
    private TransactionDBs transactionDB;
    static Logger logger = LoggerFactory.getLogger(TransactionService.class);

    /**
     * 发送交易
     *
     * @param transactionVO
     * @return
     */
    public JsonVo sendTransaction(TransactionVO transactionVO) {
        try {
            JsonVo jsonVo;
            if (!Address.isETHValidAddress(transactionVO.getTo())) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("Illegal address format !");
                return jsonVo;
            }
            if ((System.currentTimeMillis() - transactionVO.getTimestamp()) > (60 * 1000) || (System.currentTimeMillis() - transactionVO.getTimestamp()) < 0) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("The deal is no longer valid !");
                return jsonVo;
            }
            if (transactionVO.getData().length() > 1000) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("The length of additional data cannot be greater than 1000 !");
                return jsonVo;
            }
            if (transactionVO.getAmount().compareTo(BigDecimal.ZERO) != 1) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("Invalid transaction amount !");
                return jsonVo;
            }
            if (Convert.fromWei(transactionVO.getAmount(), Convert.Unit.ETHER).setScale(0, BigDecimal.ROUND_DOWN).compareTo(BigDecimal.ZERO) != 1) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("Invalid transaction amount !");
                return jsonVo;
            }
            if (!Sign.verify(Numeric.hexStringToByteArray(XMZConfig.SIGNATURE_VERIFICATION_VECTOR + transactionVO.getPublicKey()), transactionVO.getSign(), transactionVO.toSignString())) {
                return JsonVo.instance(JsonVo.CODE_FAIL, " Transaction signature error");
            }
            String from = Keys.getAddress(transactionVO.getPublicKey());
            Optional<AccountPO> account = accountDB.getAccount(from);
            if (!account.isPresent()) {
                return JsonVo.instance(JsonVo.CODE_FAIL, "Insufficient account balance !");
            }
            if (account.get().getBalance().compareTo(transactionVO.getAmount().add(transactionVO.getRate())) == -1) {
                return JsonVo.instance(JsonVo.CODE_FAIL, "Insufficient account balance !");
            }
            if (transactionVO.getRate().compareTo(XMZConfig.TRANSACTION_RATE.setScale(0, BigDecimal.ROUND_DOWN)) == -1) {
                return JsonVo.instance(JsonVo.CODE_FAIL, "Transaction fees are too low !");
            }
            TransactionPO transactionPO = new TransactionPO();
            transactionPO.setFrom(from);
            transactionPO.setTo(transactionVO.getTo());
            transactionPO.setSign(transactionVO.getSign());
            transactionPO.setAmount(transactionVO.getAmount().setScale(0, BigDecimal.ROUND_DOWN));
            transactionPO.setData(transactionVO.getData());
            transactionPO.setStatus(TransactionStatusEnum.APPENDING);
            transactionPO.setPublicKey(transactionVO.getPublicKey());
            transactionPO.setRate(transactionVO.getRate().setScale(0, BigDecimal.ROUND_DOWN));
            transactionPO.setTimestamp(transactionVO.getTimestamp());
            transactionPO.setTxHash(transactionPO.hash());
            transactionPO.setInput("0x");
            transactionPO.setLowRate(XMZConfig.TRANSACTION_LOW_RATE.setScale(0, BigDecimal.ROUND_DOWN));
            Optional<TransactionPO> transactionPOOptional = transactionDB.getAppendingTransaction(transactionPO.getTxHash());
            if (transactionPOOptional.isPresent()) {
                return JsonVo.instance(JsonVo.CODE_FAIL, "Trade repeat broadcast !");
            }
            transactionDB.putAppendingTransaction(transactionPO);
            logger.info("Create Transaction : {}", transactionPO);
            jsonVo = JsonVo.success();
            jsonVo.setItem(transactionPO.getTxHash());
            return jsonVo;
        } catch (Exception e) {
            logger.error("Create Transaction error : {} , Exception{}", transactionVO, e);
        }
        return JsonVo.instance(JsonVo.CODE_FAIL, "Transaction data error !");
    }

    /**
     * 待打包订单列表查询
     *
     * @return
     */
    public JsonVo getAllAppendingTransaction() {
        JsonVo jsonVo;
        List<TransactionPO> transactionPOS = transactionDB.getAllAppendingTransaction();
        jsonVo = JsonVo.success();
        jsonVo.setItem(transactionPOS);
        return jsonVo;
    }

    /**
     * 获取当前地址的所有转入和转出记录
     *
     * @param pagePO
     * @return
     */
    public JsonVo getAccountTransaction(PagePO pagePO) {
        JsonVo jsonVo;
        if (!Address.isETHValidAddress(pagePO.getData().toString())) {
            jsonVo = JsonVo.fail();
            jsonVo.setMessage("Illegal address format !");
            return jsonVo;
        }
        Optional<AccountTransactionPO> accountTransactionPOOptional = transactionDB.getAccountTransaction(pagePO.getData().toString());
        if (!accountTransactionPOOptional.isPresent()) {
            accountTransactionPOOptional = Optional.of(new AccountTransactionPO(pagePO.getData().toString(), new ArrayList<>()));
        }
        List<TransactionPO> transactionPOS = accountTransactionPOOptional.get().getTransactionPOList();
        Collections.reverse(transactionPOS);
        PageUtil<TransactionPO> poPageUtil = new PageUtil<>();
        poPageUtil.pageUtil(pagePO.getPage(), pagePO.getRows(), transactionPOS);
        PageVO pageVO = new PageVO();
        pageVO.setTotal(poPageUtil.getTotalElements());
        pageVO.setData(poPageUtil.getContent());
        jsonVo = JsonVo.success();
        jsonVo.setItem(pageVO);
        return jsonVo;

    }


    /**
     * 获取当前地址的所有转入和转出记录
     *
     * @param pagePO
     * @return
     */
    public JsonVo getNewestTransactionList(PagePO pagePO) {
        JsonVo jsonVo;
        Optional<List<TransactionPO>> accountTransactionPOOptional = transactionDB.getNewestTransactionList();
        if (!accountTransactionPOOptional.isPresent()) {
            accountTransactionPOOptional = Optional.of(new ArrayList<>());
        }
        List<TransactionPO> transactionPOS = accountTransactionPOOptional.get();
        Collections.reverse(transactionPOS);
        PageUtil<TransactionPO> poPageUtil = new PageUtil<>();
        poPageUtil.pageUtil(pagePO.getPage(), pagePO.getRows(), transactionPOS);
        PageVO pageVO = new PageVO();
        pageVO.setTotal(poPageUtil.getTotalElements());
        pageVO.setData(poPageUtil.getContent());
        jsonVo = JsonVo.success();
        jsonVo.setItem(pageVO);
        return jsonVo;
    }

    /**
     * 添加地址对应的转入和转出记录
     *
     * @param address
     * @param transactionPO
     * @return
     */
    public boolean putAccountTransaction(String address, TransactionPO transactionPO) {
        Optional<AccountTransactionPO> accountTransactionPOOptional = transactionDB.getAccountTransaction(address);
        if (!accountTransactionPOOptional.isPresent()) {
            accountTransactionPOOptional = Optional.of(new AccountTransactionPO(address, new ArrayList<>()));
        }
        accountTransactionPOOptional.get().addAccountTransaction(transactionPO);
        return transactionDB.putAccountTransaction(accountTransactionPOOptional.get());
    }


    public JsonVo getTransaction(String txhash) {
        JsonVo jsonVo;
        Optional<TransactionPO> transactionPOOptional = transactionDB.getTransaction(txhash);
        if (!transactionPOOptional.isPresent()) {
            transactionPOOptional = transactionDB.getTransaction(txhash);
            if (!transactionPOOptional.isPresent()) {
                jsonVo = JsonVo.fail();
                jsonVo.setMessage("The current transaction hash does not exist !");
                return jsonVo;
            }
        }
        jsonVo = JsonVo.success();
        jsonVo.setItem(transactionPOOptional.get());
        return jsonVo;
    }
}
