    package org.iplacex.proyectos.discografia.discos;

    import java.util.List;
    import java.util.Optional;

    import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.CrossOrigin;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    @CrossOrigin
    @RequestMapping("/api")
    public class DiscoController {

        private final IDiscoRepository discoRepository;
        private final IArtistaRepository artistaRepository;

        public DiscoController(
                IDiscoRepository discoRepository,
                IArtistaRepository artistaRepository) {

            this.discoRepository = discoRepository;
            this.artistaRepository = artistaRepository;
        }

        // Crear un disco
        @PostMapping(
            value = "/disco",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
        )
        public ResponseEntity<Disco> HandlePostDiscoRequest(
                @RequestBody Disco disco) {

            if (!artistaRepository.existsById(disco.idArtista)) {
                return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
                );
            }

            Disco nuevoDisco = discoRepository.save(disco);

            return new ResponseEntity<>(
                nuevoDisco,
                HttpStatus.CREATED
            );
        }

        // Obtener todos los discos
        @GetMapping(
            value = "/discos",
            produces = MediaType.APPLICATION_JSON_VALUE
        )
        public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {

            List<Disco> discos = discoRepository.findAll();

            return new ResponseEntity<>(
                discos,
                HttpStatus.OK
            );
        }

        // Obtener un disco por ID
        @GetMapping(
            value = "/disco/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
        )
        public ResponseEntity<Disco> HandleGetDiscoRequest(
                @PathVariable String id) {

            Optional<Disco> disco = discoRepository.findById(id);

            if (disco.isPresent()) {
                return new ResponseEntity<>(
                    disco.get(),
                    HttpStatus.OK
                );
            }

            return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
            );
        }

        // Obtener los discos de un artista
        @GetMapping(
            value = "/artista/{id}/discos",
            produces = MediaType.APPLICATION_JSON_VALUE
        )
        public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(
                @PathVariable String id) {

            if (!artistaRepository.existsById(id)) {
                return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
                );
            }

            List<Disco> discos = discoRepository.findDiscosByIdArtista(id);

            return new ResponseEntity<>(
                discos,
                HttpStatus.OK
            );
        }
    }