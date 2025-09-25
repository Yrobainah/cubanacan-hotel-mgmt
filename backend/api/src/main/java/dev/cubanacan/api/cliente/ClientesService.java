package dev.cubanacan.api.cliente;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import dev.cubanacan.api.pais.Pais;
import dev.cubanacan.api.pais.PaisRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class ClientesService {
    private final ClientesRepository repo;
    private final PaisRepository paisesRepo;

    public ClientesService(ClientesRepository repo, PaisRepository paisesRepo) {
        this.repo = repo;
        this.paisesRepo = paisesRepo;
    }

    public Page<ClientesDTO> list(TipoCliente tipo, Pageable pageable) {
        Page<Clientes> page = (tipo != null)
                ? repo.findByTipoCliente(tipo, pageable)
                : repo.findAll(pageable);
        return page.map(this::toDTO);
    }

    public ClientesDTO get(Long id) {
        return repo.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Cliente con ID: " + id + " no encontrado"));
    }

    public ClientesDTO create(@Valid ClientesCreateDTO in) {
        if (in.tipoCliente() == TipoCliente.VIP && (in.cargoRango() == null || in.cargoRango().isBlank())) {
            throw new RuntimeException("Cargo o Rango es obligatorio para clientes VIP");

        }

        Pais pais = paisesRepo.findById(in.paisOrigenId())
                .orElseThrow(() -> new EntityNotFoundException("País con ID: " + in.paisOrigenId() + " no encontrado"));

        Clientes cliente = Clientes.builder()
                .nombre(in.nombre())
                .apellidos(in.apellidos())
                .ci(in.ci())
                .sexo(in.sexo())
                .tipoCliente(in.tipoCliente())
                .cargoRango(in.cargoRango())
                .paisOrigen(pais)
                .build();

        Clientes saved = repo.save(cliente);
        return toDTO(saved);
    }

    private ClientesDTO toDTO(Clientes c) {
        Long paisID = c.getPaisOrigen() != null ? c.getPaisOrigen().getId().longValue() : null;
        String paisNombre = c.getPaisOrigen() != null ? c.getPaisOrigen().getNombre() : null;
        return new ClientesDTO(
                c.getId(),
                c.getNombre(),
                c.getApellidos(),
                c.getCi(),
                c.getSexo(),
                c.getCargoRango(),
                paisID,
                paisNombre);
    }
}
