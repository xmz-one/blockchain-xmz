package xmz.block.chain.blockchainxmz.transaction.po;



import xmz.block.chain.blockchainxmz.common.crypto.Hash;
import xmz.block.chain.blockchainxmz.common.enums.TransactionStatusEnum;

import java.math.BigDecimal;

public class TransactionPO {

    /**
     * 付款地址
     */
    private String from;
    /**
     * 付款人公钥
     */
    private String publicKey;

    /**
     * 收款人地址
     */
    private String to;

    /**
     * 转账金额
     */
    private BigDecimal amount;

    /**
     * 付款人签名
     */
    private String sign;

    /**
     * 附加数据
     */
    private String data;

    /**
     * 交易时间戳
     */
    private Long timestamp;

    /**
     * 交易 Hash 值
     */
    private String txHash;

    /**
     * 交易状态
     */
    private TransactionStatusEnum status = TransactionStatusEnum.APPENDING;

    /**
     * 交易错误信息
     */
    private String errorMessage;

    /**
     * 当前交易所属区块高度
     */
    private int blockNumber;
    /**
     * 当前交易所属区块hash
     */
    private String blockTxHash;
    /**
     * 交易手续费
     */
    private BigDecimal rate;

    /**
     * 最低手续费
     */
    private BigDecimal lowRate;

    /**
     * 交易角标
     */
    private Long transactionIndex;

    /**
     * 数据输入
     */
    private String input;

    /**
     * pow答案
     */
    private Long nonce;

    /**
     * 转账类型：为后期坐准备
     */
    private Long type=Long.valueOf(1);

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(Long transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Long getNonce() {
        return nonce;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }

    public BigDecimal getLowRate() {
        return lowRate;
    }

    public void setLowRate(BigDecimal lowRate) {
        this.lowRate = lowRate;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public TransactionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusEnum status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getBlockTxHash() {
        return blockTxHash;
    }

    public void setBlockTxHash(String blockTxHash) {
        this.blockTxHash = blockTxHash;
    }

    @Override
    public String toString() {
        return "TransactionPO{" +
                "from='" + from + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", to='" + to + '\'' +
                ", amount=" + amount +
                ", sign='" + sign + '\'' +
                ", data='" + data + '\'' +
                ", timestamp=" + timestamp +
                ", txHash='" + txHash + '\'' +
                ", status=" + status +
                ", errorMessage='" + errorMessage + '\'' +
                ", blockNumber=" + blockNumber +
                ", blockTxHash='" + blockTxHash + '\'' +
                ", rate=" + rate +
                ", lowRate=" + lowRate +
                ", transactionIndex=" + transactionIndex +
                ", input='" + input + '\'' +
                ", nonce=" + nonce +
                ", type=" + type +
                '}';
    }

    /**
     * 计算交易信息的Hash值
     * @return
     */
    public String hash() {
        return Hash.sha3(this.toSignString());
    }

    public String toSignString() {
        return "TransactionVO{" +
                ", to='" + to + '\'' +
                ", amount=" + amount +
                ", publicKey='" + publicKey + '\'' +
                ", data='" + data + '\'' +
                ", timestamp=" + timestamp +
                ", rate=" + rate +
                '}';
    }
}
