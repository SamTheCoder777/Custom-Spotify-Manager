import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.special.SnapshotResult;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;

public class UserTopTracks {
    private static final SpotifyApi spotifyApi = Main.spotifyApi;
    private static final GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks()
            .limit(Main.limit)
            .build();
    private static Track[] tracks = new Track[Main.limit];

    public static void execute() {
        try {
            final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();
            tracks = trackPaging.getItems();
            for(int i = 0; i < tracks.length; i++ ){
                System.out.print((i+1) + ": " + tracks[i].getName());
                System.out.printf(" (%d:%02d)", ((tracks[i].getDurationMs() / 1000) / 60), (tracks[i].getDurationMs() / 1000) % 60);
                System.out.print(" (popularity: " + tracks[i].getPopularity() + ")");
                System.out.println(" ("+tracks[i].getExternalUrls().getExternalUrls().get("spotify")+")");
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void createUserTopTrackPlaylist(){
        try{
            GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = Main.spotifyApi.getCurrentUsersProfile()
                    .build();

            CreatePlaylistRequest createPlaylistRequest = Main.spotifyApi.createPlaylist(getCurrentUsersProfileRequest.execute().getId(),Main.name)
                    .public_(false)
                    .build();

            Playlist playlist = createPlaylistRequest.execute();
            String playlistId = playlist.getId();

            String[] uris = new String[Main.limit];
            for(int i = 0; i < tracks.length; i++){
                uris[i] = tracks[i].getUri();
            }

            SnapshotResult addItemsToPlaylistRequest = Main.spotifyApi
                    .addItemsToPlaylist(playlistId, uris)
                    .build()
                    .execute();

            System.out.println("-----------DONE-----------");
            System.out.println("Playlist url: " + playlist.getExternalUrls().getExternalUrls().get("spotify"));

        }
        catch(IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
