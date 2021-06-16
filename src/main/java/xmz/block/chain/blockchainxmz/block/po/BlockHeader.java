package xmz.block.chain.blockchainxmz.block.po;

import sun.rmi.runtime.Log;
import xmz.block.chain.blockchainxmz.common.crypto.Hash;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 区块头
 */
public class BlockHeader implements Serializable {

    /**
     * 区块高度
     */
    private Integer index;

    /**
     * 难度指标
     */
    private BigInteger difficulty;

    /**
     * PoW 问题的答案
     */
    private Long nonce;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 区块头 Hash
     */
    private String hash;

    /**
     * 上一个区块的 hash 地址
     */
    private String previousHash;

    /**
     * 交易笔数
     */
    private long transactions;

    /**
     * 交易手续费
     */
    private BigDecimal rate;
    /**
     * 区块奖励
     */
    private BigDecimal blockAward;
    /**
     * 矿工地址
     */
    private String minierAddress;



    public String getMinierAddress() {
        return minierAddress;
    }

    public void setMinierAddress(String minierAddress) {
        this.minierAddress = minierAddress;
    }

    public long getTransactions() {
        return transactions;
    }

    public void setTransactions(long transactions) {
        this.transactions = transactions;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getBlockAward() {
        return blockAward;
    }

    public void setBlockAward(BigDecimal blockAward) {
        this.blockAward = blockAward;
    }

    public BlockHeader(Integer index, String previousHash) {
        this.index = index;
        this.timestamp = System.currentTimeMillis();
        this.previousHash = previousHash;
    }

    public BlockHeader() {
        this.timestamp = System.currentTimeMillis();
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public BigInteger getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(BigInteger difficulty) {
        this.difficulty = difficulty;
    }

    public Long getNonce() {
        return nonce;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "BlockHeader{" +
                "index=" + index +
                ", difficulty=" + difficulty +
                ", nonce=" + nonce +
                ", timestamp=" + timestamp +
                ", hash='" + hash + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", transactions=" + transactions +
                ", rate=" + rate +
                ", blockAward=" + blockAward +
                ", minierAddress='" + minierAddress + '\'' +
                '}';
    }

    /**
     * 计算当前区块头的 hash 值
     *
     * @return
     */
    public String hash() {
        return Hash.sha3("BlockHeader{" +
                "index=" + index +
                ", difficulty=" + difficulty +
                ", nonce=" + nonce +
                ", timestamp=" + timestamp +
                ", previousHash='" + previousHash + '\'' +
                ", blockAward=" + blockAward +
                ", minierAddress='" + minierAddress + '\'' +
                '}');
    }
}
