package com.pote.library.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BorrowRequestDTO {
	private String qrCodeStr; 
	private String studentIdCard;
	private LocalDateTime dueDate; 
	private String issuedBy;

	public String getQrCodeStr() {
		return qrCodeStr;
	}

	public void setQrCodeStr(String qrCodeStr) {
		this.qrCodeStr = qrCodeStr;
	}

	public String getStudentIdCard() {
		return studentIdCard;
	}

	public void setStudentIdCard(String studentIdCard) {
		this.studentIdCard = studentIdCard;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

}