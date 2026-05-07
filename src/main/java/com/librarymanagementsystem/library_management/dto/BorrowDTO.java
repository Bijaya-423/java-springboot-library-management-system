package com.librarymanagementsystem.library_management.dto;

import com.librarymanagementsystem.library_management.model.BorrowRecord;
import java.time.LocalDate;

public class BorrowDTO {

    private Long id;
    private Long bookId;
    private String bookTitle;
    private Long memberId;
    private String memberName;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private BorrowRecord.Status status;

    // Request fields
    private Long requestBookId;
    private Long requestMemberId;

    public BorrowDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }

    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }

    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public BorrowRecord.Status getStatus() { return status; }
    public void setStatus(BorrowRecord.Status status) { this.status = status; }

    public Long getRequestBookId() { return requestBookId; }
    public void setRequestBookId(Long requestBookId) { this.requestBookId = requestBookId; }

    public Long getRequestMemberId() { return requestMemberId; }
    public void setRequestMemberId(Long requestMemberId) { this.requestMemberId = requestMemberId; }
}