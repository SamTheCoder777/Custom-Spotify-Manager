import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class AuthorizationCodeUri {
    private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = Main.spotifyApi.authorizationCodeUri()
            .scope("user-top-read, playlist-modify-private")
            .show_dialog(true)
            .build();
    private static String code;

    public static void execute() {
        final URI uri = authorizationCodeUriRequest.execute();

        try{

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri);
            }

            System.out.println(ansi().render("@|yellow If a browser does not pop up, please copy and paste this link into your browser:|@"));
            System.out.println(uri.toURL());
            System.out.println();

            ServerSocket ss=new ServerSocket(8080, 1, InetAddress.getByName("127.0.0.1"));
            Socket s=ss.accept();//establishes connection
            var rawIn = s.getInputStream();
            var in = new BufferedReader(new InputStreamReader(rawIn, StandardCharsets.US_ASCII)); {
                while (true){
                    String cmd = in.readLine().trim();
                    if (cmd == null) break; //client is hung up
                    if (cmd.isEmpty()) continue; //empty line was sent
                    if(cmd.indexOf("code") != -1){
                        cmd = cmd.substring(cmd.indexOf("code")+5);
                        cmd = cmd.replaceAll("\\s.*", "");
                        code = cmd;
                        break;
                    }
                }
            }
            ss.close();


            File myObj = new File("setup.txt");

            Scanner reader = new Scanner(myObj, StandardCharsets.UTF_8);
            String data = "";
            String dataToWrite = "";
            while(reader.hasNextLine()){
                data = reader.nextLine();
                if(data.contains("code=")){
                    dataToWrite += "code="+code;
                }else{
                    dataToWrite += "\n"+data;
                }
            }
            PrintWriter writer = new PrintWriter(myObj);
            writer.print(dataToWrite);
            writer.close();

            AuthorizationCodeRefresh.init();
            AuthorizationCodeRefresh.execute();

        }catch(Exception e){System.out.println(ansi().render("@|red Error: " + e.getMessage()+"|@"));}





    }
}
