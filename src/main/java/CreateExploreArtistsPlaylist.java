import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.special.SnapshotResult;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumsTracksRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsAlbumsRequest;
import se.michaelthelin.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchArtistsRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import static org.fusesource.jansi.Ansi.ansi;

public class CreateExploreArtistsPlaylist {
    private static String[] artistIDs = new String[200];
    private static int offset = 0;
    private static int index = 0;
    private static String q = "genre:";
    private static SearchArtistsRequest searchArtistsRequest;
    private static GetArtistsAlbumsRequest getArtistsAlbumsRequest;
    private static ArrayList<Integer> setRandom = new ArrayList<Integer>();
    private static CreatePlaylistRequest createPlaylistRequest;
    private static GetCurrentUsersProfileRequest getCurrentUsersProfileRequest;
    private static int[] randoms = new int[Main.numArtists];
    private static ArrayList<String> urisArrayList = new ArrayList<String>();
    private static String[] uris;
    private static AddItemsToPlaylistRequest addItemsToPlaylistRequest;
    private static String playlistId;
    private static SnapshotResult snapshotResult;
    private static Playlist playlist;
    private static ArrayList<String> alreadyUsedArtistId = new ArrayList<String>();

    public static void reset(){
        artistIDs = new String[200];
        offset = 0;
        index = 0;
        q = "genre:";
        setRandom = new ArrayList<Integer>();
        randoms = new int[Main.numArtists];
        urisArrayList = new ArrayList<String>();
        alreadyUsedArtistId = new ArrayList<String>();
    }
    public static void execute(){
        reset();
        try {
            q += Main.genre;
                       getCurrentUsersProfileRequest = Main.spotifyApi.getCurrentUsersProfile()
                    .build();

            createPlaylistRequest = Main.spotifyApi.createPlaylist(getCurrentUsersProfileRequest.execute().getId(), Main.name)
                    .public_(Main.playlistPublic)
                    .build();

            playlist = createPlaylistRequest.execute();
            playlistId = playlist.getId();

            for(int i = 0; i < 3; i++){
                searchArtistsRequest = Main.spotifyApi.searchArtists(q)
                        .market(Main.location)
            .limit(50)
            .offset(offset)
//          .includeExternal("audio")
                        .build();

                Paging<Artist> artistPaging = searchArtistsRequest.execute();
                for(int x = 0; x < 50; x++){
                    artistIDs[index] = artistPaging.getItems()[x].getId();
                    index++;
                }
                offset+=50;
            }

            for(int x = 0; x < 199; x++){
                setRandom.add(x);
            }
            Collections.shuffle(setRandom);
            for(int i = 0; i < Main.numArtists; i++){
                randoms[i] = setRandom.get(i);
            }

            for(int i = 0; i < Main.numArtists; i++){
                getArtistsAlbumsRequest = Main.spotifyApi.getArtistsAlbums(artistIDs[i])
                        .album_type("album")
                        .limit(50)
//          .offset(0)
                        .market(Main.location)
                        .build();
                    final Paging<AlbumSimplified> albumSimplifiedPaging = getArtistsAlbumsRequest.execute();
                    if (albumSimplifiedPaging.getItems().length > 1) {
                    for(int x = 0; x < Main.limit; x++) {

                            int randomNum = ThreadLocalRandom.current().nextInt(0, albumSimplifiedPaging.getItems().length);

                            GetAlbumsTracksRequest getAlbumsTracksRequest = Main.spotifyApi.getAlbumsTracks(
                                            albumSimplifiedPaging.getItems()[randomNum].getId())
                                    .limit(50)
                                    .build();

                            Paging<TrackSimplified> trackSimplifiedPaging = getAlbumsTracksRequest.execute();

                            int trackRandomNum = ThreadLocalRandom.current().nextInt(0, trackSimplifiedPaging.getItems().length);
                            if (!urisArrayList.contains(trackSimplifiedPaging.getItems()[trackRandomNum].getUri())) {
                                if(!Main.includeInstrumental && !trackSimplifiedPaging.getItems()[trackRandomNum].getName().toLowerCase().contains("instrumental")){
                                    urisArrayList.add(trackSimplifiedPaging.getItems()[trackRandomNum].getUri());
                                    System.out.println("ADDED: " + trackSimplifiedPaging.getItems()[trackRandomNum].getName());
                                    Thread.sleep(200);
                                }
                                if(Main.includeInstrumental){
                                    urisArrayList.add(trackSimplifiedPaging.getItems()[trackRandomNum].getUri());
                                    System.out.println("ADDED: " + trackSimplifiedPaging.getItems()[trackRandomNum].getName());
                                    Thread.sleep(200);
                                }
                            }
                        }
                        uris = new String[urisArrayList.size()];
                        urisArrayList.toArray(uris);
                        urisArrayList.clear();
                        addItemsToPlaylistRequest = Main.spotifyApi
                                .addItemsToPlaylist(playlistId, uris)
                                .build();
                        snapshotResult = addItemsToPlaylistRequest.execute();

                    }
            }
        } catch (Exception e) {
            System.out.println(ansi().render("@|red Error: " + e.getMessage() + "|@"));
        }
        System.out.println(ansi().render("@|green -----------DONE-----------|@"));
        System.out.println(ansi().render("@|green Spotify url: |@" + playlist.getExternalUrls().getExternalUrls().get("spotify")));
    }
}
