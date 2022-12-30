import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;

public class AuthorizationCodeUri {
    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = Main.spotifyApi.authorizationCodeUri()
//          .state("x4xkmn9pu3j6ukrs8n")
            .scope("user-top-read, playlist-modify-private")
//            .show_dialog(true)
            .build();

    public static void execute() {
        final URI uri = authorizationCodeUriRequest.execute();

        System.out.println("URI: " + uri.toString());

    }
}
