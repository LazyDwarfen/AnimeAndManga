package animeAndManga.dao;

import animeAndManga.models.Change;
import org.springframework.data.repository.CrudRepository;

public interface JPAChange extends CrudRepository<Change,Long> {
}
