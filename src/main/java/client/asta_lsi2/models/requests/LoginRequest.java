package client.asta_lsi2.models.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
    private String role;

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}