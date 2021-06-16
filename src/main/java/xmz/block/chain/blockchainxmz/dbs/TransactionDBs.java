package xmz.block.chain.blockchainxmz.dbs;

import com.google.common.base.Optional;
import xmz.block.chain.blockchainxmz.transaction.po.AccountTransactionPO;
import xmz.block.chain.blockchainxmz.transaction.po.TransactionPO;

import java.util.List;

public interface TransactionDBs {
    /**
     * 添加
     * @param transactionPO
     * @return
     */
    boolean putTransaction(TransactionPO transactionPO);

    /**
     * 删除
     * @param txhash
     * @return
     */
    boolean delTransaction(String txhash);

    /**
     * 查询所有
     * @return
     */
    List<TransactionPO> getAllTransaction();

    /**
     * 根据hash查询
     * @param txhash
     * @return
     */
    Optional<TransactionPO> getTransaction(String txhash);

    /**
     * 添加
     * @param transactionPO
     * @return
     */
    boolean putAppendingTransaction(TransactionPO transactionPO);

    /**
     * 删除
     * @param txhash
     * @return
     */
    boolean delAppendingTransaction(String txhash);

    /**
     * 查询所有
     * @return
     */
    List<TransactionPO> getAllAppendingTransaction();

    /**
     * 根据hash查询
     * @param txhash
     * @return
     */
    Optional<TransactionPO> getAppendingTransaction(String txhash);
    /**
     * 添加
     * @return
     */
    boolean putAccountTransaction(AccountTransactionPO accountTransactionPO);
    /**
     * 根据address查询
     * @param address
     * @return
     */
    Optional<AccountTransactionPO> getAccountTransaction(String address);

    /**
     * 添加
     * @param transactionPO
     * @return
     */
    boolean putNewestTransactionList(TransactionPO transactionPO);
    /**
     * 根据hash查询
     * @return
     */
    Optional<List<TransactionPO>> getNewestTransactionList();
}
