package dev.cubanacan.api.cliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientesRepository extends JpaRepository<Clientes, Long> {
    List<Clientes> findByTipoCliente(TipoCliente tipo);
}
