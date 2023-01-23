import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class AuthorizationCodeRefresh {
    private static String code = "";
    private static AuthorizationCodeRequest authorizationCodeRequest;
    private static AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest;
    private static String refreshCode ="";
    public static void init(){
        try{
            File myObj = new File("setup.txt");
            Scanner reader = new Scanner(myObj);
            String data = "";
            while(reader.hasNextLine()){
                data = reader.nextLine();
                if(data.startsWith("code=")){
                    code = data.substring(5);
                    authorizationCodeRequest = Main.spotifyApi.authorizationCode(code)
                            .build();
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void authorizationCodeRefresh() {
        try {
            authorizationCodeRefreshRequest = Main.spotifyApi.authorizationCodeRefresh()
                    .build();
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            Main.spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println(ansi().render("@|red Error: " + e.getMessage()+"|@"));
        }
    }

    public static void execute() {
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            Main.spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            Main.spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

                File myObj = new File("refresh.txt");
                PrintWriter output = new PrintWriter(myObj);
                output.print(authorizationCodeCredentials.getRefreshToken());
                output.close();
                System.out.println(ansi().render("@|green Success|@\n"));

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(new URI("https://github.com/SamTheCoder777/Custom-Spotify-Manager#list-of-commands"));
                }
        } catch (Exception e) {
            System.out.println(ansi().render("@|red Error: " + e.getMessage()+"|@"));
        }
    }

    public static String getRefreshCode(){
        File myObj = new File("refresh.txt");
        try {
            Scanner reader = new Scanner(myObj);
            refreshCode = reader.nextLine();
            return refreshCode;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
