package sosteam.deamhome.domain.item.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import sosteam.deamhome.domain.account.entity.Account;
import sosteam.deamhome.global.entity.LogEntity;

import java.util.List;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist extends LogEntity {

    @DBRef(lazy = true)
    @Setter
    private Account account;

    @DBRef(lazy = true)
    private List<Item> items;

    public List<Item> addItem(Item item) {
        items.add(item);
        return this.items;
    }
}
