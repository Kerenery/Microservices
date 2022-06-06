package ru.itmo.kotiki.dto;

import java.io.Serializable;

public class KotikiFriendsPayload implements Serializable {
    private Long firstKotikId;
    private Long secondKotikId;

    public KotikiFriendsPayload(Long firstKotikId, Long secondKotikId) {
        this.firstKotikId = firstKotikId;
        this.secondKotikId = secondKotikId;
    }

    public Long getFirstKotikId() {
        return firstKotikId;
    }

    public Long getSecondKotikId() {
        return secondKotikId;
    }
}
