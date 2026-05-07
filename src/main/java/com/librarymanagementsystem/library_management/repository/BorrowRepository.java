package com.librarymanagementsystem.library_management.repository;

import com.librarymanagementsystem.library_management.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByMemberId(Long memberId);
    List<BorrowRecord> findByBookId(Long bookId);
    List<BorrowRecord> findByStatus(BorrowRecord.Status status);
    List<BorrowRecord> findByMemberEmail(String email);
}