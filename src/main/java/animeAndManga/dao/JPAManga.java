package animeAndManga.dao;

import animeAndManga.models.Manga;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JPAManga extends CrudRepository<Manga,Long>{
    List<Manga> findAll();
}
