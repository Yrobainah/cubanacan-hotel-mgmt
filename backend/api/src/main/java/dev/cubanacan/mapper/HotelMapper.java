package dev.cubanacan.mapper;

import org.springframework.stereotype.Component;

import dev.cubanacan.domain.Hotel;
import dev.cubanacan.dto.HotelRequest;
import dev.cubanacan.dto.HotelResponse;

@Component
public class HotelMapper {

    public Hotel toEntity(HotelRequest request) {
        return new Hotel(
                request.name(),
                request.city(),
                request.country(),
                request.address(),
                request.stars());
    }

    public void updateEntity(Hotel hotel, HotelRequest request) {
        hotel.update(
                request.name(),
                request.city(),
                request.country(),
                request.address(),
                request.stars());
    }

    public HotelResponse toResponse(Hotel hotel) {
        return new HotelResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getCity(),
                hotel.getCountry(),
                hotel.getAddress(),
                hotel.getStars(),
                hotel.getCreatedAt(),
                hotel.getUpdatedAt());
    }
}
