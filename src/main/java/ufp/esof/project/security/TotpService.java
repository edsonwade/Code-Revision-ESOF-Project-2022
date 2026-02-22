package ufp.esof.project.security;

import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import org.springframework.stereotype.Service;

@Service
public class TotpService {

    private final CodeGenerator codeGenerator;
    private final TimeProvider timeProvider;
    private final SecretGenerator secretGenerator;

    public TotpService() {
        this.codeGenerator = new DefaultCodeGenerator(HashingAlgorithm.SHA1, 6);
        this.timeProvider = new SystemTimeProvider();
        this.secretGenerator = new DefaultSecretGenerator();
    }

    public String generateSecret() {
        return secretGenerator.generate();
    }

    public String generateQrCodeUrl(String secret, String email, String issuer) {
        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s",
                issuer, email, secret, issuer);
    }

    public boolean verifyCode(String secret, String code) {
        DefaultCodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        verifier.setAllowedTimePeriodDiscrepancy(1);
        return verifier.isValidCode(secret, code);
    }
}
