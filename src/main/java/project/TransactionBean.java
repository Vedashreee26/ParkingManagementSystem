package project;
import java.io.Serializable;

@SuppressWarnings("serial")
public class TransactionBean implements Serializable {
	private String TransactionId;
	private String RecordID;
	private int amount;
	public String getTransactionId() {
		return TransactionId;
	}
	public void setTransactionId(String transactionId) {
		TransactionId = transactionId;
	}
	public String getRecordID() {
		return RecordID;
	}
	public void setRecordID(String recordID) {
		RecordID = recordID;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	private String paymentStatus;
	
}
