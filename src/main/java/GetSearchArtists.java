import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchArtistsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

import static org.fusesource.jansi.Ansi.ansi;

public class GetSearchArtists {
    private static SearchArtistsRequest searchArtistsRequest;
    Paging<Artist> artistPagingToReturn;
    GetSearchArtists(SpotifyApi spotifyApi, String name){
        searchArtistsRequest = spotifyApi.searchArtists(name).limit(5).market(Main.location).build();

    }

    public void setArtistPagingToReturn(Paging<Artist> artistPagingToReturn) {
        this.artistPagingToReturn = artistPagingToReturn;
    }

    public String searchArtists_Sync() {
        try {
            int counter = 1;
            String a = "";
            final Paging<Artist> artistPaging = searchArtistsRequest.execute();

            //set artistPagingToReturn
            setArtistPagingToReturn(artistPaging);
            //prepare search result print
            for (Artist artist : artistPaging.getItems()) {
                a += (counter + ": " + artist.getName() + "\n");
                counter++;
            }
            return a;
        } catch (Exception e) {
            System.out.println(ansi().render("@|red Error: " + e.getMessage() + "|@"));
            return null;
        }
    }

    public Paging<Artist> getArtist(){
        return artistPagingToReturn;
    }


    public String toString(){
        return searchArtists_Sync();
    }

}
