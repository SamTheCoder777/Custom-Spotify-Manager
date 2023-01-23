import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.browse.miscellaneous.GetAvailableGenreSeedsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.fusesource.jansi.Ansi.ansi;

public class GetSeedGenreList {
    private static final GetAvailableGenreSeedsRequest getAvailableGenreSeedsRequest = Main.spotifyApi.getAvailableGenreSeeds()
            .build();

    public static void getAvailableGenreSeeds_Sync() {
        try {
            final String[] strings = getAvailableGenreSeedsRequest.execute();

            for(String string : strings){
                System.out.println(string);
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println(ansi().render("@|red Error: " + e.getMessage()+"|@"));
        }
    }

    public static void execute(){
        getAvailableGenreSeeds_Sync();
    }
}
