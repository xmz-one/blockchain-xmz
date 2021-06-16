package xmz.block.chain.blockchainxmz.dbs;


import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmz.block.chain.blockchainxmz.block.po.BlockPO;
import xmz.block.chain.blockchainxmz.config.XMZConfig;

import java.util.ArrayList;
import java.util.List;
@Component
public class BlockDBsimpl implements BlockDBs {

    @Autowired
    private RocksDBLibrary rocksDBLibrary;

    @Override
    public boolean putBlockTxHash(BlockPO blockPO) {
        return rocksDBLibrary.put(XMZConfig.BLOCKS_HASH_BUCKET_PREFIX + blockPO.getHeader().getHash(), blockPO);
    }

    @Override
    public boolean delBlockTxHash(String txhash) {
        return rocksDBLibrary.delete(XMZConfig.BLOCKS_HASH_BUCKET_PREFIX + txhash);
    }

    @Override
    public List<BlockPO> getAllBlockTxHash() {
        List<Object> objects = rocksDBLibrary.seekByKey(XMZConfig.BLOCKS_HASH_BUCKET_PREFIX);
        List<BlockPO> blockPOS = new ArrayList<>();
        for (Object o : objects) {
            blockPOS.add((BlockPO) o);
        }
        return blockPOS;
    }

    @Override
    public List<BlockPO> getAllBlock1000(List<String> keys) {
        List<Object> objects = rocksDBLibrary.multiGetAsList(XMZConfig.BLOCKS_INDEX_BUCKET_PREFIX,keys);
        List<BlockPO> blockPOS = new ArrayList<>();
        for (Object o : objects) {
            blockPOS.add((BlockPO) o);
        }
        return blockPOS;
    }

    @Override
    public Optional<BlockPO> getBlockTxHash(String txhash) {
        Optional<Object> object = rocksDBLibrary.get(XMZConfig.BLOCKS_HASH_BUCKET_PREFIX + txhash);
        if (object.isPresent()) {
            return Optional.of((BlockPO) object.get());
        }
        return Optional.absent();
    }


    @Override
    public boolean putBlockTxIndex(BlockPO blockPO) {
        return rocksDBLibrary.put(XMZConfig.BLOCKS_INDEX_BUCKET_PREFIX + blockPO.getHeader().getIndex(), blockPO);
    }

    @Override
    public boolean delBlockTxIndex(String index) {
        return rocksDBLibrary.delete(XMZConfig.BLOCKS_INDEX_BUCKET_PREFIX + index);
    }

    @Override
    public List<BlockPO> getAllBlockTxIndex() {
        List<Object> objects = rocksDBLibrary.seekByKey(XMZConfig.BLOCKS_INDEX_BUCKET_PREFIX);
        List<BlockPO> blockPOS = new ArrayList<>();
        for (Object o : objects) {
            blockPOS.add((BlockPO) o);
        }
        return blockPOS;
    }

    @Override
    public Optional<BlockPO> getBlockTxIndex(String index) {
        Optional<Object> object = rocksDBLibrary.get(XMZConfig.BLOCKS_INDEX_BUCKET_PREFIX + index);
        if (object.isPresent()) {
            return Optional.of((BlockPO) object.get());
        }
        return Optional.absent();
    }

    @Override
    public Optional<BlockPO> getBlockLast() {
        Optional<Object> object = rocksDBLibrary.get(XMZConfig.LAST_BLOCK_INDEX);
        if (object.isPresent()) {
            return Optional.of((BlockPO) object.get());
        }
        return Optional.absent();
    }

    @Override
    public boolean putBlockLast(BlockPO blockPO) {
        return rocksDBLibrary.put(XMZConfig.LAST_BLOCK_INDEX, blockPO);
    }
}
