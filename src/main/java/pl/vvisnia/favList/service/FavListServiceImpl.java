package pl.vvisnia.favList.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.vvisnia.favList.model.FavList;
import pl.vvisnia.favList.repository.FavListRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavListServiceImpl implements FavListService {


    private FavListRepository favListRepository;

    @Autowired
    public FavListServiceImpl(FavListRepository favListRepository) {
        this.favListRepository = favListRepository;

    }
    @Override
    public List<FavList> listAll() {
        List<FavList> favLists = new ArrayList<>();
        favListRepository.findAll().forEach(favLists::add);
        return favLists;
    }

    @Override
    public FavList findByName(String name) {
        return favListRepository.findByName(name);
    }

    @Override
    public FavList save(FavList favList) {
        favListRepository.save(favList);
        return favList;
    }

    @Override
    public void delete(String id) {
        favListRepository.deleteById(id);
    }
}
