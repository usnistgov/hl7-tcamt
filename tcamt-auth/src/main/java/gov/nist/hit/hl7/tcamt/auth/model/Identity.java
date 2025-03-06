package gov.nist.hit.hl7.tcamt.auth.model;

public class Identity {
	String issuer;
	String uid;

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
