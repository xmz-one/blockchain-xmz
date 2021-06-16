package xmz.block.chain.blockchainxmz.mining.pow;

import org.apache.commons.lang3.StringUtils;
import xmz.block.chain.blockchainxmz.block.po.BlockPO;
import xmz.block.chain.blockchainxmz.common.crypto.Hash;
import xmz.block.chain.blockchainxmz.common.utils.ByteUtils;
import xmz.block.chain.blockchainxmz.common.utils.Numeric;

import java.math.BigInteger;
import java.util.Random;

/**
 * 工作量证明实现
 */
public class ProofOfWork {

    /**
     * 难度目标位, target=24 时大约 30 秒出一个区块
     */
    public static int TARGET_BITS = 16;

    public static void updateTargetBits() {
        Random rand = new Random();
        TARGET_BITS = rand.nextInt(24)+1;
        System.out.println("挖矿难度调整："+TARGET_BITS);
    }

    /**
     * 区块
     */
    private BlockPO block;

    /**
     * 难度目标值
     */
    private BigInteger target;

    /**
     * <p>创建新的工作量证明，设定难度目标值</p>
     * <p>对1进行移位运算，将1向左移动 (256 - TARGET_BITS) 位，得到我们的难度目标值</p>
     * @param block
     * @return
     */
    public static ProofOfWork newProofOfWork(BlockPO block) {
        BigInteger targetValue = BigInteger.valueOf(1).shiftLeft((256 - TARGET_BITS));
        return new ProofOfWork(block, targetValue);
    }

    private ProofOfWork(BlockPO block, BigInteger target) {
        this.block = block;
        this.target = target;
    }

    /**
     * 运行工作量证明，开始挖矿，找到小于难度目标值的Hash
     * @return
     */
    public PowResult run() {
        long nonce = 0;
        String shaHex = "";
        while (nonce < Long.MAX_VALUE) {
            byte[] data = this.prepareData(nonce);
            shaHex = Hash.sha3String(data);
            if (new BigInteger(shaHex, 16).compareTo(this.target) == -1) {
                break;
            } else {
                nonce++;
            }
        }
        return new PowResult(nonce, shaHex, this.target);
    }

    /**
     * 验证区块是否有效
     * @return
     */
    public boolean validate() {
        byte[] data = this.prepareData(this.getBlock().getHeader().getNonce());
        return new BigInteger(Hash.sha3String(data), 16).compareTo(this.target) == -1;
    }

    /**
     * 准备数据
     * <p>
     * 注意：在准备区块数据时，一定要从原始数据类型转化为byte[]，不能直接从字符串进行转换
     * @param nonce
     * @return
     */
    private byte[] prepareData(long nonce) {
        byte[] prevBlockHashBytes = {};
        if (StringUtils.isNotBlank(this.getBlock().getHeader().getPreviousHash())) {
            //这里要去掉 hash 值的　0x 前缀， 否则会抛出异常
            String prevHash = Numeric.cleanHexPrefix(this.getBlock().getHeader().getPreviousHash());
            prevBlockHashBytes = new BigInteger(prevHash, 16).toByteArray();
        }

        return ByteUtils.merge(
                prevBlockHashBytes,
                ByteUtils.toBytes(this.getBlock().getHeader().getTimestamp()),
                ByteUtils.toBytes(TARGET_BITS),
                ByteUtils.toBytes(nonce)
        );
    }


    public BlockPO getBlock() {
        return block;
    }

    public static BigInteger getTarget() {
        return BigInteger.valueOf(1).shiftLeft((256 - TARGET_BITS));
    }

}
