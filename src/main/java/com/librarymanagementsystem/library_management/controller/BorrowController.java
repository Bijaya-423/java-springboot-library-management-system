package com.librarymanagementsystem.library_management.controller;

import com.librarymanagementsystem.library_management.dto.BorrowDTO;
import com.librarymanagementsystem.library_management.service.BorrowService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    // MEMBER — request borrow
    // POST /api/borrow?bookId=1
    @PostMapping
    public ResponseEntity<BorrowDTO> requestBorrow(
            @RequestParam Long bookId,
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(borrowService.requestBorrow(bookId, email));
    }

    // MEMBER — return book
    // PUT /api/borrow/return/1
    @PutMapping("/return/{borrowId}")
    public ResponseEntity<BorrowDTO> returnBook(
            @PathVariable Long borrowId,
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(borrowService.returnBook(borrowId, email));
    }

    // MEMBER — my borrow history
    // GET /api/borrow/history
    @GetMapping("/history")
    public ResponseEntity<List<BorrowDTO>> getMyHistory(
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(borrowService.getMyHistory(email));
    }

    // ADMIN, LIBRARIAN — all records
    // GET /api/borrow/all
    @GetMapping("/all")
    public ResponseEntity<List<BorrowDTO>> getAllBorrows() {
        return ResponseEntity.ok(borrowService.getAllBorrows());
    }

    // ADMIN, LIBRARIAN — get by id
    // GET /api/borrow/1
    @GetMapping("/{id}")
    public ResponseEntity<BorrowDTO> getBorrowById(@PathVariable Long id) {
        return ResponseEntity.ok(borrowService.getBorrowById(id));
    }

    // LIBRARIAN — approve
    // PUT /api/borrow/approve/1
    @PutMapping("/approve/{id}")
    public ResponseEntity<BorrowDTO> approveBorrow(@PathVariable Long id) {
        return ResponseEntity.ok(borrowService.approveBorrow(id));
    }

    // LIBRARIAN — reject
    // PUT /api/borrow/reject/1
    @PutMapping("/reject/{id}")
    public ResponseEntity<BorrowDTO> rejectBorrow(@PathVariable Long id) {
        return ResponseEntity.ok(borrowService.rejectBorrow(id));
    }
}