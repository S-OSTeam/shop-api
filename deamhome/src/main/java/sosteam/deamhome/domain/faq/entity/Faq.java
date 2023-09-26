package sosteam.deamhome.domain.faq.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import sosteam.deamhome.domain.account.entity.Account;
import sosteam.deamhome.global.entity.LogEntity;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Faq extends LogEntity {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String answer;

    @DBRef(lazy = true)
    private Account user;

    @DBRef(lazy = true)
    private Account admin;

    @DBRef
    private FaqCategory cat;
}
