
import com.neovisionaries.i18n.CountryCode;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Scanner;


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


    public static void main(String[] args){

        try{
            String user = "";

            while(!user.equalsIgnoreCase("q")){
                try{
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
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
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

                System.out.println("Enter command (q to exit): ");
                user = input2.nextLine();
                switch (user.toLowerCase().trim()){
                    case "init":
                        try{

                            AuthorizationCodeUri.execute();
                            System.out.println("press any key to continue");
                            input.nextLine();
                            AuthorizationCodeRefresh.init();
                            AuthorizationCodeRefresh.execute();
                            System.out.println("Success");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        break;

                    case "searchartists":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println("Enter name you want to search");
                        name = input.nextLine();
                        GetSearchArtists searching = new GetSearchArtists(spotifyApi, name);
                        System.out.println(searching);
                        System.out.println();
                        break;

                    case "getartisttoptracks":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println("Enter name you want to search");
                        name = input.nextLine();
                        GetArtistsTopTracks top = new GetArtistsTopTracks(spotifyApi, name, CountryCode.NA);
                        top.execute();
                        System.out.println();
                        break;

                    case "getrecommendations":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println("Enter the genre you want");
                        genre = input.nextLine();
                        System.out.println("Enter how many recommendations you want");
                        limit = input.nextInt();
                        GetRecommendations.execute();
                        System.out.println();
                        break;

                    case "getgenrelist":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
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
                        System.out.println("How many top artists would you like to see?");
                        limit = input.nextInt();
                        UserTopArtists.execute();
                        System.out.println();
                        break;

                    case "getmytoptracks":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println("How many tracks would you like to see?");
                        limit = input.nextInt();
                        UserTopTracks.execute();
                        System.out.println();
                        break;


                    case "createplaylist":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println("Name of the playlist:");
                        name = input.nextLine();
                        CreatePlaylist.execute();
                        break;

                    case "createcategoryplaylist":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println("Category you want:");
                        category = input.nextLine();
                        System.out.println("Genre preference (in case there are duplicates):");
                        genre = input.nextLine();
                        System.out.println("Name of the playlist:");
                        name = input.nextLine();
                        System.out.println("How many playlists to search? (Total # of songs = [# of playlists] X [# of songs per playlist])");
                        numPlaylists = input.nextInt();
                        System.out.println("How many songs per playlist? (Total # of songs = [# of playlists] X [# of songs per playlists])");
                        limit = input.nextInt();
                        CreateCategoryPlaylist.execute();
                        System.out.println();
                        break;

                    case "createcustomplaylist":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println("Genre preference (in case there are duplicates. Press enter to skip):");
                        genre = input.nextLine();
                        System.out.println("Name of the playlist:");
                        name = input.nextLine();
                        System.out.println("How many playlists to search? " +
                                "\n(Current number playlists in the file: " + CreateCustomPlaylist.playlistNumbers()+")");
                        numPlaylists = input.nextInt();
                        System.out.println("How many songs per playlist [Max: 100]? (Total # of songs = [# of playlists] X [# of songs per playlists])");
                        limit = input.nextInt();
                        CreateCustomPlaylist.execute();
                        System.out.println();
                        break;


                    case "createrecommendedplaylist":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println("Genre you want:");
                        genre = input.nextLine();
                        System.out.println("Name of the playlist:");
                        name = input.nextLine();
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
                        System.out.println("What genre?");
                        genre = input.nextLine();
                        System.out.println("Name of the playlist?");
                        name = input.nextLine();
                        System.out.println("How many artists?");
                        numArtists = input.nextInt();
                        System.out.println("How many songs per artist?");
                        limit = input.nextInt();
                        CreateExploreArtistsPlaylist.execute();
                        System.out.println();
                        break;


                    case "help":
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        System.out.println("List of commands:");
                        System.out.println("init: Set up program for the first run");
                        System.out.println();
                        System.out.println("CreateCategoryPlaylist: Creates playlist filled with the songs from category playlist");
                        System.out.println("CreateCustomPlaylist: Creates playlist filled with random songs from the given playlists provided in \"playlistList.txt\"");
                        System.out.println("CreateExploreArtistsPlaylist: Create playlists filled with artists related with given genre");
                        System.out.println("CreatePlaylist: Creates playlist with given name");
                        System.out.println("CreateRecommendedPlaylist: Creates playlist filled with recommended songs for a given genre");
                        System.out.println();
                        System.out.println("GetArtistTopTracks: Prints out top tracks for a given artist");
                        System.out.println("GetCategoryList: Prints out list of categories available");
                        System.out.println("GetGenreList: Prints out whole list for all available genres");
                        System.out.println("GetMyTopArtists: Prints out the user's most played artists");
                        System.out.println("GetMyTopTracks: Prints out the user's most played tracks");
                        System.out.println("GetRecommendations: Prints out recommended tracks for a given genre");
                        System.out.println();
                        System.out.println("SearchArtists: Searches for the artist with given name");
                        System.out.println("SearchGenre: Prints out genre that starts with given character");
                        System.out.println();
                        break;

                    default:
                        AuthorizationCodeRefresh.authorizationCodeRefresh();
                        if(!user.equalsIgnoreCase("q")){
                            System.out.println("Command: " + user + " does not exist.\nType \"help\" to get list of commands");
                            System.out.println();
                        }else{
                            System.out.println("Terminating...");
                        }
                        break;
                }
        }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println();
            throw new RuntimeException(e);
        }
    }
}
