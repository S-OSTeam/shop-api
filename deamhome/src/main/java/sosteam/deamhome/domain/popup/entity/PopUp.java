package sosteam.deamhome.domain.popup.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import sosteam.deamhome.domain.account.entity.Account;
import sosteam.deamhome.global.entity.BaseEntity;

import java.time.LocalDateTime;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PopUp extends BaseEntity {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String ip;

    //팝업 활성화 여부
    @NotNull
    private boolean isOnBoard;

    @NotNull
    @Setter
    private LocalDateTime endAt;

    @DBRef
    @Setter
    private Account account;
}
