package xmz.block.chain.blockchainxmz.transaction.vo;

import java.math.BigDecimal;

public class TransactionVO {

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
     * 下单时间
     */
    private Long timestamp;

    /**
     * 交易手续费
     */
    private BigDecimal rate;

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

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
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

    @Override
    public String toString() {
        return "TransactionVO{" +
                "publicKey='" + publicKey + '\'' +
                ", to='" + to + '\'' +
                ", amount=" + amount +
                ", sign='" + sign + '\'' +
                ", data='" + data + '\'' +
                ", timestamp=" + timestamp +
                ", rate=" + rate +
                '}';
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
