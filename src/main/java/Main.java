import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.neovisionaries.i18n.CountryCode;
import org.fusesource.jansi.AnsiConsole;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Main {
    public static String token;
    public static String clientID;
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080");
    static SpotifyApi spotifyApi;

    static String name;
    static String genre;
    static String category;
    static int limit;
    static int numPlaylists;
    static int numArtists;
    static boolean includeInstrumental = false;

    static boolean playlistPublic = false;
    public static CountryCode location = CountryCode.US;


    public static void main(String[] args) {

        try {
            //get location of the user
            String sURL = "http://ip-api.com/json/?fields=status,countryCode";

            // Connect to the URL using java's native library
            URL url = new URL(sURL);
            URLConnection request = url.openConnection();
            request.connect();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            String countryCode = rootobj.get("countryCode").getAsString(); //just grab the countryCode
            String status = rootobj.get("status").getAsString();

            //on success, set the location
            if (status.equalsIgnoreCase("success")) {
                location = CountryCode.getByAlpha2Code(countryCode);
            }

            AnsiConsole.systemInstall();
            String user = "";

            while (!user.equalsIgnoreCase("q")) {
                File myObj = new File("setup.txt");
                Scanner reader = new Scanner(myObj);
                String data;

                while (reader.hasNextLine()) {
                    data = reader.nextLine();
                    if(data.startsWith("clientID=")){
                        clientID = data.substring(9);
                    }
                    if(data.startsWith("token=")){
                        token = data.substring(6);
                    }
                }

                spotifyApi = new SpotifyApi.Builder()
                        .setClientSecret(token)
                        .setClientId(clientID)
                        .setRedirectUri(redirectUri)
                        .build();

                try{
                    spotifyApi.setRefreshToken(AuthorizationCodeRefresh.getRefreshCode());
                    if(clientID.length() == 0 || token.length() == 0)
                        throw new Exception();
                } catch (Exception e) {
                    System.out.println("Detected first run\nPlease run \"init\" to set up\n");
                }

                Scanner input = new Scanner(System.in);

                System.out.println(ansi().render("@|green Enter command (q to exit): |@"));
                user = input.nextLine();

                GetCommand.execute(user);
            }

        } catch (Exception e) {
            System.out.println(ansi().render("@|red Error: " + e.getMessage()+"|@"));
            System.out.println();
        }
    }
}
