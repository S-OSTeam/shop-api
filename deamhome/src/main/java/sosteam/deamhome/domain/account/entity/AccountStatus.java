package sosteam.deamhome.domain.account.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import sosteam.deamhome.global.attribute.Status;
import sosteam.deamhome.global.entity.BaseEntity;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatus extends BaseEntity {
    @NotNull
    @Indexed(unique = true)
    private String userId;

    @NotNull
    @Indexed(unique = true)
    private String snsId;

    @NotNull
    private Status status = Status.LIVE;

    @DBRef(lazy = true)
    @Setter
    private Account account;
}
