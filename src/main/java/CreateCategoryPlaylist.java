import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.special.SnapshotResult;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static org.fusesource.jansi.Ansi.ansi;

public class CreateCategoryPlaylist {
    private static CreatePlaylistRequest createPlaylistRequest;
    private static GetCurrentUsersProfileRequest getCurrentUsersProfileRequest;
    private static AddItemsToPlaylistRequest addItemsToPlaylistRequest;
    private static GetPlaylistsItemsRequest getPlaylistsItemsRequest;
    private static Playlist playlist;
    static Paging<PlaylistSimplified> playlistSimplifiedPaging;
    private static String playlistId;
    private static String[] uris;
    private static int[] randomNumbers;
    private static int[] randomPlaylistNumbers;
    private static ArrayList<Integer> setRandom = new ArrayList<Integer>();

    static ArrayList<String> urisList = new ArrayList<String>();

    public static void reset(){
        setRandom = new ArrayList<Integer>();
        urisList = new ArrayList<String>();
    }
    public static void createPlaylist() {
        try {
            getCurrentUsersProfileRequest = Main.spotifyApi.getCurrentUsersProfile()
                    .build();

            createPlaylistRequest = Main.spotifyApi.createPlaylist(getCurrentUsersProfileRequest.execute().getId(), Main.name)
                    .public_(Main.playlistPublic)
                    .build();

            playlist = createPlaylistRequest.execute();
            playlistId = playlist.getId();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println(ansi().render("@|red Error: " + e.getMessage()+"\nTIP: make sure your genre exists!|@"));
        }
    }

    public static void addSongs() {
        try {
            playlistSimplifiedPaging = GetCategoryPlaylist.getPlaylists();
            randomPlaylistNumbers = new int[playlistSimplifiedPaging.getItems().length];

            for(int i = 0; i < randomPlaylistNumbers.length; i++){
                setRandom.add(i);
            }
            Collections.shuffle(setRandom);
            for(int i = 0; i < Main.numPlaylists; i++){
                randomPlaylistNumbers[i] = setRandom.get(i);
            }

            for (int i = 0; i < Main.numPlaylists; i++) {

                    getPlaylistsItemsRequest = Main.spotifyApi
                            .getPlaylistsItems(playlistSimplifiedPaging.getItems()[randomPlaylistNumbers[i]].getId())
                            .build();
                randomNumbers = new int[getPlaylistsItemsRequest.execute().getItems().length];
                setRandom.clear();
                for(int z = 0; z < randomNumbers.length; z++){
                    setRandom.add(z);
                }
                Collections.shuffle(setRandom);
                for(int z = 0; z < Main.limit; z++){
                    randomNumbers[i] = setRandom.get(i);
                }

                Paging<PlaylistTrack> playlistSimplifiedPaging1 = getPlaylistsItemsRequest.execute();

                for (int x = 0; x < Main.limit; x++) {
                        if (playlistSimplifiedPaging1.getItems()[randomNumbers[x]].getTrack().getType().toString().equals("TRACK")
                                && !urisList.contains(playlistSimplifiedPaging1.getItems()[randomNumbers[x]].getTrack().getUri())) {
                            urisList.add(playlistSimplifiedPaging1.getItems()[randomNumbers[x]].getTrack().getUri());
                            System.out.println("ADDED: " + playlistSimplifiedPaging1.getItems()[randomNumbers[x]].getTrack().getName());
                            Thread.sleep(200);
                        }else{
                            Recommendations rec = GetRecommendations.getRecommendations();

                            while(urisList.contains(rec.getTracks()[0].getUri())){
                                rec = GetRecommendations.getRecommendations();
                            }
                            urisList.add(rec.getTracks()[0].getUri());
                            System.out.println("ADDED: " + rec.getTracks()[0].getName());
                            Thread.sleep(200);
                        }
                }

                uris = new String[urisList.size()];

                urisList.toArray(uris);
                urisList.clear();
                addItemsToPlaylistRequest = Main.spotifyApi
                        .addItemsToPlaylist(playlistId, uris)
                        .build();
                SnapshotResult snapshotResult = addItemsToPlaylistRequest.execute();
            }
            System.out.println(ansi().render("@|green -----------DONE-----------|@"));
            System.out.println(ansi().render("@|green Spotify url: |@" + playlist.getExternalUrls().getExternalUrls().get("spotify")));

        }catch (InterruptedException | ParseException | SpotifyWebApiException | IOException e) {
            System.out.println(ansi().render("@|red Error: " + e.getMessage()+"|@"));
        }

    }


    public static void execute() {
        reset();
        System.out.println(ansi().render("@|yellow This might take some time...\nGo grab a coffee or something while im working...|@"));
        createPlaylist();
        addSongs();
    }
}
