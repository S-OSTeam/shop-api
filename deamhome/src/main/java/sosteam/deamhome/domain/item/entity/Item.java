package sosteam.deamhome.domain.item.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import sosteam.deamhome.domain.account.entity.Account;
import sosteam.deamhome.domain.category.entity.ItemCategory;
import sosteam.deamhome.domain.category.entity.ItemDetailCategory;
import sosteam.deamhome.global.entity.Image;
import sosteam.deamhome.global.entity.LogEntity;

import java.util.List;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item extends LogEntity {

    @NotNull
    private String title;

    private String content;

    private String summary;

    private int price = 0;

    private int sellCnt = 0;

    private int wishCnt = 0;

    private int clickCnt = 0;

    private Double avgReview = 0.0;

    private int reviewCnt = 0;

    private int qnaCnt = 0;
    
    private boolean status;

    @DBRef(lazy = true)
    @Setter
    private Account account;

    @DBRef(lazy = true)
    private List<Image> images;

    @DBRef(lazy = true)
    @Setter
    private ItemCategory itemCategory;

    @DBRef(lazy = true)
    @Setter
    private ItemDetailCategory itemDetailCategory;

    public List<Image> addImage(Image image) {
        images.add(image);

        return images;
    }
}
