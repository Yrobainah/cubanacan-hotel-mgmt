package dev.cubanacan.dto;

import java.math.BigDecimal;
import java.time.Instant;

import dev.cubanacan.domain.RoomStatus;
import dev.cubanacan.domain.RoomType;

public record RoomResponse(
        Long id,
        Long hotelId,
        String roomNumber,
        RoomType type,
        int capacity,
        BigDecimal pricePerNight,
        RoomStatus status,
        Instant createdAt,
        Instant updatedAt) {
}
