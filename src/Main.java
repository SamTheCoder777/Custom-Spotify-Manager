import org.fusesource.jansi.AnsiConsole;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.io.File;
import java.net.URI;
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


    public static void main(String[] args){

        try{
            AnsiConsole.systemInstall();
            String user = "";

            while(!user.equalsIgnoreCase("q")){
                    File myObj = new File("setup.txt");
                    Scanner reader = new Scanner(myObj);
                    String data;
                    while(reader.hasNextLine()){
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
