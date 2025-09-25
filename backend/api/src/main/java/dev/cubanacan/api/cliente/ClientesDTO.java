package dev.cubanacan.api.cliente;

public record ClientesDTO(Long id,
        String nombre,
        String apellidos,
        String ci,
        Sexo sexo,
        String cargoRango,
        Long paisOrigenId,
        String paisOrigenNombre) {
}
