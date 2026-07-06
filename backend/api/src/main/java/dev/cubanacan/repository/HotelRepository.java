package dev.cubanacan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cubanacan.domain.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
