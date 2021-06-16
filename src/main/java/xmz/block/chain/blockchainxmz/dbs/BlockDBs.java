package xmz.block.chain.blockchainxmz.dbs;

import com.google.common.base.Optional;
import org.springframework.stereotype.Component;
import xmz.block.chain.blockchainxmz.block.po.BlockPO;

import java.util.List;
public interface BlockDBs {
    /**
     * 添加
     * @param blockPO
     * @return
     */
    boolean putBlockTxHash(BlockPO blockPO);

    /**
     * 删除
     * @param txhash
     * @return
     */
    boolean delBlockTxHash(String txhash);

    /**
     * 查询所有
     * @return
     */
    List<BlockPO> getAllBlockTxHash();

    List<BlockPO> getAllBlock1000(List<String> keys);

    /**
     * 根据hash查询
     * @param txhash
     * @return
     */
    Optional<BlockPO> getBlockTxHash(String txhash);

    /**
     * 添加
     * @param blockPO
     * @return
     */
    boolean putBlockTxIndex(BlockPO blockPO);

    /**
     * 删除
     * @param txhash
     * @return
     */
    boolean delBlockTxIndex(String txhash);

    /**
     * 查询所有
     * @return
     */
    List<BlockPO> getAllBlockTxIndex();

    /**
     * 根据index询
     * @param index
     * @return
     */
    Optional<BlockPO> getBlockTxIndex(String index);

    /**
     * 查询
     * @return
     */
    Optional<BlockPO> getBlockLast();
    /**
     * 添加
     * @param blockPO
     * @return
     */
    boolean putBlockLast(BlockPO blockPO);
}
