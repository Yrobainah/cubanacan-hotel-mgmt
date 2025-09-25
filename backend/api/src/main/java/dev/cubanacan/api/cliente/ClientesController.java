package dev.cubanacan.api.cliente;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController {

    private final ClientesService service;

    public ClientesController(ClientesService service) {
        this.service = service;
    }

    @GetMapping
    public Page<ClientesDTO> list(@RequestParam(required = false) TipoCliente tipo, Pageable pageable) {
        return service.list(tipo, pageable);
    }

    @GetMapping("/{id}")
    public ClientesDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public ClientesDTO create(@Valid @RequestBody ClientesCreateDTO in) {
        return service.create(in);
    }
}
