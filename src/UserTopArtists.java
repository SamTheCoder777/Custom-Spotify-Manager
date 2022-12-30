import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;

import java.io.IOException;

public class UserTopArtists {
    private static final GetUsersTopArtistsRequest getUsersTopArtistsRequest = Main.spotifyApi.getUsersTopArtists()
          .limit(Main.limit)
//          .offset(0)
//          .time_range("medium_term")
            .build();

    public static void getUsersTopArtists_Sync() {
        try {
            final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();
            int i = 0;
            for(Artist artist : artistPaging.getItems()){
                System.out.println(i + " " + artist.getName());
                i++;
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void execute(){
        getUsersTopArtists_Sync();
    }
}
