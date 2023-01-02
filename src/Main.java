
import com.neovisionaries.i18n.CountryCode;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Scanner;

import org.fusesource.jansi.AnsiConsole;

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
                    String data = "";
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
                Scanner input2 = new Scanner(System.in);

                System.out.println(ansi().render("@|green Enter command (q to exit): |@"));
                user = input2.nextLine();
                switch (user.toLowerCase().trim()){
                    case "init":
                        AuthorizationCodeUri.execute();
                        break;

                    case "searchartists":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println(ansi().render("@|green Enter name you want to search|@"));
                        name = input.nextLine();
                        System.out.println();
                        GetSearchArtists searching = new GetSearchArtists(spotifyApi, name);
                        System.out.println(searching);
                        System.out.println();
                        break;

                    case "getartiststoptracks":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println(ansi().render("@|green Enter name you want to search|@"));
                        name = input.nextLine();
                        GetArtistsTopTracks top = new GetArtistsTopTracks(spotifyApi, name, CountryCode.US);
                        System.out.println();
                        top.execute();
                        System.out.println();
                        break;

                    case "getrecommendations":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println(ansi().render("@|green Enter the genre you want|@"));
                        genre = input.nextLine();
                        System.out.println(ansi().render("@|green Enter how many recommendations you want|@"));
                        limit = input.nextInt();
                        System.out.println();
                        GetRecommendations.execute();
                        System.out.println();
                        break;

                    case "getgenrelist":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println();
                        GetSeedGenreList.execute();
                        System.out.println();
                        break;

                    case "searchgenre":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        SearchGenre.execute();
                        System.out.println();
                        break;

                    case "getmytopartists":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println(ansi().render("@|green How many top artists would you like to see?|@"));
                        limit = input.nextInt();
                        System.out.println();
                        UserTopArtists.getUsersTopArtists();
                        System.out.println();
                        System.out.println(ansi().render("@|green Would you like to create a playlist filled with above artists? |@@|yellow (y or n)|@"));
                        user = input.next();
                        if(user.equalsIgnoreCase("y")){
                            input.nextLine();
                            System.out.println(ansi().render("@|green Name of the playlist:|@"));
                            name = input.nextLine();
                            System.out.println(ansi().render("@|green How many artists (randomized) should be included in the playlist?|@"));
                            numArtists = input.nextInt();
                            System.out.println(ansi().render("@|green How many songs per artists?|@"));
                            limit = input.nextInt();
                            System.out.println(ansi().render("@|green Include instrumental? |@@|yellow (y or n)|@"));
                            includeInstrumental = input.next().equalsIgnoreCase("y");
                            System.out.println();
                            UserTopArtists.createUsersTopArtistsPlaylist();
                        }
                        System.out.println();
                        break;

                    case "getmytoptracks":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println(ansi().render("@|green How many tracks would you like to see?|@"));
                        limit = input.nextInt();
                        System.out.println();
                        UserTopTracks.execute();
                        System.out.println();
                        System.out.println(ansi().render("@|green Would you like to add the songs into a playlist? |@@|yellow (y or n)|@"));
                        if(input.next().equalsIgnoreCase("y")){
                            input.nextLine();
                            System.out.println(ansi().render("@|green Name of the playlist:|@"));
                            name = input.nextLine();
                            System.out.println();
                            UserTopTracks.createUserTopTrackPlaylist();
                        }
                        System.out.println();
                        break;


                    case "createplaylist":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println(ansi().render("@|green Name of the playlist:|@"));
                        name = input.nextLine();
                        System.out.println();
                        CreatePlaylist.execute();
                        System.out.println();
                        break;

                    case "createcategoryplaylist":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println(ansi().render("@|green Category you want:|@"));
                        category = input.nextLine();
                        System.out.println(ansi().render("@|green Genre preference (in case there are duplicates):|@"));
                        genre = input.nextLine();
                        System.out.println(ansi().render("@|green Name of the playlist:|@"));
                        name = input.nextLine();
                        System.out.println(ansi().render("@|green How many playlists to search?|@ @|yellow (Total # of songs = [# of playlists] X [# of songs per playlist])|@"));
                        numPlaylists = input.nextInt();
                        System.out.println(ansi().render("@|green How many songs per playlist?|@ @|yellow (Total # of songs = [# of playlists] X [# of songs per playlist])|@"));
                        limit = input.nextInt();
                        System.out.println();
                        CreateCategoryPlaylist.execute();
                        System.out.println();
                        break;

                    case "createcustomplaylist":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println(ansi().render("@|green Genre preference|@ @|yellow (This is for in case there are duplicates. Press enter to skip):|@"));
                        genre = input.nextLine();
                        System.out.println(ansi().render("@|green Name of the playlist:|@"));
                        name = input.nextLine();
                        System.out.println(ansi().render("@|green How many playlists to search? |@" +
                                "\n@|yellow (Current number playlists in the file: " + CreateCustomPlaylist.playlistNumbers()+")|@"));
                        numPlaylists = input.nextInt();
                        System.out.println(ansi().render("@|green How many songs per playlist|@ @|yellow [Max: 100]|@@|green ?|@ @|yellow (Total # of songs = [# of playlists] X [# of songs per playlists])|@"));
                        limit = input.nextInt();
                        System.out.println();
                        CreateCustomPlaylist.execute();
                        System.out.println();
                        break;


                    case "createrecommendedplaylist":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println(ansi().render("@|green Genre you want:|@"));
                        genre = input.nextLine();
                        System.out.println(ansi().render("@|green Name of the playlist:|@"));
                        name = input.nextLine();
                        System.out.println();
                        CreateRecommendedPlaylist.execute();
                        System.out.println();
                        break;

                    case "getcategorylist":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        GetCategoryPlaylist.printCategoryList();
                        System.out.println();
                        break;

                    case "createexploreartistsplaylist":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println(ansi().render("@|green What genre?|@"));
                        genre = input.nextLine();
                        System.out.println(ansi().render("@|green Name of the playlist?|@"));
                        name = input.nextLine();
                        System.out.println(ansi().render("@|green How many artists?|@"));
                        numArtists = input.nextInt();
                        System.out.println(ansi().render("@|green How many songs per artist?|@"));
                        limit = input.nextInt();
                        System.out.println(ansi().render("@|green Include instrumental?|@ @|yellow (y or n)|@"));
                        includeInstrumental = input.next().equalsIgnoreCase("y");
                        System.out.println();
                        CreateExploreArtistsPlaylist.execute();
                        System.out.println();
                        break;


                    case "help":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println(ansi().render("\n@|yellow List of commands:|@") +
                                "\n\ninit: Set up program for the first run" +
                                "\n\nCreateCategoryPlaylist: Creates playlist filled with the songs from category playlist" +
                                "\nCreateCustomPlaylist: Creates playlist filled with random songs from the given playlists provided in \"playlistList.txt\"" +
                                "\nCreateExploreArtistsPlaylist: Create playlists filled with artists related with given genre" +
                                "\nCreatePlaylist: Creates playlist with given name" +
                                "\nCreateRecommendedPlaylist: Creates playlist filled with recommended songs for a given genre" +
                                "\n\nGetArtistsTopTracks: Prints out top tracks for a given artist" +
                                "\nGetCategoryList: Prints out list of categories available" +
                                "\nGetGenreList: Prints out whole list for all available genres" +
                                "\nGetMyTopArtists: Prints out the user's most played artists" +
                                "\nGetMyTopTracks: Prints out the user's most played tracks" +
                                "\nGetRecommendations: Prints out recommended tracks for a given genre" +
                                "\n\nSearchArtists: Searches for the artist with given name" +
                                "\nSearchGenre: Prints out genre that starts with given character\n");
                        break;

                    default:
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        if(!user.equalsIgnoreCase("q")){
                            System.out.println(ansi().render("@|red Command: |@" + user + "@|red  does not exist.\nType \"help\" to get list of commands|@"));
                            System.out.println();
                        }else{
                            System.out.println(ansi().render("@|red Terminating...|@"));
                        }
                        break;
                }
        }

        } catch (Exception e) {
            System.out.println(ansi().render("@|red Error: " + e.getMessage()+"|@"));
            System.out.println();
        }
    }
}
