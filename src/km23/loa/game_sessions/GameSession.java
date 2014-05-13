package km23.loa.game_sessions;

import km23.loa.user.User;
import km23.loa.locations.Location;

import java.util.Observer;

/**
 * Created by mosk on 13.04.14.
 */
public abstract class GameSession extends Thread{
    protected static int count = 0;
    protected int id = ++count;
    protected GameSessionSettings settings;
    protected GameSessionState state = GameSessionState.NOT_ACTIVE;
    protected Location currentLocation;

    public GameSession(){

        currentLocation = new Location();

    }

    public int getSessionId() {
        return id;
    }
    public abstract  void addUser(User user);
    public abstract  void removeUser(User user);
    public void startSession(){
        state = GameSessionState.IN_PROGRESS;
        start();
    }

    public void stopSession(){
        state = GameSessionState.NOT_ACTIVE;

    }

}
