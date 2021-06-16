package xmz.block.chain.blockchainxmz.mining;

import xmz.block.chain.blockchainxmz.block.po.BlockPO;

import com.google.common.base.Optional;

public interface BlockMinerServiceImpl {

    /**
     * 创建区块
     * @param blockPO
     * @return
     */
    BlockPO createBlock(Optional<BlockPO> blockPO);

}
