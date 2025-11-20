package pe.edu.upeu.bibliotecabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.bibliotecabackend.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
