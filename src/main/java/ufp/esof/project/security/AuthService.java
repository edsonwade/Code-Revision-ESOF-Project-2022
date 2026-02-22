package ufp.esof.project.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityAuditService auditService;
    private final TotpService totpService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEnabled(true);
        user.setOrganizationId(1L);

        Role defaultRole = roleRepository.findByName("USER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("USER");
                    return roleRepository.save(role);
                });
        user.getRoles().add(defaultRole);

        user = userRepository.save(user);

        String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getEmail(), "USER");
        String refreshToken = createRefreshToken(user, request.getIpAddress(), request.getUserAgent());

        auditService.logAction(user.getId(), "REGISTER", null, null, request.getIpAddress(), request.getUserAgent(), null);

        return new AuthResponse(accessToken, refreshToken, user.getId(), user.getEmail());
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!user.getEnabled()) {
            throw new RuntimeException("Account is disabled");
        }

        if (!user.getAccountNonLocked()) {
            throw new RuntimeException("Account is locked");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getEmail(), getUserRole(user));
        String refreshToken = createRefreshToken(user, request.getIpAddress(), request.getUserAgent());

        auditService.logAction(user.getId(), "LOGIN", null, null, request.getIpAddress(), request.getUserAgent(), null);

        return new AuthResponse(accessToken, refreshToken, user.getId(), user.getEmail());
    }

    @Transactional
    public AuthResponse refreshToken(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException(" Invalid refresh token"));

        if (token.isRevoked() || token.isExpired()) {
            throw new RuntimeException("Refresh token expired or revoked");
        }

        User user = userRepository.findById(token.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        token.setRevoked(true);
        token.setRevokedAt(LocalDateTime.now());
        refreshTokenRepository.save(token);

        String newAccessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getEmail(), getUserRole(user));
        String newRefreshToken = createRefreshToken(user, token.getIpAddress(), token.getUserAgent());

        auditService.logAction(user.getId(), "TOKEN_REFRESH", null, null, token.getIpAddress(), token.getUserAgent(), null);

        return new AuthResponse(newAccessToken, newRefreshToken, user.getId(), user.getEmail());
    }

    @Transactional
    public void logout(Long userId, String token, String ipAddress, String userAgent) {
        refreshTokenRepository.deleteByUserIdAndRevokedTrue(userId);
        auditService.logAction(userId, "LOGOUT", null, null, ipAddress, userAgent, null);
    }

    @Transactional
    public void setup2fa(Long userId, String secret) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setTwoFactorSecret(secret);
        user.setTwoFactorEnabled(true);
        userRepository.save(user);
        auditService.logAction(userId, "2FA_ENABLED", null, null, null, null, "2FA setup completed");
    }

    @Transactional
    public boolean verify2fa(Long userId, String code) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (user.getTwoFactorSecret() == null) {
            throw new RuntimeException("2FA not set up for this user");
        }
        
        boolean valid = totpService.verifyCode(user.getTwoFactorSecret(), code);
        if (valid) {
            auditService.logAction(userId, "2FA_VERIFIED", null, null, null, null, "2FA code verified");
        } else {
            auditService.logAction(userId, "2FA_FAILED", null, null, null, null, "Invalid 2FA code");
        }
        return valid;
    }

    @Transactional
    public void disable2fa(Long userId, String code) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (user.getTwoFactorSecret() == null) {
            throw new RuntimeException("2FA not set up for this user");
        }
        
        if (!totpService.verifyCode(user.getTwoFactorSecret(), code)) {
            throw new RuntimeException("Invalid 2FA code");
        }
        
        user.setTwoFactorSecret(null);
        user.setTwoFactorEnabled(false);
        userRepository.save(user);
        auditService.logAction(userId, "2FA_DISABLED", null, null, null, null, "2FA disabled");
    }

    private String createRefreshToken(User user, String ipAddress, String userAgent) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(user.getId());
        refreshToken.setToken(jwtTokenProvider.generateRefreshToken(user.getId(), user.getEmail()));
        refreshToken.setExpiresAt(LocalDateTime.now().plusNanos(jwtTokenProvider.getRefreshTokenExpiration() * 1000000));
        refreshToken.setIpAddress(ipAddress);
        refreshToken.setUserAgent(userAgent);
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    private String getUserRole(User user) {
        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        return roles.contains("ADMIN") ? "ADMIN" : "USER";
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String ipAddress;
        private String userAgent;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String email;
        private String password;
        private String ipAddress;
        private String userAgent;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthResponse {
        private String accessToken;
        private String refreshToken;
        private Long userId;
        private String email;
    }
}
