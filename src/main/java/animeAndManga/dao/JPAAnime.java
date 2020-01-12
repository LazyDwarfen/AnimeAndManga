package animeAndManga.dao;

import animeAndManga.models.Anime;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JPAAnime extends CrudRepository<Anime,Long>{
    List<Anime> findAll();
}
