package dev.cubanacan.api.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientesCreateDTO(@NotBlank String nombre,
        @NotBlank String apellidos,
        String ci,
        Sexo sexo,
        @NotBlank TipoCliente tipoCliente,
        String cargoRango,
        @NotNull Long paisOrigenId) {
}
