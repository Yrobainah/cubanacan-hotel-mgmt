package dev.cubanacan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.cubanacan.domain.Hotel;
import dev.cubanacan.dto.HotelRequest;
import dev.cubanacan.dto.HotelResponse;
import dev.cubanacan.exception.BadRequestException;
import dev.cubanacan.mapper.HotelMapper;
import dev.cubanacan.repository.HotelRepository;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    private HotelService hotelService;

    @BeforeEach
    void setUp() {
        hotelService = new HotelService(hotelRepository, new HotelMapper());
    }

    @Test
    void createsValidHotel() {
        HotelRequest request = new HotelRequest(
                "Hotel Nacional",
                "Havana",
                "Cuba",
                "Calle 21 y O",
                5);
        when(hotelRepository.save(any(Hotel.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        HotelResponse response = hotelService.create(request);

        assertThat(response.name()).isEqualTo("Hotel Nacional");
        assertThat(response.city()).isEqualTo("Havana");
        assertThat(response.stars()).isEqualTo(5);
    }

    @Test
    void rejectsHotelWithInvalidStars() {
        HotelRequest request = new HotelRequest(
                "Invalid Hotel",
                "Havana",
                "Cuba",
                "Unknown",
                6);

        assertThatThrownBy(() -> hotelService.create(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Hotel stars must be between 1 and 5");
        verifyNoInteractions(hotelRepository);
    }
}
