import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchArtistsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class GetSearchArtists {
    private static SearchArtistsRequest searchArtistsRequest;
    Paging<Artist> artistPagingToReturn;
    GetSearchArtists(SpotifyApi spotifyApi, String name){
        searchArtistsRequest = spotifyApi.searchArtists(name).limit(5).build();

    }

    public void setArtistPagingToReturn(Paging<Artist> artistPagingToReturn) {
        this.artistPagingToReturn = artistPagingToReturn;
    }

    public String searchArtists_Sync() {
        try {
            int counter = 0;
            String a = "";
            final Paging<Artist> artistPaging = searchArtistsRequest.execute();

            //set artistPagingToReturn
            setArtistPagingToReturn(artistPaging);
            //prepare search result print
            for(Artist artist: artistPaging.getItems()){
                a+=(counter + ": " + artist.getName()+"\n");
                counter++;
            }
            return a;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            return("Error: " + e.getMessage()+"\n");
        }
    }

    public Paging<Artist> getArtist(){
        return artistPagingToReturn;
    }


    public String toString(){
        return searchArtists_Sync();
    }

}
