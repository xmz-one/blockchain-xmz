package xmz.block.chain.blockchainxmz.block.po;

import xmz.block.chain.blockchainxmz.transaction.po.TransactionPO;
import xmz.block.chain.blockchainxmz.transaction.vo.TransactionVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 区块数据
 */
public class BlockBody implements Serializable {

	/**
	 * 区块所包含的交易记录
	 */
	private List<TransactionPO> transactions;

	public BlockBody(List<TransactionPO> transactions) {
		this.transactions = transactions;
	}

	public BlockBody() {
		this.transactions = new ArrayList<>();
	}

	public List<TransactionPO> getTransactions() {
		return transactions;
	}

	/**
	 * 添加一笔交易打包到区块
	 * @param transaction
	 */
	public void addTransaction(TransactionPO transaction) {
		transactions.add(transaction);
	}

	@Override
	public String toString() {
		return "BlockBody{" +
				"transactions=" + transactions +
				'}';
	}
}
