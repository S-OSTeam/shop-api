package sosteam.deamhome.domain.auth.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import sosteam.deamhome.global.entity.LogEntity;

import java.time.LocalDateTime;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthMail extends LogEntity {
    @NotNull
    private String authCode;

    @NotNull
    @Setter
    private LocalDateTime endAt;

    @NotNull
    private String email;

    @NotNull
    @Setter
    private boolean isSuccess;


    public AuthMail(String email, String authCode, LocalDateTime endAt) {
        this.authCode = authCode;
        this.endAt = endAt;
        this.email = email;
        this.isSuccess = false;
    }
}
