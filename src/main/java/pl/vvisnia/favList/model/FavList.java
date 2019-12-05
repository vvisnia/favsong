package pl.vvisnia.favList.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class FavList {

    @Id
    private ObjectId _id;
    private String name;
    private List<String> tracksList;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTracksList() {
        return tracksList;
    }

    public void setTracksList(List<String> tracksList) {
        this.tracksList = tracksList;
    }

    public FavList(ObjectId _id, String name, List<String> tracksList) {
        this._id = _id;
        this.name = name;
        this.tracksList = tracksList;
    }

    public FavList(String name, List<String> tracksList) {
        this.name = name;
        this.tracksList = tracksList;
    }

    public FavList() {

    }

    @Override
    public String toString() {
        return "FavList{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", tracksList=" + tracksList +
                '}';
    }
}
