package xmz.block.chain.blockchainxmz.dbs;

import com.google.common.base.Optional;
import org.springframework.stereotype.Component;
import xmz.block.chain.blockchainxmz.wallet.po.AccountPO;

import java.util.List;
public interface AccountDBs {
    /**
     * 添加
     * @param accountPO
     * @return
     */
    boolean putAccount(AccountPO accountPO);

    /**
     * 删除
     * @param address
     * @return
     */
    boolean delAccount(String address);

    /**
     * 查询所有
     * @return
     */
    List<AccountPO> getAllAccount();

    /**
     * 根据地址查询
     * @param address
     * @return
     */
    Optional<AccountPO> getAccount(String address);


    /**
     * 查询
     * @return
     */
    Optional<AccountPO> getMinierAccount();
    /**
     * 添加
     * @param accountPO
     * @return
     */
    boolean putMinierAccount(AccountPO accountPO);
}
