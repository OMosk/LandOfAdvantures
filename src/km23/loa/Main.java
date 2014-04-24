package km23.loa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import km23.loa.game_sessions.GameSession;
import km23.loa.game_sessions.SingleGameSession;
import km23.loa.UserCommandException;

import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.framing.Framedata;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Main extends WebSocketServer{
    //ArrayList<WebSocket> clients = new ArrayList<WebSocket>();
    HashMap<WebSocket, User> clients = new HashMap<WebSocket, User>();
    HashMap<Integer, GameSession> sessions = new HashMap<Integer, GameSession>();
    JSONParser parser = new JSONParser();

    public Main(int port){
        super(new InetSocketAddress(port));
    }
    public static void main(String[] args) throws InterruptedException , IOException {
	    System.out.println("Hello world");

        Main s = new Main( 8887 );
        s.start();
        System.out.println( "Server started on port: " + s.getPort() );

        BufferedReader sysin = new BufferedReader( new InputStreamReader( System.in ) );
        while ( true ) {
            String in = sysin.readLine();
            if( in.equals( "exit" ) ) {
                try {
                    s.stop();
                }
                catch(InterruptedException e){
                    //
                }
                catch(IOException e){

                }
                break;
            } else if( in.equals( "restart" ) ) {
                s.stop();
                s.start();
                break;
            }
        }
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake){
        System.out.println(handshake.getResourceDescriptor());
        clients.put(conn, new User(conn));
    }

    @Override
    public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
        //this.sendToAll( conn + " has left the room!" );
        clients.remove(conn);
        System.out.println( conn + " has left the room!" );
    }

    @Override
    public void onMessage( WebSocket conn, String message ) {

        User temporaryUser = clients.get(conn);
        System.out.println("User " + temporaryUser + "sendMessage: " + message);
        try {
            JSONObject command = (JSONObject) parser.parse(message);
            performCommand(command, temporaryUser);

        }
        catch(UserCommandException e){
            System.out.println(e);
        }
        catch(ParseException e){
            System.out.println(e + "Bad command(could not parse) from user " + temporaryUser.getName() );
        }

    }


    public void onFragment( WebSocket conn, Framedata fragment ) {
        System.out.println(conn + "(received fragment): " + fragment );
    }
    public void sendToAll(String message)
    {

    }
    @Override
    public void onError( WebSocket conn, Exception ex ) {
        ex.printStackTrace();
        if( conn != null ) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }
    public void performCommand(JSONObject command, User user) throws UserCommandException
    {
        try{
            String commandType = (String) command.get("commandType");
            if(commandType!=null && commandType.equals("system")){

                String action = (String)command.get("action");

                if(action!=null && action.equals("setName"))
                    user.setName((String)command.get("name"));
                else
                if(action!=null && action.equals("createSingleGameSession")){
                    SingleGameSession session = new SingleGameSession(user);
                    int id = (int)session.getId();
                    sessions.put(id, session);
                    user.getWebSocket().send("singleGamesSession was created");
                    //user.getWebSocket()
                    session.startSession();
                }
            }else
            if(commandType.equals("hero"))
            {
                user.handleInput(command);
            }
            else
                throw new UserCommandException(user, "Unknown command type or no type");

        }
        catch(ClassCastException e)
        {
            System.out.println(e + " Wrong type of field `commandType`");
        }
    }

    public void stop() throws InterruptedException, IOException{

        try {
            for(GameSession i: sessions.values()) i.stop();
            //for(User i: clients.values()) i.getWebSocket().close(0);

            super.stop();
        }
        catch(InterruptedException e){
            //
            throw e;
        }
        catch(IOException e){
            //
            throw e;
        }


    }

}
