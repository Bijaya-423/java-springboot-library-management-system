package com.librarymanagementsystem.library_management.controller;

import com.librarymanagementsystem.library_management.dto.LoginDTO;
import com.librarymanagementsystem.library_management.dto.MemberDTO;
import com.librarymanagementsystem.library_management.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@SecurityRequirements  // ← removes lock icon from all auth endpoints
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<MemberDTO> register(@RequestBody MemberDTO dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(
                loginDTO.getEmail(),
                loginDTO.getPassword()
        ));
    }
}