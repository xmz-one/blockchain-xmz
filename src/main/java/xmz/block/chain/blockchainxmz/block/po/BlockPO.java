package xmz.block.chain.blockchainxmz.block.po;

import java.io.Serializable;

/**
 * 区块
 */
public class BlockPO implements Serializable {

	/**
	 * 区块 Header
	 */
	private BlockHeader header;

	/**
	 * 区块 Body
	 */
	private BlockBody body;


	public BlockPO(BlockHeader header, BlockBody body) {
		this.header = header;
		this.body = body;
	}

	public BlockPO() {
	}

	public BlockHeader getHeader() {
		return header;
	}

	public void setHeader(BlockHeader header) {
		this.header = header;
	}

	public BlockBody getBody() {
		return body;
	}

	public void setBody(BlockBody body) {
		this.body = body;
	}


	@Override
	public String toString() {
		return "Block{" +
				"header=" + header +
				", body=" + body +
				'}';
	}

}
