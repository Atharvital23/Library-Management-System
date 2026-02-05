package com.pote.library.entity;

import java.time.LocalDateTime;

import com.pote.library.enums.BorrowStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "borrow_transaction")
public class BorrowTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// --- IMPORTANT: Relationship Mapping ---
	// Links to the BookCopy entity. Allows you to see which specific copy was
	// taken.
	@ManyToOne
	@JoinColumn(name = "book_copy_id", nullable = false)
	private BookCopy bookCopy;

	// --- IMPORTANT: Relationship Mapping ---
	// Links to the Student entity. Allows you to see who took the book.
	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@Column(name = "issue_date")
	private LocalDateTime issueDate;

	@Column(name = "due_date", nullable = false)
	private LocalDateTime dueDate;

	@Column(name = "return_date")
	private LocalDateTime returnDate;

	@Column(name = "fine_amount")
	private Double fineAmount;

	// Uses the BookStatus Enum (ISSUED, RETURNED, LOST)
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BorrowStatus status;

	@Column(name = "issued_by", nullable = false)
	private String issuedBy; // Username of Librarian

	@Column(name = "received_by")
	private String receivedBy; // Username of Librarian

	// Maps MySQL TINYINT/BOOLEAN to Java boolean
	@Column(name = "is_fine_paid")
	private boolean isFinePaid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}

	public void setBookCopy(BookCopy bookCopy) {
		this.bookCopy = bookCopy;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public LocalDateTime getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDateTime issueDate) {
		this.issueDate = issueDate;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDateTime getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDateTime returnDate) {
		this.returnDate = returnDate;
	}

	public Double getFineAmount() {
		return fineAmount;
	}

	public void setFineAmount(Double fineAmount) {
		this.fineAmount = fineAmount;
	}

	public BorrowStatus getStatus() {
		return status;
	}

	public void setStatus(BorrowStatus status) {
		this.status = status;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public String getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	public boolean isFinePaid() {
		return isFinePaid;
	}

	public void setFinePaid(boolean isFinePaid) {
		this.isFinePaid = isFinePaid;
	}

}