package com.ftdc.baseutil.bean;

import java.math.BigDecimal;

public class QRCode {
	private String memberId;
	private int codeType;
	private BigDecimal totalFee;
	private long timestamp;
	private String remark;

	public QRCode(String memberId, int codeType, BigDecimal totalFee, long timestamp) {
		super();
		this.memberId = memberId;
		this.codeType = codeType;
		this.totalFee = totalFee;
		this.timestamp = timestamp;
	}

	public QRCode() {
		super();
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getCodeType() {
		return codeType;
	}

	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "QRCode [memberId=" + memberId + ", codeType=" + codeType + ", totalFee=" + totalFee + ", timestamp="
				+ timestamp + "]";
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
