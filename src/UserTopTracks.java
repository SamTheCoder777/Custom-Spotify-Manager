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

import static org.fusesource.jansi.Ansi.ansi;

public class UserTopTracks {
    private static final SpotifyApi spotifyApi = Main.spotifyApi;
    private static GetUsersTopTracksRequest getUsersTopTracksRequest;
    private static Track[] tracks;

    public static void execute() {
        tracks = new Track[Main.limit];

        getUsersTopTracksRequest = spotifyApi.getUsersTopTracks()
                .limit(Main.limit)
                .build();

        try {
            Paging<Track> trackPaging = getUsersTopTracksRequest.execute();
            tracks = trackPaging.getItems();
            for(int i = 0; i < tracks.length; i++ ){
                System.out.print(ansi().render("@|green "+ (i+1) +  ": |@") + tracks[i].getName()+" ");
                System.out.printf(ansi().render("@|green (%d:%02d) |@").toString(), ((tracks[i].getDurationMs() / 1000) / 60), (tracks[i].getDurationMs() / 1000) % 60);
                System.out.print(ansi().render("@|yellow (popularity: " + tracks[i].getPopularity() + ") |@"));
                System.out.println(" ("+tracks[i].getExternalUrls().getExternalUrls().get("spotify")+")");
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println(ansi().render("@|red Error: " + e.getMessage()+"|@"));
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

            System.out.println(ansi().render("@|green -----------DONE-----------|@"));
            System.out.println(ansi().render("@|green Spotify url: |@" + playlist.getExternalUrls().getExternalUrls().get("spotify")));

        }
        catch(IOException | SpotifyWebApiException | ParseException e) {
            System.out.println(ansi().render("@|red Error: " + e.getMessage()+"|@"));
        }
    }
}
