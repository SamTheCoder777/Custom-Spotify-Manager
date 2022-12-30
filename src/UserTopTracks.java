import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;

import java.io.IOException;

public class UserTopTracks {
    private static final SpotifyApi spotifyApi = Main.spotifyApi;
    private static final GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks()
            .limit(Main.limit)
//          .offset(0)
//          .time_range("medium_term")
            .build();


    public static void execute() {
        try {
            final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();
            Track[] tracks = trackPaging.getItems();
            for(int i = 0; i < tracks.length; i++ ){
                System.out.print(i + ": " + tracks[i].getName());
                System.out.printf(" (%d:%02d)", ((tracks[i].getDurationMs() / 1000) / 60), (tracks[i].getDurationMs() / 1000) % 60);
                System.out.print(" (popularity: " + tracks[i].getPopularity() + ")");
                System.out.println(" ("+tracks[i].getExternalUrls().getExternalUrls().get("spotify")+")");
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
