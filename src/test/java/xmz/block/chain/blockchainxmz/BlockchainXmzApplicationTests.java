package xmz.block.chain.blockchainxmz;

import com.alibaba.fastjson.JSON;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.boot.test.context.SpringBootTest;
import xmz.block.chain.blockchainxmz.common.crypto.ECKeyPair;
import xmz.block.chain.blockchainxmz.common.crypto.Keys;
import xmz.block.chain.blockchainxmz.common.crypto.Sign;
import xmz.block.chain.blockchainxmz.common.utils.Numeric;
import xmz.block.chain.blockchainxmz.dbs.AccountDBs;
import xmz.block.chain.blockchainxmz.dbs.BlockDBs;
import xmz.block.chain.blockchainxmz.dbs.TransactionDBs;

@SpringBootTest
class BlockchainXmzApplicationTests {
    @Autowired
    private TransactionDBs transactionDBs;
    @Autowired
    private AccountDBs accountDBs;
    @Autowired
    private BlockDBs blockDBs;
    @Test
    void contextLoads() throws Exception{
    }
}
