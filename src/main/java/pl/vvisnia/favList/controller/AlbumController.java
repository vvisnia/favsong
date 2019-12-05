package pl.vvisnia.favList.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.vvisnia.favList.model.Artists;
import pl.vvisnia.favList.model.FavList;
import pl.vvisnia.favList.model.SpotifyAlbum;
import pl.vvisnia.favList.model.Tracks;
import pl.vvisnia.favList.service.FavListService;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;


@RestController
public class AlbumController {

    private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);

    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders httpHeaders = new HttpHeaders();

    @Autowired
    FavListService favListService;

    @GetMapping("/artists/{artist}")
    public Artists getArtists(OAuth2Authentication authentication, @PathVariable String artist) {
        httpHeaders.add("Authorization", "Bearer " + getToken(authentication));
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<Artists> response = restTemplate.exchange("https://api.spotify.com/v1/search?q=" + artist + "&type=artist&market=US&limit=5",
                HttpMethod.GET,
                httpEntity,
                Artists.class);

        logger.info("User searched for artist named \"" + artist + "\"");
        return response.getBody();
    }

    @GetMapping("/albums/{artistID}")
    public SpotifyAlbum getAlbums(OAuth2Authentication authentication, @PathVariable String artistID) {
        httpHeaders.add("Authorization", "Bearer " + getToken(authentication));
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<SpotifyAlbum> response = restTemplate.exchange("https://api.spotify.com/v1/artists/" + artistID + "/albums?market=US",
                HttpMethod.GET,
                httpEntity,
                SpotifyAlbum.class);

        logger.info("User searched for albums of artistID \"" + artistID + "\"");

        return response.getBody();
    }

    @GetMapping("/tracks/{albumID}")
    public Tracks getTracks(OAuth2Authentication authentication, @PathVariable String albumID) {
        httpHeaders.add("Authorization", "Bearer " + getToken(authentication));
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<Tracks> response = restTemplate.exchange("https://api.spotify.com/v1/albums/" + albumID + "?market=US",
                HttpMethod.GET,
                httpEntity,
                Tracks.class);

        logger.info("User searched for tracks from albumID \"" + albumID + "\"");

        return response.getBody();
    }

    private String getToken(OAuth2Authentication authentication) {
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        return details.getTokenValue();
    }

    //wtf
    @PostMapping("/favlist/save")
    public ResponseEntity saveList(@RequestBody FavList favList) {
        Map<Object, Object> model = new HashMap<>();

        //  List<String> testList = new ArrayList<String>();
        // FavList favList1 = new FavList("wisnia", testList);
        if (favListService.findByName("wisniah") != null) {
            favListService.delete(favListService.findByName("wisniah").get_id().toHexString());
        }
        favListService.save(favList);

        //System.out.println(favList.getName() + favList.getTracksList().toString());
        //  System.out.println(favList.getName());
        // System.out.println(favList.getTracksList());
        System.out.println(favListService.findByName("wisniah"));
        // System.out.println(favListService.listAll());
        model.put("test", "temp");

        return ok(model);
    }
}
