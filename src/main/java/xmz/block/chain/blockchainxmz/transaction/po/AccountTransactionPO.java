package xmz.block.chain.blockchainxmz.transaction.po;

import java.util.List;

public class AccountTransactionPO {

    private String address;

    private List<TransactionPO> transactionPOList;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<TransactionPO> getTransactionPOList() {
        return transactionPOList;
    }

    public void setTransactionPOList(List<TransactionPO> transactionPOList) {
        this.transactionPOList = transactionPOList;
    }

    public AccountTransactionPO(String address, List<TransactionPO> transactionPOList) {
        this.address = address;
        this.transactionPOList = transactionPOList;
    }

    public AccountTransactionPO() {
    }

    public void addAccountTransaction(TransactionPO transaction) {
        transactionPOList.add(transaction);
        if (transactionPOList.size() > 1000) {
            transactionPOList.remove(0);
        }
    }


    @Override
    public String toString() {
        return "AccountTransactionPO{" +
                "address='" + address + '\'' +
                ", transactionPOList=" + transactionPOList +
                '}';
    }
}
