package com.librarymanagementsystem.library_management.dto;

import com.librarymanagementsystem.library_management.model.Member;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MemberDTO {

    // id only shown in RESPONSE, not required in REQUEST
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String name;
    private String email;

    // password only in REQUEST, never shown in RESPONSE
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String phone;
    private String address;
    private Member.Role role;

    public MemberDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Member.Role getRole() { return role; }
    public void setRole(Member.Role role) { this.role = role; }
}