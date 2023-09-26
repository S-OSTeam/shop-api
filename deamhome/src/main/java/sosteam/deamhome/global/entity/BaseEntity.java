package sosteam.deamhome.global.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

public abstract class BaseEntity {
    @Id
    private String id;

    @CreatedDate
    private final LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    private final LocalDateTime updatedAt = LocalDateTime.now();
}
