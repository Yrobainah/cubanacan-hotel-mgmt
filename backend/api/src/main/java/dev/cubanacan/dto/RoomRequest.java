package dev.cubanacan.dto;

import java.math.BigDecimal;

import dev.cubanacan.domain.RoomStatus;
import dev.cubanacan.domain.RoomType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RoomRequest(
        @NotBlank @Size(max = 20) String roomNumber,
        @NotNull RoomType type,
        @NotNull @Min(1) Integer capacity,
        @NotNull @DecimalMin(value = "0.0", inclusive = false)
        @Digits(integer = 10, fraction = 2) BigDecimal pricePerNight,
        @NotNull RoomStatus status) {
}
