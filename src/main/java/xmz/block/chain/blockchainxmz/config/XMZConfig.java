package xmz.block.chain.blockchainxmz.config;

import org.springframework.stereotype.Component;
import xmz.block.chain.blockchainxmz.common.utils.Convert;

import java.math.BigDecimal;

/**
 * 系统全局配置
 */
@Component
public class XMZConfig {

    /**
     * 交易数据验签
     */
    public static final String SIGNATURE_VERIFICATION_VECTOR="3056301006072a8648ce3d020106052b8104000a03420004";

    /**
     * 挖矿奖励
     */
    public static final BigDecimal MINING_REWARD =  Convert.toWei("50",Convert.Unit.ETHER);

    /**
     * 创世区块难度值
     */
    public static final Long GENESIS_BLOCK_NONCE = 100000L;

    /**
     * 单笔交易费率
     */
    //单笔最低手续费
    public static final BigDecimal TRANSACTION_LOW_RATE = Convert.toWei("0.5",Convert.Unit.ETHER);
    //浮动手续费
    public static final BigDecimal TRANSACTION_SPREAD_RATE = Convert.toWei("0.05",Convert.Unit.ETHER);
    //当前手续费
    public static BigDecimal TRANSACTION_RATE = Convert.toWei("0.5",Convert.Unit.ETHER);
    /**
     * 数据存储地址
     */
    public static final String dataDir="block-data/xmz";
    /**
     * db配置key,配置之后不能修改
     */
    /**
     * 挖矿总量
     */
    public static final String TOTAL_MINIER_NUM = "total_minier_num";
    /**
     * 总交易笔数
     */
    public static final String TOTAL_TRANSACTION_NUM = "total_transaction_num";
    /**
     * 当日产出
     */
    public static final String TOTAL_DATE_MINIER_NUM = "total_date_minier_num_";
    /**
     * 当日交易笔数
     */
    public static final String TOTAL_DATE_TRANSACTION_NUM = "total_date_transaction_num_";
    /**
     * 挖矿账户
     */
    public static final String MINIER_ACCOUNT = "miner_account";
    /**
     * 黑洞账户
     */
    public static final String BLACK_HOLE_ACCOUNT = "0x0000000000000000000000000000000000000000";
    /**
     * 钱包数据存储 address 桶前缀
     */
    public static final String WALLETS_BUCKET_PREFIX = "wallets_";
    /**
     * 区块数据存储 hash 桶前缀
     */
    public static final String BLOCKS_HASH_BUCKET_PREFIX = "blocks_hash_";
    /**
     * 区块数据存储 index 桶前缀
     */
    public static final String BLOCKS_INDEX_BUCKET_PREFIX = "blocks_index_";
    /**
     * 最新区块数据
     */
    public static final String LAST_BLOCK_INDEX = "LAST_"+BLOCKS_HASH_BUCKET_PREFIX+"last_block";
    /**
     * 待打包交易 hash 桶前缀
     */
    public static final String TRANSACTION_APPENDING_BUCKET_PREFIX = "transaction_appending_";
    /**
     * 打包完成交易 hash 桶前缀
     */
    public static final String TRANSACTION_BUCKET_PREFIX = "transaction_";
    /**
     * 完成交易前缀
     */
    public static final String NEWEST_TRANSACTION_BUCKET_PREFIX = "newest_transaction";

    /**
     * 地址发送和接收的所有交易数据
     */
    public static final String ADDRESS_TRANSACTION_BUCKET_PREFIX = "address_transaction_";

}
