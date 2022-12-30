import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.IOException;
import java.util.Scanner;
public class SearchGenre {
    private static String alphabet = "";
    public static void execute(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the starting alphabet of the genre to search");
        alphabet = input.nextLine();
        while(alphabet.length() > 1){
            System.out.println("Please enter the starting alphabet only!\nPlease enter the alphabet again:");
            alphabet = input.nextLine();
        }
        try{
            final String[] strings = Main.spotifyApi.getAvailableGenreSeeds().build().execute();
            char[] genreChar;
            char[] alphabetChar = alphabet.toCharArray();
            for(String genre : strings){
                genreChar = genre.toCharArray();
                if(genreChar[0] == alphabetChar[0]){
                    System.out.println(genre);
                }
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
