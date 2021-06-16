package xmz.block.chain.blockchainxmz.dbs;

import com.google.common.base.Optional;
import org.rocksdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import xmz.block.chain.blockchainxmz.common.utils.SerializeUtils;
import xmz.block.chain.blockchainxmz.config.XMZConfig;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Component
public class RocksDBLibrary {

    static Logger logger = LoggerFactory.getLogger(RocksDBLibrary.class);

    private RocksDB rocksDB;

    /**
     * 初始化RocksDB
     */
    @PostConstruct
    public void initRocksDB() {
        try {
            File directory = new File(System.getProperty("user.dir") + "/" + XMZConfig.dataDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            rocksDB = RocksDB.open(new Options().setCreateIfMissing(true), XMZConfig.dataDir);
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增
     *
     * @param key
     * @param value
     * @return
     */
    public boolean put(String key, Object value) {
        try {
            rocksDB.put(key.getBytes(), SerializeUtils.serialize(value));
            return true;
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.error("ERROR for RocksDB : {}", e);
            }
            return false;
        }
    }

    /**
     * 查询
     *
     * @param key
     * @return
     */
    public Optional<Object> get(String key) {
        try {
            return Optional.of(SerializeUtils.unSerialize(rocksDB.get(key.getBytes())));
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.error("ERROR for RocksDB : {}", e);
            }
            return Optional.absent();
        }
    }

    /**
     * 删除
     *
     * @param key
     * @return
     */
    public boolean delete(String key) {
        try {
            rocksDB.delete(key.getBytes());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public <T> List<T> seekByKey(String keyPrefix) {
        ArrayList<T> ts = new ArrayList<>();
        RocksIterator iterator = rocksDB.newIterator(new ReadOptions());
        iterator.seek(keyPrefix.getBytes());
        byte[] key = keyPrefix.getBytes();
        for (iterator.seek(key); iterator.isValid() && new String(iterator.key()).startsWith(keyPrefix); iterator.next()) {
            ts.add((T) SerializeUtils.unSerialize(iterator.value()));
        }
        return ts;
    }


    public <T> List<T> multiGetAsList(String key,List<String> keys) {
        try {
            List<T> ts = new ArrayList<>();
            List<byte[]> listby = new ArrayList<>();
            for (String s : keys) {
                listby.add((key+s).getBytes());
            }
            List<byte[]> bytes = rocksDB.multiGetAsList(listby);
            for (byte[] aByte : bytes) {
                ts.add((T) SerializeUtils.unSerialize(aByte));
            }
            return ts;
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.error("ERROR for RocksDB : {}", e);
            }
            return new ArrayList<>();
        }
    }


    /**
     * 关闭
     */
    public void closeDB() {
        if (null != rocksDB) {
            rocksDB.close();
        }
    }
}
