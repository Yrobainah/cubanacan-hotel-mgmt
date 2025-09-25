package dev.cubanacan.api.pais;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/paises")
public class PaisController {

    private final PaisRepository repo;

    public PaisController(PaisRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Pais> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Pais get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

}
