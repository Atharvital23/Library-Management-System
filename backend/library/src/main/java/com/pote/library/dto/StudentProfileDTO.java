package com.pote.library.dto;

import com.pote.library.entity.BorrowTransaction;
import com.pote.library.entity.Student;
import lombok.Data;
import java.util.List;

@Data
public class StudentProfileDTO {
	private Student studentDetails;
	private List<BorrowTransaction> transactionHistory;
	private Double totalFineOwed;

	public StudentProfileDTO() {

	}

	public Student getStudentDetails() {
		return studentDetails;
	}

	public void setStudentDetails(Student studentDetails) {
		this.studentDetails = studentDetails;
	}

	public List<BorrowTransaction> getTransactionHistory() {
		return transactionHistory;
	}

	public void setTransactionHistory(List<BorrowTransaction> transactionHistory) {
		this.transactionHistory = transactionHistory;
	}

	public Double getTotalFineOwed() {
		return totalFineOwed;
	}

	public void setTotalFineOwed(Double totalFineOwed) {
		this.totalFineOwed = totalFineOwed;
	}

}