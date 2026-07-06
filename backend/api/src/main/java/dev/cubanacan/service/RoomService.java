package dev.cubanacan.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.cubanacan.domain.Hotel;
import dev.cubanacan.domain.Room;
import dev.cubanacan.dto.RoomRequest;
import dev.cubanacan.dto.RoomResponse;
import dev.cubanacan.exception.BadRequestException;
import dev.cubanacan.exception.DuplicateResourceException;
import dev.cubanacan.exception.ResourceNotFoundException;
import dev.cubanacan.mapper.RoomMapper;
import dev.cubanacan.repository.HotelRepository;
import dev.cubanacan.repository.RoomRepository;

@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;

    public RoomService(
            RoomRepository roomRepository,
            HotelRepository hotelRepository,
            RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.roomMapper = roomMapper;
    }

    public RoomResponse create(Long hotelId, RoomRequest request) {
        validateRequest(request);
        Hotel hotel = getHotel(hotelId);
        ensureRoomNumberAvailable(hotelId, request.roomNumber(), null);
        return roomMapper.toResponse(
                roomRepository.save(roomMapper.toEntity(request, hotel)));
    }

    @Transactional(readOnly = true)
    public List<RoomResponse> findByHotel(Long hotelId) {
        if (!hotelRepository.existsById(hotelId)) {
            throw new ResourceNotFoundException("Hotel not found with id " + hotelId);
        }
        return roomRepository.findAllByHotel_IdOrderByRoomNumberAsc(hotelId).stream()
                .map(roomMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoomResponse findById(Long roomId) {
        return roomMapper.toResponse(getRoom(roomId));
    }

    public RoomResponse update(Long roomId, RoomRequest request) {
        validateRequest(request);
        Room room = getRoom(roomId);
        ensureRoomNumberAvailable(
                room.getHotel().getId(),
                request.roomNumber(),
                roomId);
        roomMapper.updateEntity(room, request);
        return roomMapper.toResponse(roomRepository.save(room));
    }

    public void delete(Long roomId) {
        roomRepository.delete(getRoom(roomId));
    }

    private Hotel getHotel(Long hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Hotel not found with id " + hotelId));
    }

    private Room getRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Room not found with id " + roomId));
    }

    private void validateRequest(RoomRequest request) {
        if (request.capacity() == null || request.capacity() < 1) {
            throw new BadRequestException("Room capacity must be at least 1");
        }
        BigDecimal price = request.pricePerNight();
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Room price per night must be greater than 0");
        }
    }

    private void ensureRoomNumberAvailable(
            Long hotelId,
            String roomNumber,
            Long currentRoomId) {
        boolean duplicate = currentRoomId == null
                ? roomRepository.existsByHotel_IdAndRoomNumber(hotelId, roomNumber)
                : roomRepository.existsByHotel_IdAndRoomNumberAndIdNot(
                        hotelId,
                        roomNumber,
                        currentRoomId);
        if (duplicate) {
            throw new DuplicateResourceException(
                    "Room number " + roomNumber + " already exists in hotel " + hotelId);
        }
    }
}
