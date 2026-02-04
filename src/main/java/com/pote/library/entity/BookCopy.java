package com.pote.library.entity;

import java.time.LocalDateTime;
import com.pote.library.enums.BookStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "book_copy")
public class BookCopy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// --- IMPORTANT: Relationship Mapping ---
	// Replaces 'int book_id'. This links the copy to its parent Book details.
	@ManyToOne
	@JoinColumn(name = "book_id", nullable = false)
	private Book book;

	@Column(name = "qr_code_str", nullable = false, unique = true)
	private String qrCodeStr;

	// Maps the Java Enum to the Database string (e.g., "AVAILABLE")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BookStatus status;

	@Column(name = "added_at")
	private LocalDateTime addedAt;

	@Column(name = "shelf_location")
	private String shelfLocation;

	@Column(name = "purchase_price")
	private Double purchasePrice;

	@Column(name = "condition_note")
	private String conditionNote;

	// Maps MySQL TINYINT(1)/BOOLEAN to Java boolean automatically
	@Column(name = "is_reference")
	private boolean isReference;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getQrCodeStr() {
		return qrCodeStr;
	}

	public void setQrCodeStr(String qrCodeStr) {
		this.qrCodeStr = qrCodeStr;
	}

	public BookStatus getStatus() {
		return status;
	}

	public void setStatus(BookStatus status) {
		this.status = status;
	}

	public LocalDateTime getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(LocalDateTime addedAt) {
		this.addedAt = addedAt;
	}

	public String getShelfLocation() {
		return shelfLocation;
	}

	public void setShelfLocation(String shelfLocation) {
		this.shelfLocation = shelfLocation;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getConditionNote() {
		return conditionNote;
	}

	public void setConditionNote(String conditionNote) {
		this.conditionNote = conditionNote;
	}

	public boolean isReference() {
		return isReference;
	}

	public void setReference(boolean isReference) {
		this.isReference = isReference;
	}

}