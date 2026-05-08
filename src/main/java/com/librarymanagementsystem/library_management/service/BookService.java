package com.librarymanagementsystem.library_management.service;

import com.librarymanagementsystem.library_management.dto.BookDTO;
import com.librarymanagementsystem.library_management.exception.BadRequestException;
import com.librarymanagementsystem.library_management.exception.ResourceNotFoundException;
import com.librarymanagementsystem.library_management.model.Book;
import com.librarymanagementsystem.library_management.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Book not found: " + id));
        return toDTO(book);
    }

    public List<BookDTO> searchBooks(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<BookDTO> getBooksByCategory(String category) {
        return bookRepository.findByCategory(category)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public BookDTO addBook(BookDTO dto) {
        // Check duplicate ISBN
        if (bookRepository.findByIsbn(dto.getIsbn()).isPresent()) {
            throw new BadRequestException(
                "Book with ISBN " + dto.getIsbn() + " already exists");
        }

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setCategory(dto.getCategory());
        book.setPublisher(dto.getPublisher());
        book.setTotalCopies(dto.getTotalCopies());
        book.setAvailableCopies(dto.getTotalCopies()); // auto set

        return toDTO(bookRepository.save(book));
    }

    public BookDTO updateBook(Long id, BookDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Book not found: " + id));
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setCategory(dto.getCategory());
        book.setPublisher(dto.getPublisher());
        book.setTotalCopies(dto.getTotalCopies());
        return toDTO(bookRepository.save(book));
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found: " + id);
        }
        bookRepository.deleteById(id);
    }

    private BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setCategory(book.getCategory());
        dto.setPublisher(book.getPublisher());
        dto.setTotalCopies(book.getTotalCopies());
        dto.setAvailableCopies(book.getAvailableCopies());
        return dto;
    }
}