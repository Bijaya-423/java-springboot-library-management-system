//package com.librarymanagementsystem.library_management.service;
//
//import com.librarymanagementsystem.library_management.dto.MemberDTO;
//import com.librarymanagementsystem.library_management.exception.BadRequestException;
//import com.librarymanagementsystem.library_management.exception.ResourceNotFoundException;
//import com.librarymanagementsystem.library_management.model.Member;
//import com.librarymanagementsystem.library_management.repository.MemberRepository;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class MemberService {
//
//    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public MemberService(MemberRepository memberRepository,
//                         PasswordEncoder passwordEncoder) {
//        this.memberRepository = memberRepository;
//        this.passwordEncoder  = passwordEncoder;
//    }
//
//    public List<MemberDTO> getAllMembers() {
//        return memberRepository.findAll()
//                .stream().map(this::toDTO).collect(Collectors.toList());
//    }
//
//    // ADMIN & LIBRARIAN can see anyone
//    // MEMBER can only see their own profile
//    public MemberDTO getMemberById(Long id, String loggedInEmail) {
//        Member member = memberRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Member not found: " + id));
//
//        Member loggedIn = memberRepository.findByEmail(loggedInEmail)
//                .orElseThrow(() -> new ResourceNotFoundException("Logged in member not found"));
//
//        // If MEMBER role — only allow own profile
//        if (loggedIn.getRole() == Member.Role.MEMBER
//                && !loggedIn.getId().equals(id)) {
//            throw new BadRequestException("Access denied. You can only view your own profile");
//        }
//
//        return toDTO(member);
//    }
//
//    public MemberDTO updateMember(Long id, MemberDTO dto) {
//        Member member = memberRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Member not found: " + id));
//        member.setName(dto.getName());
//        member.setPhone(dto.getPhone());
//        member.setAddress(dto.getAddress());
//        return toDTO(memberRepository.save(member));
//    }
//
//    public void deleteMember(Long id) {
//        if (!memberRepository.existsById(id)) {
//            throw new ResourceNotFoundException("Member not found: " + id);
//        }
//        memberRepository.deleteById(id);
//    }
//
//    private MemberDTO toDTO(Member member) {
//        MemberDTO dto = new MemberDTO();
//        dto.setId(member.getId());
//        dto.setName(member.getName());
//        dto.setEmail(member.getEmail());
//        dto.setPhone(member.getPhone());
//        dto.setAddress(member.getAddress());
//        dto.setRole(member.getRole());
//        return dto;
//    }
//}

package com.librarymanagementsystem.library_management.service;

import com.librarymanagementsystem.library_management.dto.MemberDTO;
import com.librarymanagementsystem.library_management.exception.BadRequestException;
import com.librarymanagementsystem.library_management.exception.ResourceNotFoundException;
import com.librarymanagementsystem.library_management.model.Member;
import com.librarymanagementsystem.library_management.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository,
                         PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder  = passwordEncoder;
    }

    // ADMIN & LIBRARIAN — see all members including ADMIN
    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ADMIN    → can see anyone
    // LIBRARIAN → can see anyone including ADMIN
    // MEMBER   → can only see own profile
    public MemberDTO getMemberById(Long id, String loggedInEmail) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Member not found: " + id));

        Member loggedIn = memberRepository.findByEmail(loggedInEmail)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Logged in member not found"));

        // MEMBER role — only own profile allowed
        if (loggedIn.getRole() == Member.Role.MEMBER
                && !loggedIn.getId().equals(id)) {
            throw new BadRequestException(
                "Access denied. You can only view your own profile.");
        }

        // ADMIN and LIBRARIAN — can see everyone ✅
        return toDTO(member);
    }

    public MemberDTO updateMember(Long id, MemberDTO dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Member not found: " + id));
        member.setName(dto.getName());
        member.setPhone(dto.getPhone());
        member.setAddress(dto.getAddress());
        return toDTO(memberRepository.save(member));
    }

    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Member not found: " + id);
        }
        memberRepository.deleteById(id);
    }

    private MemberDTO toDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setName(member.getName());
        dto.setEmail(member.getEmail());
        dto.setPhone(member.getPhone());
        dto.setAddress(member.getAddress());
        dto.setRole(member.getRole());
        return dto;
    }
}

