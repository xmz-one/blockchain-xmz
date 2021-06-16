package xmz.block.chain.blockchainxmz.wallet.po;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 钱包账户
 */
public class AccountPO implements Serializable {

	/**
	 * 钱包地址
	 */
    private String address;

	/**
	 * 账户余额
	 */
    private BigDecimal balance;


	public AccountPO() {}

	public AccountPO(String address, BigDecimal balance) {
		this.address = address;
		this.balance = balance;
	}


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}


	@Override
	public String toString() {
		return "Account{" +
				"address='" + address + '\'' +
				", balance=" + balance +
				'}';
	}
}
