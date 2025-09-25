package dev.cubanacan.api.cliente;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController {

    private final ClientesRepository repo;

    public ClientesController(ClientesRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Clientes> list(@RequestParam(required = false) TipoCliente tipo) {
        return (tipo != null) ? repo.findByTipoCliente(tipo) : repo.findAll();
    }

    @GetMapping("/{id}")
    public Clientes get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    public Clientes create(@Valid @RequestBody Clientes cliente) {
        return repo.save(cliente);
    }
}
