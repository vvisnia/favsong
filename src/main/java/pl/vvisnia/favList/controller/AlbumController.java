package pl.vvisnia.favList.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.vvisnia.favList.model.Artists;
import pl.vvisnia.favList.model.SpotifyAlbum;
import pl.vvisnia.favList.model.Tracks;


@RestController
public class AlbumController {

    private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);

    private RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders httpHeaders = new HttpHeaders();

    @GetMapping("/artists/{artist}")
    public Artists getArtists(OAuth2Authentication authentication, @PathVariable String artist){
        httpHeaders.add("Authorization", "Bearer " + getToken(authentication));
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<Artists> response = restTemplate.exchange("https://api.spotify.com/v1/search?q="+ artist + "&type=artist&market=US&limit=5",
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

        ResponseEntity<SpotifyAlbum> response = restTemplate.exchange("https://api.spotify.com/v1/artists/"+ artistID + "/albums?market=US",
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

        ResponseEntity<Tracks> response = restTemplate.exchange("https://api.spotify.com/v1/albums/"+ albumID +"?market=US",
                HttpMethod.GET,
                httpEntity,
                Tracks.class);

        logger.info("User searched for tracks from albumID \"" + albumID + "\"");

        return response.getBody();
    }

    private String getToken(OAuth2Authentication authentication){
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();

        return details.getTokenValue();
    }
}
