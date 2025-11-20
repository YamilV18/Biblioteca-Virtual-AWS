package pe.edu.upeu.bibliotecabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.bibliotecabackend.model.Libro;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(String titulo, String autor);
}