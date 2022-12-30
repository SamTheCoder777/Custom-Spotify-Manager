import com.neovisionaries.i18n.CountryCode;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsTopTracksRequest;

import java.io.IOException;
import java.util.Scanner;

public class GetArtistsTopTracks {
    private String name;
    private Paging<Artist> artistPaging = new GetSearchArtists(Main.spotifyApi, "").getArtist();
    private SpotifyApi spotifyApi;
    private int index;
    private Scanner input;
    private static GetArtistsTopTracksRequest getArtistsTopTracksRequest;
    private CountryCode countryCode;
    private String id;
    private GetSearchArtists searchArtists;
    GetArtistsTopTracks(SpotifyApi spotifyApi, String name, CountryCode countryCode){
        this.name = name;
        this.spotifyApi = spotifyApi;
        this.countryCode = countryCode;
        this.input = new Scanner(System.in);

        searchArtists = new GetSearchArtists(spotifyApi, name);

    }

    public void setArtistPaging(){
        getArtistsTopTracksRequest = spotifyApi
                .getArtistsTopTracks(searchArtists.getArtist().getItems()[index].getId(), countryCode)
                .build();
    }


    public void inputIndex(){
        System.out.println(searchArtists);
        System.out.print("Input the number of your artist: ");
        index = input.nextInt();
        System.out.println();
        while(index > 4 || index < 0){
           System.out.println("Error: wrong index");
           System.out.print("Please enter the index again: ");
           index = input.nextInt();
        }



    }

    public static void getArtistsTopTracks_Sync() {
        try {
            Track[] tracks = getArtistsTopTracksRequest.execute();
            for(int i = 0; i < tracks.length; i++){
                System.out.print(i + ": " + tracks[i].getName());
                System.out.printf(" (%d:%02d)", ((tracks[i].getDurationMs() / 1000) / 60), (tracks[i].getDurationMs() / 1000) % 60);
                System.out.println(" (popularity: " + tracks[i].getPopularity() + ")");
            }
            System.out.println("Length: " + tracks.length);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void execute(){
        inputIndex();
        setArtistPaging();

        getArtistsTopTracks_Sync();
    }


}
