package com.comicverse.repository;
import java.util.List;
import com.comicverse.model.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComicRepository extends JpaRepository <Comic, Long> {


    List<Comic> findByTituloContaining(String titulo);

    List<Comic> findByPrecioLessThanEqual(Double precio);

    List<Comic> findByTituloContainingAndPrecioLessThanEqual(String titulo, Double precio);

    List<Comic> findByTituloContainingIgnoreCase(String titulo);


    boolean existsByTituloAndEditorial(String titulo, String editorial);

}

