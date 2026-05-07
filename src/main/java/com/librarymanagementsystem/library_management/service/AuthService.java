package com.librarymanagementsystem.library_management.service;

import com.librarymanagementsystem.library_management.dto.MemberDTO;
import com.librarymanagementsystem.library_management.exception.BadRequestException;
import com.librarymanagementsystem.library_management.model.Member;
import com.librarymanagementsystem.library_management.repository.MemberRepository;
import com.librarymanagementsystem.library_management.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(MemberRepository memberRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public MemberDTO register(MemberDTO dto) {
        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email already registered");
        }
        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPassword(passwordEncoder.encode(dto.getPassword()));
        member.setPhone(dto.getPhone());
        member.setAddress(dto.getAddress());
        member.setRole(dto.getRole() != null ? dto.getRole() : Member.Role.MEMBER);

        Member saved = memberRepository.save(member);

        MemberDTO result = new MemberDTO();
        result.setId(saved.getId());
        result.setName(saved.getName());
        result.setEmail(saved.getEmail());
        result.setRole(saved.getRole());
        return result;
    }

    public Map<String, String> login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }
        String token = jwtUtil.generateToken(email, member.getRole().name());
        return Map.of(
                "token", token,
                "role", member.getRole().name(),
                "name", member.getName()
        );
    }
}