package xmz.block.chain.blockchainxmz.wallet.service;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmz.block.chain.blockchainxmz.common.crypto.Address;
import xmz.block.chain.blockchainxmz.common.crypto.ECKeyPair;
import xmz.block.chain.blockchainxmz.common.crypto.Keys;
import xmz.block.chain.blockchainxmz.common.utils.Convert;
import xmz.block.chain.blockchainxmz.common.utils.JsonVo;
import xmz.block.chain.blockchainxmz.dbs.AccountDBs;
import xmz.block.chain.blockchainxmz.wallet.po.AccountPO;

import java.math.BigDecimal;


@Service
public class WalletService {

    @Autowired
    private AccountDBs accountDBImpl;

    static Logger logger = LoggerFactory.getLogger(WalletService.class);

    /**
     * 初始化挖矿账户和黑洞地址
     *
     * @return
     * @throws Exception
     */
    public AccountPO minierAccount() throws Exception {
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        logger.info("Create miner account address : {} account PrivateKey,{}", ecKeyPair.getAddress(), ecKeyPair.exportPrivateKey());
        AccountPO account = new AccountPO(ecKeyPair.getAddress(), Convert.toWei("100000000", Convert.Unit.ETHER));
        accountDBImpl.putAccount(account);
        return account;
    }

    /**
     * 查询地址余额
     * @param address
     * @return
     */
    public JsonVo getAccount(String address) {
        JsonVo jsonVo;
        if(!Address.isETHValidAddress(address)){
            jsonVo=JsonVo.fail();
            jsonVo.setMessage("Illegal address format !");
            return jsonVo;
        }
        Optional<AccountPO> accountPO = accountDBImpl.getAccount(address);
        if (!accountPO.isPresent()) {
            accountPO = Optional.of(new AccountPO(address, BigDecimal.ZERO));
        }
        jsonVo=JsonVo.success();
        jsonVo.setItem(accountPO.get().getBalance());
        return jsonVo;
    }

}
