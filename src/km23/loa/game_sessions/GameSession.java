package km23.loa.game_sessions;

import km23.loa.User;
import km23.loa.locations.Location;

/**
 * Created by mosk on 13.04.14.
 */
public abstract class GameSession implements Runnable{
    protected static int count = 0;
    protected int id = ++count;
    protected GameSessionSettings settings;
    protected GameSessionState state = GameSessionState.NOT_ACTIVE;
    protected Location currentLocation;

    public GameSession()
    {
        currentLocation = new Location();
    }

    public int getId() {
        return id;
    }
    abstract public void addUser(User user);
    abstract public void removeUser(User user);




}
