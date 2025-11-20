package pe.edu.upeu.bibliotecabackend.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.bibliotecabackend.model.Libro;
import pe.edu.upeu.bibliotecabackend.service.LibroService;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    // 1. CREATE (POST) - Crear un nuevo libro
    @PostMapping
    public ResponseEntity<Libro> crearLibro(@RequestBody Libro libro) {
        // Retorna 201 Created
        Libro nuevoLibro = libroService.guardar(libro);
        return new ResponseEntity<>(nuevoLibro, HttpStatus.CREATED);
    }

    // 2. READ (GET) - Obtener todos o buscar (completo)
    @GetMapping
    public List<Libro> obtenerLibros(@RequestParam(required = false) String q) {
        if (q != null && !q.isEmpty()) {
            return libroService.buscar(q);
        }
        return libroService.obtenerTodos();
    }

    // 2. READ (GET) - Obtener por ID (completo)
    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerLibroPorId(@PathVariable Long id) {
        return libroService.obtenerPorId(id)
                .map(ResponseEntity::ok) // Si existe, retorna 200 OK
                .orElse(ResponseEntity.notFound().build()); // Si no existe, retorna 404 Not Found
    }

    // 3. UPDATE (PUT) - Actualizar un libro existente
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizarLibro(@PathVariable Long id, @RequestBody Libro libroDetalles) {
        return libroService.actualizar(id, libroDetalles)
                .map(ResponseEntity::ok) // Si se actualiza, retorna 200 OK
                .orElse(ResponseEntity.notFound().build()); // Si el ID no existe, retorna 404 Not Found
    }

    // 4. DELETE (DELETE) - Eliminar un libro por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        boolean fueEliminado = libroService.eliminarPorId(id);

        if (fueEliminado) {
            // Retorna 204 No Content para indicar que la acción fue exitosa y no hay cuerpo de respuesta
            return ResponseEntity.noContent().build();
        } else {
            // Retorna 404 Not Found si el libro no existía
            return ResponseEntity.notFound().build();
        }
    }
}