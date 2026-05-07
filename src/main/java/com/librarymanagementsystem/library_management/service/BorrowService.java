package com.librarymanagementsystem.library_management.service;

import com.librarymanagementsystem.library_management.dto.BorrowDTO;
import com.librarymanagementsystem.library_management.exception.BadRequestException;
import com.librarymanagementsystem.library_management.exception.ResourceNotFoundException;
import com.librarymanagementsystem.library_management.model.*;
import com.librarymanagementsystem.library_management.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public BorrowService(BorrowRepository borrowRepository,
                         BookRepository bookRepository,
                         MemberRepository memberRepository) {
        this.borrowRepository = borrowRepository;
        this.bookRepository   = bookRepository;
        this.memberRepository = memberRepository;
    }

    // MEMBER — request to borrow a book (status = PENDING)
    public BorrowDTO requestBorrow(Long bookId, String memberEmail) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new BadRequestException("No copies available for this book");
        }

        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setMember(member);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14));
        record.setStatus(BorrowRecord.Status.PENDING);

        return toDTO(borrowRepository.save(record));
    }

    // LIBRARIAN — approve borrow request
    public BorrowDTO approveBorrow(Long borrowId) {
        BorrowRecord record = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found"));

        if (record.getStatus() != BorrowRecord.Status.PENDING) {
            throw new BadRequestException("Only PENDING requests can be approved");
        }

        Book book = record.getBook();
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        record.setStatus(BorrowRecord.Status.APPROVED);
        return toDTO(borrowRepository.save(record));
    }

    // LIBRARIAN — reject borrow request
    public BorrowDTO rejectBorrow(Long borrowId) {
        BorrowRecord record = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found"));

        if (record.getStatus() != BorrowRecord.Status.PENDING) {
            throw new BadRequestException("Only PENDING requests can be rejected");
        }

        record.setStatus(BorrowRecord.Status.REJECTED);
        return toDTO(borrowRepository.save(record));
    }

    // MEMBER — return a book
    public BorrowDTO returnBook(Long borrowId, String memberEmail) {
        BorrowRecord record = borrowRepository.findById(borrowId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found"));

        if (!record.getMember().getEmail().equals(memberEmail)) {
            throw new BadRequestException("You can only return your own borrowed books");
        }

        if (record.getStatus() == BorrowRecord.Status.RETURNED) {
            throw new BadRequestException("Book already returned");
        }

        Book book = record.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        record.setReturnDate(LocalDate.now());
        record.setStatus(BorrowRecord.Status.RETURNED);
        return toDTO(borrowRepository.save(record));
    }

    // MEMBER — own borrow history
    public List<BorrowDTO> getMyHistory(String memberEmail) {
        return borrowRepository.findByMemberEmail(memberEmail)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ADMIN, LIBRARIAN — all borrow records
    public List<BorrowDTO> getAllBorrows() {
        return borrowRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ADMIN, LIBRARIAN — get borrow by id
    public BorrowDTO getBorrowById(Long id) {
        BorrowRecord record = borrowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found"));
        return toDTO(record);
    }

    private BorrowDTO toDTO(BorrowRecord record) {
        BorrowDTO dto = new BorrowDTO();
        dto.setId(record.getId());
        dto.setBookId(record.getBook().getId());
        dto.setBookTitle(record.getBook().getTitle());
        dto.setMemberId(record.getMember().getId());
        dto.setMemberName(record.getMember().getName());
        dto.setBorrowDate(record.getBorrowDate());
        dto.setDueDate(record.getDueDate());
        dto.setReturnDate(record.getReturnDate());
        dto.setStatus(record.getStatus());
        return dto;
    }
}