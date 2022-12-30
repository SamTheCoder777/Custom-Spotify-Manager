import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Category;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.requests.data.browse.GetCategorysPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.browse.GetListOfCategoriesRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GetCategoryPlaylist {
    private static GetListOfCategoriesRequest getListOfCategoriesRequest;
    private static GetCategorysPlaylistsRequest getCategoryRequest;

    private static Paging<PlaylistSimplified> playlistSimplifiedPaging;
    public static int offset = 0;



    public static Paging<PlaylistSimplified>  getPlaylists() {
        try {
             getCategoryRequest = Main.spotifyApi.getCategorysPlaylists(Main.category)
//          .country(CountryCode.SE)
                    .limit(50)
//          .offset(0)
                    .build();

            playlistSimplifiedPaging = getCategoryRequest.execute();
            return playlistSimplifiedPaging;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static void printCategoryList(){
        try {
            boolean isListComplete = false;
            ArrayList<String> categories = new ArrayList<String>();
            while(!isListComplete){
                getListOfCategoriesRequest = Main.spotifyApi.getListOfCategories()
                .limit(50)
                .offset(offset)
                .build();
                Paging<Category> categoryPaging = getListOfCategoriesRequest.execute();
                for(int i = 0; i < categoryPaging.getItems().length; i++){
                    categories.add(categoryPaging.getItems()[i].getName());
                }
                offset+=50;
                if(categoryPaging.getItems().length == 0){
                    isListComplete = true;
                }
            }

            Collections.sort(categories);
            for(String s : categories){
                System.out.println(s);
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
