package sosteam.deamhome.domain.review.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import sosteam.deamhome.domain.account.entity.Account;
import sosteam.deamhome.domain.item.entity.Item;
import sosteam.deamhome.global.entity.Image;
import sosteam.deamhome.global.entity.LogEntity;

import java.util.List;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review extends LogEntity {

    private String title;
    private String content;
    private int like;
    private double score;
    private boolean status;

    @DBRef(lazy = true)
    private List<Image> images;

    @DBRef(lazy = true)
    @Setter
    private Item item;

    @DBRef(lazy = true)
    @Setter
    private Account account;

    public List<Image> addImage(Image image) {
        images.add(image);

        return images;
    }
}
