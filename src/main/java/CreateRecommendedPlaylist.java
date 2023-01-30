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

import static org.fusesource.jansi.Ansi.ansi;


public class CreateRecommendedPlaylist {
    private static GetCurrentUsersProfileRequest getCurrentUsersProfileRequest;
    private static CreatePlaylistRequest createPlaylistRequest;
    private static Playlist playlist;
    private static String playlistId;
    private static Recommendations recommendations;
    private static final String[] uris = new String[50];
    public static void createPlaylist() {
        try {
            getCurrentUsersProfileRequest = Main.spotifyApi.getCurrentUsersProfile()
                    .build();

            createPlaylistRequest = Main.spotifyApi.createPlaylist(getCurrentUsersProfileRequest.execute().getId(), Main.name)
                    .public_(Main.playlistPublic)
                    .build();

            playlist = createPlaylistRequest.execute();
            playlistId = playlist.getId();

        } catch (Exception e) {
            System.out.println(ansi().render("@|red Error: " + e.getMessage() + "\nTIP: make sure your genre exists!|@"));
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
            System.out.println(ansi().render("@|green -----------DONE-----------|@"));
            System.out.println(ansi().render("@|green Spotify url: |@" + playlist.getExternalUrls().getExternalUrls().get("spotify")));
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println(ansi().render("@|red Error: " + e.getMessage()+"|@"));
        }

    }
    public static void execute(){
        System.out.println(ansi().render("@|yellow This might take some time...\nGo grab a coffee or something while im working...|@"));
        createPlaylist();
        addSongs();
    }


}
