import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import se.michaelthelin.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class CreateRecommendedPlaylist {
    private static GetCurrentUsersProfileRequest getCurrentUsersProfileRequest;
    private static CreatePlaylistRequest createPlaylistRequest;
    private static Playlist playlist;
    private static String playlistId;
    private static Recommendations recommendations;
    private static String[] uris = new String[50];
    public static void createPlaylist() {
        try {
            getCurrentUsersProfileRequest = Main.spotifyApi.getCurrentUsersProfile()
                    .build();

            createPlaylistRequest = Main.spotifyApi.createPlaylist(getCurrentUsersProfileRequest.execute().getId(), Main.name)
                    .public_(false)
                    .build();

            playlist = createPlaylistRequest.execute();
            playlistId = playlist.getId();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage() + "\nTIP: make sure your genre exists!");
        }
    }

    public static void addSongs(){
        recommendations = GetRecommendations.getFullRecommendations();
        for(int i = 0; i < 50; i++){
            uris[i] = recommendations.getTracks()[i].getUri();
        }
        AddItemsToPlaylistRequest addItemsToPlaylistRequest = Main.spotifyApi
                .addItemsToPlaylist(playlistId, uris)
//          .position(0)
                .build();
        try {
            addItemsToPlaylistRequest.execute();
            System.out.println("-----------DONE-----------");
            System.out.println("Playlist url: " + playlist.getExternalUrls().getExternalUrls().get("spotify"));
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException(e);
        }

    }
    public static void execute(){
        System.out.println("This might take some time...\nGo grab a coffee or something while im working...");
        createPlaylist();
        addSongs();
    }


}
