package dev.cubanacan.mapper;

import org.springframework.stereotype.Component;

import dev.cubanacan.domain.Hotel;
import dev.cubanacan.domain.Room;
import dev.cubanacan.dto.RoomRequest;
import dev.cubanacan.dto.RoomResponse;

@Component
public class RoomMapper {

    public Room toEntity(RoomRequest request, Hotel hotel) {
        return new Room(
                hotel,
                request.roomNumber(),
                request.type(),
                request.capacity(),
                request.pricePerNight(),
                request.status());
    }

    public void updateEntity(Room room, RoomRequest request) {
        room.update(
                request.roomNumber(),
                request.type(),
                request.capacity(),
                request.pricePerNight(),
                request.status());
    }

    public RoomResponse toResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getHotel().getId(),
                room.getRoomNumber(),
                room.getType(),
                room.getCapacity(),
                room.getPricePerNight(),
                room.getStatus(),
                room.getCreatedAt(),
                room.getUpdatedAt());
    }
}
