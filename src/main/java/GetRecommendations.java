import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import se.michaelthelin.spotify.requests.data.browse.GetRecommendationsRequest;

import java.io.IOException;

import static org.fusesource.jansi.Ansi.ansi;

public class GetRecommendations {
    private static final SpotifyApi spotifyApi = Main.spotifyApi;
    private int limit;
    private String genre;

    private static GetRecommendationsRequest getRecommendationsRequest;

    public static void execute() {
        getRecommendationsRequest = spotifyApi.getRecommendations()
                .limit(Main.limit)
                .market(Main.location)
                .seed_genres(Main.genre)
                .build();
        try {
            final Recommendations recommendations = getRecommendationsRequest.execute();
            for(int i = 0; i < recommendations.getTracks().length; i++){
                System.out.print(ansi().render("@|green "+ (i+1) +  ": |@") + recommendations.getTracks()[i].getName());
                System.out.printf(ansi().render("@|green (%d:%02d) |@").toString(), ((recommendations.getTracks()[i].getDurationMs() / 1000) / 60),
                        (recommendations.getTracks()[i].getDurationMs() / 1000) % 60);
                System.out.print(ansi().render("@|yellow ("+recommendations.getTracks()[i].getExternalUrls().getExternalUrls().get("spotify")+")|@"));
                System.out.println();
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println(ansi().render("@|red Error: " + e.getMessage()+"|@"));
        }
    }

    public static Recommendations getRecommendations() {
        getRecommendationsRequest = spotifyApi.getRecommendations()
                .seed_genres(Main.genre)
                .market(Main.location)
                .build();
        try {
            Recommendations recommendations = getRecommendationsRequest.execute();
            return recommendations;
        } catch (Exception e) {
            System.out.println(ansi().render("@|red Error: " + e.getMessage() + "|@"));
            return null;
        }
    }

    public static Recommendations getFullRecommendations() {
        getRecommendationsRequest = spotifyApi.getRecommendations()
                .limit(50)
                .seed_genres(Main.genre)
                .market(Main.location)
                .build();
        try {
            final Recommendations recommendations = getRecommendationsRequest.execute();
            return recommendations;
        } catch (Exception e) {
            System.out.println(ansi().render("@|red Error: " + e.getMessage() + "|@"));
            return null;
        }
    }
}
