package pe.edu.upeu.bibliotecabackend.service;

import org.springframework.stereotype.Service;
import pe.edu.upeu.bibliotecabackend.model.Categoria;
import pe.edu.upeu.bibliotecabackend.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // CREATE / UPDATE
    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // READ ALL
    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }

    // READ BY ID
    public Optional<Categoria> obtenerPorId(Integer id) {
        return categoriaRepository.findById(id);
    }

    // DELETE
    public void borrar(Integer id) {
        categoriaRepository.deleteById(id);
    }

    // UPDATE (Método específico)
    public Categoria actualizar(Integer id, Categoria detalles) {
        return categoriaRepository.findById(id).map(categoriaExistente -> {
            categoriaExistente.setNombre(detalles.getNombre());
            categoriaExistente.setDescripcion(detalles.getDescripcion()); // Actualiza la nueva descripción
            return categoriaRepository.save(categoriaExistente);
        }).orElseThrow(() ->
                new RuntimeException("Categoría no encontrada con ID: " + id)
        );
    }
}
