package com.gazatem.ekip.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class SignUpRequest {
    private String username;
    private String password;
    private String email;
    private Set<String> role;
}
