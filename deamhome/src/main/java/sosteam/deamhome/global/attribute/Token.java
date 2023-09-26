package sosteam.deamhome.global.attribute;

import lombok.Getter;

@Getter
public enum Token {
    ACCESS(
            1000 * 60 * 60 * 12,
            "accessToken"
    ),
    REFRESH(
            1000 * 60 * 60 * 24 * 14,
            "refreshToken"
    );

    private final Long milliseconds;
    private final String type;

    Token(int time, String type) {
        this.milliseconds = Long.valueOf(time);
        this.type = type;
    }
}
