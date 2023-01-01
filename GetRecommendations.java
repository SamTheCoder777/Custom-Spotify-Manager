import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import se.michaelthelin.spotify.requests.data.browse.GetRecommendationsRequest;

import java.io.IOException;

public class GetRecommendations {
    private static final SpotifyApi spotifyApi = Main.spotifyApi;
    private int limit;
    private String genre;

    private static GetRecommendationsRequest getRecommendationsRequest;

    public static void execute() {
        getRecommendationsRequest = spotifyApi.getRecommendations()
                .limit(Main.limit)
                .seed_genres(Main.genre)
                .build();
        try {
            final Recommendations recommendations = getRecommendationsRequest.execute();
            for(int i = 0; i < recommendations.getTracks().length; i++){
                System.out.print(i + ": " + recommendations.getTracks()[i].getName());
                System.out.printf(" (%d:%02d)", ((recommendations.getTracks()[i].getDurationMs() / 1000) / 60),
                        (recommendations.getTracks()[i].getDurationMs() / 1000) % 60);
                System.out.print(" ("+recommendations.getTracks()[i].getExternalUrls().getExternalUrls().get("spotify")+")");
                System.out.println();
            }
            System.out.println("Length: " + recommendations.getTracks().length);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static Recommendations getRecommendations() {
        getRecommendationsRequest = spotifyApi.getRecommendations()
                .limit(1)
                .seed_genres(Main.genre)
                .build();
        try {
            final Recommendations recommendations = getRecommendationsRequest.execute();
            return recommendations;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static Recommendations getFullRecommendations() {
        getRecommendationsRequest = spotifyApi.getRecommendations()
                .limit(50)
                .seed_genres(Main.genre)
                .build();
        try {
            final Recommendations recommendations = getRecommendationsRequest.execute();
            return recommendations;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
