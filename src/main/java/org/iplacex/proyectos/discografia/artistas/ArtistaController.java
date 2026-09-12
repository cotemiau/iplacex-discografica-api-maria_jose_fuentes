package org.iplacex.proyectos.discografia.artistas;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    private final IArtistaRepository artistaRepository;

    public ArtistaController(IArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    // Crear un artista
    @PostMapping(
        value = "/artista",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleInsertArtistaRequest(
            @RequestBody Artista artista) {

        Artista nuevoArtista = artistaRepository.save(artista);

        return new ResponseEntity<>(
            nuevoArtista,
            HttpStatus.CREATED
        );
    }

    // Obtener todos los artistas
    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest() {

        List<Artista> artistas = artistaRepository.findAll();

        return new ResponseEntity<>(
            artistas,
            HttpStatus.OK
        );
    }

    // Obtener un artista por ID
    @GetMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleGetArtistaRequest(
            @PathVariable String id) {

        Optional<Artista> artista = artistaRepository.findById(id);

        if (artista.isPresent()) {
            return new ResponseEntity<>(
                artista.get(),
                HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
            HttpStatus.NOT_FOUND
        );
    }

    // Actualizar un artista
    @PutMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleUpdateArtistaRequest(
            @PathVariable String id,
            @RequestBody Artista artista) {

        if (!artistaRepository.existsById(id)) {
            return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
            );
        }

        artista._id = id;

        Artista artistaActualizado = artistaRepository.save(artista);

        return new ResponseEntity<>(
            artistaActualizado,
            HttpStatus.OK
        );
    }

    // Eliminar un artista
    @DeleteMapping(
        value = "/artista/{id}"
    )
    public ResponseEntity<Void> HandleDeleteArtistaRequest(
            @PathVariable String id) {

        if (!artistaRepository.existsById(id)) {
            return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
            );
        }

        artistaRepository.deleteById(id);

        return new ResponseEntity<>(
            HttpStatus.NO_CONTENT
        );
    }
}