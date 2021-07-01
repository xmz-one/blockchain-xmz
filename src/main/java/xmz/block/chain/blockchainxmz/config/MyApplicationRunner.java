package xmz.block.chain.blockchainxmz.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import xmz.block.chain.blockchainxmz.dbs.AccountDBs;
import xmz.block.chain.blockchainxmz.wallet.po.AccountPO;
import xmz.block.chain.blockchainxmz.wallet.service.WalletService;

import java.io.File;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    static Logger logger = LoggerFactory.getLogger(MyApplicationRunner.class);
    @Autowired
    private WalletService walletService;
    @Autowired
    private AccountDBs accountDB;

    @Override
    public void run(ApplicationArguments arguments) throws Exception {
        // 首次运行，执行一些初始化的工作
        File lockFile = new File(System.getProperty("user.dir") + "/" + XMZConfig.dataDir + "/node.lock");
        if (!lockFile.exists()) {
            lockFile.createNewFile();
            AccountPO minerAccount = walletService.minierAccount();
            accountDB.putMinierAccount(minerAccount);
            //暂停数据渲染，让矿工输出挖矿地址和私钥
//            Thread.sleep(1000000);
        }
        logger.error("初始化成功 !");
    }
}
