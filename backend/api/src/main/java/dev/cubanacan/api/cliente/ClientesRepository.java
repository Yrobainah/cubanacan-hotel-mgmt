package dev.cubanacan.api.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

public interface ClientesRepository extends JpaRepository<Clientes, Long> {

    Page<Clientes> findByTipoCliente(TipoCliente tipo, Pageable pageable);
}
