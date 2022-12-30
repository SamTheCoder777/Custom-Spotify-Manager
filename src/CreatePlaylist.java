import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;

public class CreatePlaylist {
    private static CreatePlaylistRequest createPlaylistRequest;
    private static GetCurrentUsersProfileRequest getCurrentUsersProfileRequest;

    public static void execute() {
        try {
            getCurrentUsersProfileRequest = Main.spotifyApi.getCurrentUsersProfile()
                    .build();

            createPlaylistRequest = Main.spotifyApi.createPlaylist(getCurrentUsersProfileRequest.execute().getId(),Main.name)
                    .public_(false)
                    .build();

            Playlist playlist = createPlaylistRequest.execute();

            System.out.println("Name: " + playlist.getName());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
