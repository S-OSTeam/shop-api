package sosteam.deamhome.domain.cart.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import sosteam.deamhome.domain.account.entity.Account;
import sosteam.deamhome.global.entity.LogEntity;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends LogEntity {

    @DBRef(lazy = true)
    @Setter
    private Account account;
}
