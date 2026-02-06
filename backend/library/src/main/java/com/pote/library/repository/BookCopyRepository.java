package com.pote.library.repository;

import com.pote.library.entity.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

	/**
	 * Find specific copy by QR Code (used when scanning)
	 * 
	 * @param qrCodeStr
	 * 
	 *                  return the BookCopy object
	 * @return
	 */
	Optional<BookCopy> findByQrCodeStr(String qrCodeStr);
}