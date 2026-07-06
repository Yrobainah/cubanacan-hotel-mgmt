package dev.cubanacan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cubanacan.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByHotel_IdOrderByRoomNumberAsc(Long hotelId);

    boolean existsByHotel_IdAndRoomNumber(Long hotelId, String roomNumber);

    boolean existsByHotel_IdAndRoomNumberAndIdNot(
            Long hotelId,
            String roomNumber,
            Long roomId);
}
