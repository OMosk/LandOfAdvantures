package km23.loa;

import km23.loa.characters.Hero;
import km23.loa.game_sessions.GameSession;
import org.java_websocket.WebSocket;
import org.json.simple.JSONObject;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by mosk on 13.04.14.
 */
public class User implements Observer{


    protected String name = "defaultName";
    protected WebSocket webSocket;
    static protected int count = 0;
    protected final int id = ++count;
    protected Hero hero;
    public UserStatus status = UserStatus.NEW_USER;
    //protected JSONParser parser = new JSONParser();
    GameSession currentGameSession;

    public User(WebSocket webSocket){
        this.webSocket = webSocket;
        //name = "";
    }
    public GameSession getCurrentGameSession() {return currentGameSession;}
    public void setCurrentGameSession(GameSession currentGameSession) {
        this.currentGameSession = currentGameSession;
    }
    public int getId() {
        return id;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public Hero getHero() {
        return hero;
    }
    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void update(Observable o, Object arg){

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void handleInput(JSONObject command){


    }
    public void joinGameSession(GameSession session){
        this.currentGameSession = session;
    }
    public void leaveGameSession(){

    }
    public String toString()
    {
        return name + "[" + id + "]";
    }
}
