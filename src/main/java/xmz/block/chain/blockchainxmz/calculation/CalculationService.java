package xmz.block.chain.blockchainxmz.calculation;

import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmz.block.chain.blockchainxmz.common.utils.DateUtils;
import xmz.block.chain.blockchainxmz.config.XMZConfig;
import xmz.block.chain.blockchainxmz.dbs.AccountDBs;
import xmz.block.chain.blockchainxmz.dbs.RocksDBLibrary;
import xmz.block.chain.blockchainxmz.wallet.po.AccountPO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;


@Service
public class CalculationService {

    @Autowired
    private RocksDBLibrary rocksDBLibrary;
    @Autowired
    private AccountDBs accountDBs;

    public void updateTOTAL_MINIER_NUM(BigDecimal num) {
        Optional<Object> totaloptional = rocksDBLibrary.get(XMZConfig.TOTAL_MINIER_NUM);
        if (!totaloptional.isPresent()) {
            totaloptional = Optional.of(BigDecimal.ZERO);
        }
        BigDecimal total = ((BigDecimal) totaloptional.get()).add(num);
        Optional<Object> datetotaloptional = rocksDBLibrary.get(XMZConfig.TOTAL_DATE_MINIER_NUM + DateUtils.getDated());
        if (!datetotaloptional.isPresent()) {
            datetotaloptional = Optional.of(BigDecimal.ZERO);
        }
        BigDecimal dateTotal = ((BigDecimal) datetotaloptional.get()).add(num);
        rocksDBLibrary.put(XMZConfig.TOTAL_MINIER_NUM, total);
        rocksDBLibrary.put(XMZConfig.TOTAL_DATE_MINIER_NUM + DateUtils.getDated(), dateTotal);
    }


    public void updateTOTAL_TRANSACTION_NUM(BigInteger num) {
        Optional<Object> totaloptional = rocksDBLibrary.get(XMZConfig.TOTAL_TRANSACTION_NUM);
        if (!totaloptional.isPresent()) {
            totaloptional = Optional.of(BigInteger.ZERO);
        }
        BigInteger total = ((BigInteger) totaloptional.get()).add(num);
        Optional<Object> datetotaloptional = rocksDBLibrary.get(XMZConfig.TOTAL_DATE_TRANSACTION_NUM + DateUtils.getDated());
        if (!datetotaloptional.isPresent()) {
            datetotaloptional = Optional.of(BigInteger.ZERO);
        }
        BigInteger dateTotal = ((BigInteger) datetotaloptional.get()).add(num);
        rocksDBLibrary.put(XMZConfig.TOTAL_TRANSACTION_NUM, total);
        rocksDBLibrary.put(XMZConfig.TOTAL_DATE_TRANSACTION_NUM + DateUtils.getDated(), dateTotal);
    }

    public Object getCalculation(String key) {
        return rocksDBLibrary.get(key).get();
    }


    public Object getCalculationTotalAccount() {
        long num=0;
        List<AccountPO> accountPOS = accountDBs.getAllAccount();
        for (AccountPO accountPO : accountPOS) {
            if(accountPO.getBalance().compareTo(BigDecimal.ZERO)==1){
                num++;
            }
        }
        return num;
    }
}
