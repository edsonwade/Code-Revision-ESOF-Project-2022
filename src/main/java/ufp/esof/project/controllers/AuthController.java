package ufp.esof.project.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufp.esof.project.security.AuthService;
import ufp.esof.project.security.TotpService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TotpService totpService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            AuthService.AuthResponse response = authService.register(
                    new AuthService.RegisterRequest(
                            request.email(),
                            request.password(),
                            request.firstName(),
                            request.lastName(),
                            request.ipAddress(),
                            request.userAgent()
                    )
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthService.AuthResponse response = authService.login(
                    new AuthService.LoginRequest(
                            request.email(),
                            request.password(),
                            request.ipAddress(),
                            request.userAgent()
                    )
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        try {
            String refreshToken = request.get("refreshToken");
            AuthService.AuthResponse response = authService.refreshToken(refreshToken);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> request) {
        try {
            Long userId = Long.parseLong(request.get("userId"));
            String token = request.get("token");
            authService.logout(userId, token, request.get("ipAddress"), request.get("userAgent"));
            return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/2fa/setup")
    public ResponseEntity<?> setup2fa(@RequestBody Map<String, String> request) {
        try {
            Long userId = Long.parseLong(request.get("userId"));
            String secret = totpService.generateSecret();
            String qrCodeUrl = totpService.generateQrCodeUrl(secret, request.get("email"), "ESOF-App");
            authService.setup2fa(userId, secret);
            return ResponseEntity.ok(Map.of("secret", secret, "qrCodeUrl", qrCodeUrl));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/2fa/verify")
    public ResponseEntity<?> verify2fa(@RequestBody Map<String, String> request) {
        try {
            Long userId = Long.parseLong(request.get("userId"));
            String code = request.get("code");
            boolean valid = authService.verify2fa(userId, code);
            if (valid) {
                return ResponseEntity.ok(Map.of("message", "2FA verified successfully", "verified", true));
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid 2FA code", "verified", false));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/2fa/disable")
    public ResponseEntity<?> disable2fa(@RequestBody Map<String, String> request) {
        try {
            Long userId = Long.parseLong(request.get("userId"));
            String code = request.get("code");
            authService.disable2fa(userId, code);
            return ResponseEntity.ok(Map.of("message", "2FA disabled successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    public record RegisterRequest(String email, String password, String firstName, String lastName, String ipAddress, String userAgent) {}
    public record LoginRequest(String email, String password, String ipAddress, String userAgent) {}
}
