package km23.loa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
//import java.util.Collection;
import java.util.ArrayList;
import java.util.Random;

import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.framing.Framedata;


public class Main extends WebSocketServer{
    ArrayList<WebSocket> clients = new ArrayList<WebSocket>();
    public Main(int port){
        super( new InetSocketAddress(port));
    }
    public static void main(String[] args) throws InterruptedException , IOException {
	// write your code here
        System.out.println("Hello world");


        Main s = new Main( 8887 );
        s.start();
        System.out.println( "ChatServer started on port: " + s.getPort() );
        int x = 100, y = 100;
        Random rand = new Random();
        BufferedReader sysin = new BufferedReader( new InputStreamReader( System.in ) );
        while ( true ) {
            String in = sysin.readLine();

            x += rand.nextInt()%3 - 1;
            y += rand.nextInt()%3 - 1;

            s.sendToAll( "{\"x\": "+ x + ", \"y\": "+y+"}" );
            System.out.println("Send: " + "{\"x\": "+ x + ", \"y\": "+y+"}");
            if( in.equals( "exit" ) ) {
                s.stop();
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
        clients.add(conn);
    }

    @Override
    public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
        //this.sendToAll( conn + " has left the room!" );
        clients.remove(conn);
        System.out.println( conn + " has left the room!" );
    }

    @Override
    public void onMessage( WebSocket conn, String message ) {
        this.sendToAll( message );
        System.out.println( conn + ": " + message );
    }


    public void onFragment( WebSocket conn, Framedata fragment ) {
        System.out.println(conn + "(received fragment): " + fragment );
    }
    public void sendToAll(String message)
    {
        for(WebSocket c: clients){
            c.send(message);
        }
    }
    @Override
    public void onError( WebSocket conn, Exception ex ) {
        ex.printStackTrace();
        if( conn != null ) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }
}
