package pe.edu.upeu.bibliotecabackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.bibliotecabackend.model.Categoria;
import pe.edu.upeu.bibliotecabackend.service.CategoriaService;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // URL: /api/categorias
    @GetMapping
    public List<Categoria> obtenerTodas() {
        return categoriaService.obtenerTodas();
    }

    // URL: /api/categorias/1
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Integer id) {
        return categoriaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // URL: /api/categorias
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria crearCategoria(@RequestBody Categoria categoria) {
        return categoriaService.guardar(categoria);
    }

    // URL: /api/categorias/1
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Integer id, @RequestBody Categoria categoriaDetalles) {
        try {
            Categoria categoriaActualizada = categoriaService.actualizar(id, categoriaDetalles);
            return ResponseEntity.ok(categoriaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // URL: /api/categorias/1
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarCategoria(@PathVariable Integer id) {
        categoriaService.borrar(id);
    }
}