package com.pote.library.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BorrowRequestDTO {
	private String qrCodeStr; // The scanned QR code
	private String studentIdCard; // The student's ID card number
	private LocalDateTime dueDate; // Optional: If admin wants to override default
	private String issuedBy; // Username of the librarian

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