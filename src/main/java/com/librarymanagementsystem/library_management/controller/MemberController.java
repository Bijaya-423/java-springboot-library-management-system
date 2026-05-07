package com.librarymanagementsystem.library_management.controller;

import com.librarymanagementsystem.library_management.dto.MemberDTO;
import com.librarymanagementsystem.library_management.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // ADMIN, LIBRARIAN
    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    // ADMIN, LIBRARIAN, OWN MEMBER
    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(
            @PathVariable Long id,
            Authentication authentication) {
        return ResponseEntity.ok(
            memberService.getMemberById(id, authentication.getName()));
    }

    // ADMIN only
    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember(
            @PathVariable Long id,
            @RequestBody MemberDTO dto) {
        return ResponseEntity.ok(memberService.updateMember(id, dto));
    }

    // ADMIN only
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok("Member deleted successfully");
    }
}