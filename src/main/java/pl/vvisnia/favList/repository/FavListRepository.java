package pl.vvisnia.favList.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.vvisnia.favList.model.FavList;

public interface FavListRepository extends MongoRepository<FavList, String> {
FavList findByName(String name);
}
