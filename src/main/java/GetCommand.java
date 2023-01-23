import com.neovisionaries.i18n.CountryCode;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class GetCommand {
    public static void execute(String user){
        Scanner input = new Scanner(System.in);
        switch (user.toLowerCase().trim()) {
            case "init" -> AuthorizationCodeUri.execute();
            case "searchartists" -> {
                AuthorizationCodeRefresh.authorizationCodeRefresh();
                System.out.println(ansi().render("@|green Enter name you want to search|@"));
                Main.name = input.nextLine();
                System.out.println();
                GetSearchArtists searching = new GetSearchArtists(Main.spotifyApi, Main.name);
                System.out.println(searching);
                System.out.println();
            }
            case "getartiststoptracks" -> {
                AuthorizationCodeRefresh.authorizationCodeRefresh();
                System.out.println(ansi().render("@|green Enter name you want to search|@"));
                Main.name = input.nextLine();
                GetArtistsTopTracks top = new GetArtistsTopTracks(Main.spotifyApi, Main.name, CountryCode.US);
                System.out.println();
                top.execute();
                System.out.println();
            }
            case "getrecommendations" -> {
                AuthorizationCodeRefresh.authorizationCodeRefresh();
                System.out.println(ansi().render("@|green Enter the genre you want|@"));
                Main.genre = input.nextLine();
                System.out.println(ansi().render("@|green Enter how many recommendations you want|@"));
                Main.limit = input.nextInt();
                System.out.println();
                GetRecommendations.execute();
                System.out.println();
            }
            case "getgenrelist" -> {
                AuthorizationCodeRefresh.authorizationCodeRefresh();
                System.out.println();
                GetSeedGenreList.execute();
                System.out.println();
            }
            case "searchgenre" -> {
                AuthorizationCodeRefresh.authorizationCodeRefresh();
                SearchGenre.execute();
                System.out.println();
            }
            case "getmytopartists" -> {
                AuthorizationCodeRefresh.authorizationCodeRefresh();
                System.out.println(ansi().render("@|green How many top artists would you like to see?|@"));
                Main.limit = input.nextInt();
                System.out.println();
                UserTopArtists.getUsersTopArtists();
                System.out.println();
                System.out.println(ansi().render("@|green Would you like to create a playlist filled with above artists? |@@|yellow (y or n)|@"));
                user = input.next();
                if (user.equalsIgnoreCase("y")) {
                    input.nextLine();
                    System.out.println(ansi().render("@|green Name of the playlist:|@"));
                    Main.name = input.nextLine();
                    System.out.println(ansi().render("@|green Would you like the playlist to be public? |@@|yellow (y or n)|@"));
                    Main.playlistPublic = (input.nextLine().equalsIgnoreCase("y"));
                    System.out.println(ansi().render("@|green How many artists (randomized) should be included in the playlist?|@"));
                    Main.numArtists = input.nextInt();
                    System.out.println(ansi().render("@|green How many songs per artists?|@"));
                    Main.limit = input.nextInt();
                    System.out.println(ansi().render("@|green Include instrumental? |@@|yellow (y or n)|@"));
                    Main.includeInstrumental = input.next().equalsIgnoreCase("y");
                    System.out.println();
                    UserTopArtists.createUsersTopArtistsPlaylist();
                }
                System.out.println();
            }
            case "getmytoptracks" -> {
                AuthorizationCodeRefresh.authorizationCodeRefresh();
                System.out.println(ansi().render("@|green How many tracks would you like to see?|@"));
                Main.limit = input.nextInt();
                System.out.println();
                UserTopTracks.execute();
                System.out.println();
                System.out.println(ansi().render("@|green Would you like to add the songs into a playlist? |@@|yellow (y or n)|@"));
                if (input.next().equalsIgnoreCase("y")) {
                    input.nextLine();
                    System.out.println(ansi().render("@|green Name of the playlist:|@"));
                    Main.name = input.nextLine();
                    System.out.println(ansi().render("@|green Would you like the playlist to be public? |@@|yellow (y or n)|@"));
                    Main.playlistPublic = (input.nextLine().equalsIgnoreCase("y"));
                    System.out.println();
                    UserTopTracks.createUserTopTrackPlaylist();
                }
                System.out.println();
            }
            case "createplaylist" -> {
                AuthorizationCodeRefresh.authorizationCodeRefresh();
                System.out.println(ansi().render("@|green Name of the playlist:|@"));
                Main.name = input.nextLine();
                System.out.println(ansi().render("@|green Would you like the playlist to be public? |@@|yellow (y or n)|@"));
                Main.playlistPublic = (input.nextLine().equalsIgnoreCase("y"));
                System.out.println();
                CreatePlaylist.execute();
                System.out.println();
            }
            case "createcategoryplaylist" -> {
                AuthorizationCodeRefresh.authorizationCodeRefresh();
                System.out.println(ansi().render("@|green Category you want:|@"));
                Main.category = input.nextLine();
                System.out.println(ansi().render("@|green Genre preference (in case there are duplicates):|@"));
                Main.genre = input.nextLine();
                System.out.println(ansi().render("@|green Name of the playlist:|@"));
                Main.name = input.nextLine();
                System.out.println(ansi().render("@|green Would you like the playlist to be public? |@@|yellow (y or n)|@"));
                Main.playlistPublic = (input.nextLine().equalsIgnoreCase("y"));
                System.out.println(ansi().render("@|green How many playlists to search?|@ @|yellow (Total # of songs = [# of playlists] X [# of songs per playlist])|@"));
                Main.numPlaylists = input.nextInt();
                System.out.println(ansi().render("@|green How many songs per playlist?|@ @|yellow (Total # of songs = [# of playlists] X [# of songs per playlist])|@"));
                Main.limit = input.nextInt();
                System.out.println();
                CreateCategoryPlaylist.execute();
                System.out.println();
            }
            case "createcustomplaylist" -> {
                AuthorizationCodeRefresh.authorizationCodeRefresh();
                System.out.println(ansi().render("@|green Genre preference|@ @|yellow (This is for in case there are duplicates. Press enter to skip):|@"));
                Main.genre = input.nextLine();
                System.out.println(ansi().render("@|green Name of the playlist:|@"));
                Main.name = input.nextLine();
                System.out.println(ansi().render("@|green Would you like the playlist to be public? |@@|yellow (y or n)|@"));
                Main.playlistPublic = (input.nextLine().equalsIgnoreCase("y"));
                System.out.println(ansi().render("@|green How many playlists to search? |@" +
                        "\n@|yellow (Current number playlists in the file: " + CreateCustomPlaylist.playlistNumbers() + ")|@"));
                Main.numPlaylists = input.nextInt();
                System.out.println(ansi().render("@|green How many songs per playlist|@ @|yellow [Max: 100]|@@|green ?|@ @|yellow (Total # of songs = [# of playlists] X [# of songs per playlists])|@"));
                Main.limit = input.nextInt();
                System.out.println();
                CreateCustomPlaylist.execute();
                System.out.println();
            }
            case "createrecommendedplaylist" -> {
                AuthorizationCodeRefresh.authorizationCodeRefresh();
                System.out.println(ansi().render("@|green Genre you want:|@"));
                Main.genre = input.nextLine();
                System.out.println(ansi().render("@|green Name of the playlist:|@"));
                Main.name = input.nextLine();
                System.out.println(ansi().render("@|green Would you like the playlist to be public? |@@|yellow (y or n)|@"));
                Main.playlistPublic = (input.nextLine().equalsIgnoreCase("y"));
                System.out.println();
                CreateRecommendedPlaylist.execute();
                System.out.println();
            }
            case "getcategorylist" -> {
                AuthorizationCodeRefresh.authorizationCodeRefresh();
                GetCategoryPlaylist.printCategoryList();
                System.out.println();
            }
            case "createexploreartistsplaylist" -> {
                AuthorizationCodeRefresh.authorizationCodeRefresh();
                System.out.println(ansi().render("@|green What genre?|@"));
                Main.genre = input.nextLine();
                System.out.println(ansi().render("@|green Name of the playlist?|@"));
                Main.name = input.nextLine();
                System.out.println(ansi().render("@|green Would you like the playlist to be public? |@@|yellow (y or n)|@"));
                Main.playlistPublic = (input.nextLine().equalsIgnoreCase("y"));
                System.out.println(ansi().render("@|green How many artists?|@"));
                Main.numArtists = input.nextInt();
                System.out.println(ansi().render("@|green How many songs per artist?|@"));
                Main.limit = input.nextInt();
                System.out.println(ansi().render("@|green Include instrumental?|@ @|yellow (y or n)|@"));
                Main.includeInstrumental = input.next().equalsIgnoreCase("y");
                System.out.println();
                CreateExploreArtistsPlaylist.execute();
                System.out.println();
            }
            case "help" -> {
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
            }
            default -> {
                AuthorizationCodeRefresh.authorizationCodeRefresh();
                if (!user.equalsIgnoreCase("q")) {
                    System.out.println(ansi().render("@|red Command: |@" + user + "@|red  does not exist.\nType \"help\" to get list of commands|@"));
                    System.out.println();
                } else {
                    System.out.println(ansi().render("@|red Terminating...|@"));
                }
            }
        }
    }
}
