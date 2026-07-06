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

import dev.cubanacan.dto.RoomRequest;
import dev.cubanacan.dto.RoomResponse;
import dev.cubanacan.service.RoomService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/hotels/{hotelId}/rooms")
    public ResponseEntity<RoomResponse> create(
            @PathVariable Long hotelId,
            @Valid @RequestBody RoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roomService.create(hotelId, request));
    }

    @GetMapping("/hotels/{hotelId}/rooms")
    public List<RoomResponse> findByHotel(@PathVariable Long hotelId) {
        return roomService.findByHotel(hotelId);
    }

    @GetMapping("/rooms/{roomId}")
    public RoomResponse findById(@PathVariable Long roomId) {
        return roomService.findById(roomId);
    }

    @PutMapping("/rooms/{roomId}")
    public RoomResponse update(
            @PathVariable Long roomId,
            @Valid @RequestBody RoomRequest request) {
        return roomService.update(roomId, request);
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<Void> delete(@PathVariable Long roomId) {
        roomService.delete(roomId);
        return ResponseEntity.noContent().build();
    }
}
