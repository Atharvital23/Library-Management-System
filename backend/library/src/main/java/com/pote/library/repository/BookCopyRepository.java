package com.pote.library.repository;

import com.pote.library.entity.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
	// Custom query: Find specific copy by QR Code (used when scanning)
	Optional<BookCopy> findByQrCodeStr(String qrCodeStr);
}