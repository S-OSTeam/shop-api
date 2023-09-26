package sosteam.deamhome.global.entity;

import jakarta.validation.constraints.NotNull;

public abstract class LogEntity extends BaseEntity {
    @NotNull
    private String ip;

    @NotNull
    private String userAgent;

    @NotNull
    private String referer;
}
