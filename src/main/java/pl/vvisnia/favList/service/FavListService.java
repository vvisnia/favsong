package pl.vvisnia.favList.service;

import pl.vvisnia.favList.model.FavList;

import java.util.List;

public interface FavListService {
    List<FavList> listAll();

    FavList findByName(String name);

    FavList save(FavList favList);

    void delete(String id);


}
