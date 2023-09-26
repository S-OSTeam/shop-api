package sosteam.deamhome.domain.category.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import sosteam.deamhome.domain.item.entity.Item;
import sosteam.deamhome.global.entity.BaseEntity;

import java.util.List;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailCategory extends BaseEntity {

    @NotNull
    private String title;

    @DBRef(lazy = true)
    private List<Item> items;

    @DBRef(lazy = true)
    private List<ItemCategory> itemCategories;
}
