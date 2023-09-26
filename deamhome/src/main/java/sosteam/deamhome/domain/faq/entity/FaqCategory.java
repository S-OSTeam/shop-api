package sosteam.deamhome.domain.faq.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import sosteam.deamhome.global.entity.LogEntity;

import java.util.List;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaqCategory extends LogEntity {
    @NotNull
    private String title;

    @DBRef(lazy = true)
    private List<Faq> faqs;

    public List<Faq> addFaq(Faq faq) {
        faqs.add(faq);

        return this.faqs;
    }
}
