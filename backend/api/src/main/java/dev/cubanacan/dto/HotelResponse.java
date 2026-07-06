package dev.cubanacan.dto;

import java.time.Instant;

public record HotelResponse(
        Long id,
        String name,
        String city,
        String country,
        String address,
        int stars,
        Instant createdAt,
        Instant updatedAt) {
}
