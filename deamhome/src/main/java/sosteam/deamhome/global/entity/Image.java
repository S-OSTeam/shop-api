package sosteam.deamhome.global.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image extends BaseEntity {

    @NotNull
    private String fileName;

    @NotNull
    private String fileOriginName;

    @NotNull
    private int size;

    @NotNull
    private String type;

    @NotNull
    private String fileUrl;
}
