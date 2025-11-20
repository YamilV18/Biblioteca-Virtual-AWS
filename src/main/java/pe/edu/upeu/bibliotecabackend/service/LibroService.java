package pe.edu.upeu.bibliotecabackend.service;

import org.springframework.stereotype.Service;
import pe.edu.upeu.bibliotecabackend.model.Libro;
import pe.edu.upeu.bibliotecabackend.repository.LibroRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    // Inyección de dependencia a través del constructor
    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }

    public Optional<Libro> obtenerPorId(Long id) {
        return libroRepository.findById(id);
    }

    public List<Libro> buscar(String query) {
        return libroRepository.findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(query, query);
    }

    public Libro guardar(Libro libro) {
        return libroRepository.save(libro);
    }

    public Optional<Libro> actualizar(Long id, Libro libroDetalles) {
        // Busca el libro existente
        return libroRepository.findById(id)
                .map(libroExistente -> {
                    // Actualiza los campos
                    libroExistente.setTitulo(libroDetalles.getTitulo());
                    libroExistente.setAutor(libroDetalles.getAutor());
                    libroExistente.setIsbn(libroDetalles.getIsbn());
                    libroExistente.setDescripcion(libroDetalles.getDescripcion());
                    libroExistente.setUrlPortada(libroDetalles.getUrlPortada());
                    libroExistente.setUrlArchivo(libroDetalles.getUrlArchivo());

                    // Guarda y retorna el libro actualizado
                    return libroRepository.save(libroExistente);
                });
    }

    public boolean eliminarPorId(Long id) {
        if (libroRepository.existsById(id)) {
            libroRepository.deleteById(id);
            return true;
        }
        return false;
    }
}