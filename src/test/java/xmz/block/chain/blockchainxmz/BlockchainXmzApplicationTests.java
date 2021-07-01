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
import xmz.block.chain.blockchainxmz.common.utils.Convert;
import xmz.block.chain.blockchainxmz.common.utils.JsonVo;
import xmz.block.chain.blockchainxmz.common.utils.Numeric;
import xmz.block.chain.blockchainxmz.config.XMZConfig;
import xmz.block.chain.blockchainxmz.dbs.AccountDBs;
import xmz.block.chain.blockchainxmz.dbs.BlockDBs;
import xmz.block.chain.blockchainxmz.dbs.TransactionDBs;
import xmz.block.chain.blockchainxmz.transaction.vo.TransactionVO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.PublicKey;

@SpringBootTest
class BlockchainXmzApplicationTests {
    @Autowired
    private TransactionDBs transactionDBs;
    @Autowired
    private AccountDBs accountDBs;
    @Autowired
    private BlockDBs blockDBs;

    @Test
    void contextLoads() throws Exception {
//        for (Object o : blockDBs.getAllBlockTxIndex()) {
//            System.out.println(JSON.toJSON(o));
//        }
        TransactionVO transactionVO = new TransactionVO();
        transactionVO.setTo("0x2b602c21f53ffbadb3fafb07fea0b64130c13a5c");
        transactionVO.setAmount(Convert.toWei("100", Convert.Unit.ETHER));
        transactionVO.setData("转账");
        transactionVO.setRate(Convert.toWei("0.5", Convert.Unit.ETHER).setScale(0, BigDecimal.ROUND_DOWN));
        transactionVO.setTimestamp(System.currentTimeMillis());
//        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
//        Sign.publicKeyFromPrivate(Numeric.toHexStringNoPrefix());
        ECKeyPair ecKeyPair1 = ECKeyPair.create(Numeric.toBigIntNoPrefix("d649496062ecf112f6c97d29e5f621d47622f6ba1a2104c8d5a7748bba17380e"));
        transactionVO.setPublicKey(ecKeyPair1.getPublicKeyValue().toString(16));
        transactionVO.setSign(Sign.sign(ecKeyPair1.exportPrivateKey(),transactionVO.toSignString()));
        System.out.println(JSON.toJSONString(transactionVO));
//        System.out.println(ecKeyPair.getPublicKeyValue().toString(16));
//        System.out.println(ecKeyPair.getPublicKey().toString());
//        System.out.println(ecKeyPair.exportPrivateKey());
//        System.out.println(ecKeyPair.getAddress());
//        System.out.println(Keys.publicKeyEncode(ecKeyPair.getPublicKey().getEncoded()));
//        System.out.println(Keys.publicKeyEncode(ecKeyPair.getPublicKey().getEncoded()));
//        //验证数据和加密数据
        System.out.println(Sign.verify(Keys.publicKeyDecode(Keys.publicKeyEncode(ecKeyPair1.getPublicKey().getEncoded())),transactionVO.getSign(),transactionVO.toSignString()));
        System.out.println(Numeric.toHexStringNoPrefix(ecKeyPair1.getPublicKey().getEncoded()));
        System.out.println(Keys.getAddress(ecKeyPair1.getPublicKeyValue().toString(16)));
        System.out.println(ecKeyPair1.getPublicKeyValue().toString(16));
        System.out.println(Sign.verify(Numeric.hexStringToByteArray("3056301006072a8648ce3d020106052b8104000a03420004"+ecKeyPair1.getPublicKeyValue().toString(16)),transactionVO.getSign(),transactionVO.toSignString()));
        if (Sign.verify(Numeric.hexStringToByteArray(XMZConfig.SIGNATURE_VERIFICATION_VECTOR + transactionVO.getPublicKey()), transactionVO.getSign(), transactionVO.toSignString())) {
            System.out.println("成功");
        }
    }
}
