package xmz.block.chain.blockchainxmz.dbs;


import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmz.block.chain.blockchainxmz.config.XMZConfig;
import xmz.block.chain.blockchainxmz.wallet.po.AccountPO;

import java.util.ArrayList;
import java.util.List;
@Component
public class AccountDBsImpl implements AccountDBs {

    @Autowired
    private RocksDBLibrary rocksDBLibrary;


    @Override
    public boolean putAccount(AccountPO accountPO) {
        return rocksDBLibrary.put(XMZConfig.WALLETS_BUCKET_PREFIX + accountPO.getAddress(), accountPO);
    }

    @Override
    public boolean delAccount(String address) {
        return rocksDBLibrary.delete(XMZConfig.WALLETS_BUCKET_PREFIX + address);
    }

    @Override
    public List<AccountPO> getAllAccount() {
        List<Object> objects = rocksDBLibrary.seekByKey(XMZConfig.WALLETS_BUCKET_PREFIX);
        List<AccountPO> accounts = new ArrayList<>();
        for (Object o : objects) {
            accounts.add((AccountPO) o);
        }
        return accounts;
    }

    @Override
    public Optional<AccountPO> getAccount(String address) {
        Optional<Object> object = rocksDBLibrary.get(XMZConfig.WALLETS_BUCKET_PREFIX + address);
        if (object.isPresent()) {
            return Optional.of((AccountPO) object.get());
        }
        return Optional.absent();
    }


    @Override
    public boolean putMinierAccount(AccountPO accountPO) {
        return rocksDBLibrary.put(XMZConfig.MINIER_ACCOUNT, accountPO);
    }
    @Override
    public Optional<AccountPO> getMinierAccount() {
        Optional<Object> object = rocksDBLibrary.get(XMZConfig.MINIER_ACCOUNT);
        if (object.isPresent()) {
            return Optional.of((AccountPO) object.get());
        }
        return Optional.absent();
    }
}
