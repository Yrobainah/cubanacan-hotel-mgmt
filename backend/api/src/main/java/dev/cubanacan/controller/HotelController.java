package dev.cubanacan.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cubanacan.dto.HotelRequest;
import dev.cubanacan.dto.HotelResponse;
import dev.cubanacan.service.HotelService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseEntity<HotelResponse> create(@Valid @RequestBody HotelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.create(request));
    }

    @GetMapping
    public List<HotelResponse> findAll() {
        return hotelService.findAll();
    }

    @GetMapping("/{hotelId}")
    public HotelResponse findById(@PathVariable Long hotelId) {
        return hotelService.findById(hotelId);
    }

    @PutMapping("/{hotelId}")
    public HotelResponse update(
            @PathVariable Long hotelId,
            @Valid @RequestBody HotelRequest request) {
        return hotelService.update(hotelId, request);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> delete(@PathVariable Long hotelId) {
        hotelService.delete(hotelId);
        return ResponseEntity.noContent().build();
    }
}
