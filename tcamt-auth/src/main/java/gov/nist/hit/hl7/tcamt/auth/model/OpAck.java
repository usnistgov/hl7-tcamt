package gov.nist.hit.hl7.tcamt.auth.model;

public class OpAck<T> {
	AckStatus status;
	String op;
	String text;
	T data;

	public OpAck() {
	}

	public OpAck(AckStatus status, String text, String op, T data) {
		this.text = text;
		this.op = op;
		this.status = status;
		this.data = data;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public AckStatus getStatus() {
		return status;
	}

	public void setStatus(AckStatus status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
