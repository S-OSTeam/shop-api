package sosteam.deamhome.domain.order.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import sosteam.deamhome.domain.account.entity.Account;
import sosteam.deamhome.domain.item.entity.Item;

import java.util.List;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @NotNull
    private String address;

    @NotNull
    private String userName;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    private String adminMemo;
    private String content;
    private boolean isPaid = false;
    private boolean isRefund = false;

    @DBRef(lazy = true)
    private Account account;

    @DBRef(lazy = true)
    private List<Item> items;

    public List<Item> addItem(Item item) {
        items.add(item);

        return items;
    }
}
