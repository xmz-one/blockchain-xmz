package xmz.block.chain.blockchainxmz.dbs;


import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmz.block.chain.blockchainxmz.config.XMZConfig;
import xmz.block.chain.blockchainxmz.transaction.po.AccountTransactionPO;
import xmz.block.chain.blockchainxmz.transaction.po.TransactionPO;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionDBsImpl implements TransactionDBs {

    @Autowired
    private RocksDBLibrary rocksDBLibrary;


    @Override
    public boolean putTransaction(TransactionPO transactionPO) {
        return rocksDBLibrary.put(XMZConfig.TRANSACTION_BUCKET_PREFIX + transactionPO.getTxHash(), transactionPO);
    }

    @Override
    public boolean delTransaction(String txhash) {
        return rocksDBLibrary.delete(XMZConfig.TRANSACTION_BUCKET_PREFIX + txhash);
    }

    @Override
    public List<TransactionPO> getAllTransaction() {
        List<Object> objects = rocksDBLibrary.seekByKey(XMZConfig.TRANSACTION_BUCKET_PREFIX);
        List<TransactionPO> transactionPOS = new ArrayList<>();
        for (Object o : objects) {
            transactionPOS.add((TransactionPO) o);
        }
        return transactionPOS;
    }

    @Override
    public Optional<TransactionPO> getTransaction(String txhash) {
        Optional<Object> object = rocksDBLibrary.get(XMZConfig.TRANSACTION_BUCKET_PREFIX + txhash);
        if (object.isPresent()) {
            return Optional.of((TransactionPO) object.get());
        }
        return Optional.absent();
    }


    @Override
    public boolean putAppendingTransaction(TransactionPO transactionPO) {
        return rocksDBLibrary.put(XMZConfig.TRANSACTION_APPENDING_BUCKET_PREFIX + transactionPO.getTxHash(), transactionPO);
    }

    @Override
    public boolean delAppendingTransaction(String txhash) {
        return rocksDBLibrary.delete(XMZConfig.TRANSACTION_APPENDING_BUCKET_PREFIX + txhash);
    }

    @Override
    public List<TransactionPO> getAllAppendingTransaction() {
        List<Object> objects = rocksDBLibrary.seekByKey(XMZConfig.TRANSACTION_APPENDING_BUCKET_PREFIX);
        List<TransactionPO> transactionPOS = new ArrayList<>();
        for (Object o : objects) {
            transactionPOS.add((TransactionPO) o);
        }
        return transactionPOS;
    }

    @Override
    public Optional<TransactionPO> getAppendingTransaction(String txhash) {
        Optional<Object> object = rocksDBLibrary.get(XMZConfig.TRANSACTION_APPENDING_BUCKET_PREFIX + txhash);
        if (object.isPresent()) {
            return Optional.of((TransactionPO) object.get());
        }
        return Optional.absent();
    }

    @Override
    public Optional<AccountTransactionPO> getAccountTransaction(String address) {
        Optional<Object> object = rocksDBLibrary.get(XMZConfig.ADDRESS_TRANSACTION_BUCKET_PREFIX + address);
        if (object.isPresent()) {
            return Optional.of((AccountTransactionPO) object.get());
        }
        return Optional.absent();
    }

    @Override
    public boolean putNewestTransactionList(TransactionPO transactionPO) {
        Optional<Object> objectOptional = rocksDBLibrary.get(XMZConfig.NEWEST_TRANSACTION_BUCKET_PREFIX);
        if (!objectOptional.isPresent()) {
            List<TransactionPO> transactionPOS = new ArrayList<>();
            transactionPOS.add(transactionPO);
            return rocksDBLibrary.put(XMZConfig.NEWEST_TRANSACTION_BUCKET_PREFIX, transactionPOS);
        } else {
            Optional<List<TransactionPO>> listOptional = Optional.of((List<TransactionPO>) objectOptional.get());
            listOptional.get().add(transactionPO);
            if (listOptional.get().size() > 10000) {
                listOptional.get().remove(0);
            }
            return rocksDBLibrary.put(XMZConfig.NEWEST_TRANSACTION_BUCKET_PREFIX, listOptional.get());
        }
    }

    @Override
    public Optional<List<TransactionPO>> getNewestTransactionList() {
        Optional<Object> objectOptional = rocksDBLibrary.get(XMZConfig.NEWEST_TRANSACTION_BUCKET_PREFIX);
        if (objectOptional.isPresent()) {
            return Optional.of((List<TransactionPO>) objectOptional.get());
        }
        return Optional.absent();
    }

    @Override
    public boolean putAccountTransaction(AccountTransactionPO accountTransactionPO) {
        return rocksDBLibrary.put(XMZConfig.ADDRESS_TRANSACTION_BUCKET_PREFIX + accountTransactionPO.getAddress(), accountTransactionPO);
    }
}
