package dev.cubanacan.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.cubanacan.domain.Hotel;
import dev.cubanacan.dto.HotelRequest;
import dev.cubanacan.dto.HotelResponse;
import dev.cubanacan.exception.BadRequestException;
import dev.cubanacan.exception.ResourceNotFoundException;
import dev.cubanacan.mapper.HotelMapper;
import dev.cubanacan.repository.HotelRepository;

@Service
@Transactional
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelService(HotelRepository hotelRepository, HotelMapper hotelMapper) {
        this.hotelRepository = hotelRepository;
        this.hotelMapper = hotelMapper;
    }

    public HotelResponse create(HotelRequest request) {
        validateStars(request.stars());
        return hotelMapper.toResponse(hotelRepository.save(hotelMapper.toEntity(request)));
    }

    @Transactional(readOnly = true)
    public List<HotelResponse> findAll() {
        return hotelRepository.findAll(Sort.by(Sort.Direction.ASC, "name")).stream()
                .map(hotelMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public HotelResponse findById(Long hotelId) {
        return hotelMapper.toResponse(getHotel(hotelId));
    }

    public HotelResponse update(Long hotelId, HotelRequest request) {
        validateStars(request.stars());
        Hotel hotel = getHotel(hotelId);
        hotelMapper.updateEntity(hotel, request);
        return hotelMapper.toResponse(hotelRepository.save(hotel));
    }

    public void delete(Long hotelId) {
        hotelRepository.delete(getHotel(hotelId));
    }

    private Hotel getHotel(Long hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Hotel not found with id " + hotelId));
    }

    private void validateStars(Integer stars) {
        if (stars == null || stars < 1 || stars > 5) {
            throw new BadRequestException("Hotel stars must be between 1 and 5");
        }
    }
}
