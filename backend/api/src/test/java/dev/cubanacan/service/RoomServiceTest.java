package dev.cubanacan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.cubanacan.domain.Hotel;
import dev.cubanacan.domain.Room;
import dev.cubanacan.domain.RoomStatus;
import dev.cubanacan.domain.RoomType;
import dev.cubanacan.dto.RoomRequest;
import dev.cubanacan.dto.RoomResponse;
import dev.cubanacan.exception.DuplicateResourceException;
import dev.cubanacan.mapper.RoomMapper;
import dev.cubanacan.repository.HotelRepository;
import dev.cubanacan.repository.RoomRepository;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private HotelRepository hotelRepository;

    private RoomService roomService;
    private Hotel hotel;

    @BeforeEach
    void setUp() {
        roomService = new RoomService(
                roomRepository,
                hotelRepository,
                new RoomMapper());
        hotel = new Hotel(
                "Hotel Nacional",
                "Havana",
                "Cuba",
                "Calle 21 y O",
                5);
    }

    @Test
    void createsValidRoom() {
        RoomRequest request = validRoomRequest();
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(roomRepository.existsByHotel_IdAndRoomNumber(1L, "101"))
                .thenReturn(false);
        when(roomRepository.save(any(Room.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RoomResponse response = roomService.create(1L, request);

        assertThat(response.roomNumber()).isEqualTo("101");
        assertThat(response.type()).isEqualTo(RoomType.DOUBLE);
        assertThat(response.capacity()).isEqualTo(2);
        assertThat(response.pricePerNight()).isEqualByComparingTo("125.50");
        assertThat(response.status()).isEqualTo(RoomStatus.AVAILABLE);
    }

    @Test
    void rejectsDuplicateRoomNumberWithinHotel() {
        RoomRequest request = validRoomRequest();
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(roomRepository.existsByHotel_IdAndRoomNumber(1L, "101"))
                .thenReturn(true);

        assertThatThrownBy(() -> roomService.create(1L, request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Room number 101 already exists in hotel 1");
        verify(roomRepository, never()).save(any(Room.class));
    }

    private RoomRequest validRoomRequest() {
        return new RoomRequest(
                "101",
                RoomType.DOUBLE,
                2,
                new BigDecimal("125.50"),
                RoomStatus.AVAILABLE);
    }
}
