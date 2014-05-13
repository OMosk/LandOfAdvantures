package km23.loa.game_sessions;

import km23.loa.user.User;
import km23.loa.characters.Hero;
import km23.loa.user.UserStatus;
import org.json.simple.JSONObject;

import java.util.concurrent.TimeUnit;

/**
 * Created by mosk on 13.04.14.
 */
public class SingleGameSession extends GameSession {
    protected User user;
    //protected HashMap<Integer, >

    public SingleGameSession(){
        super();
    }
    public SingleGameSession(User user){

        this();
        this.user = user;
        user.setCurrentGameSession(this);

        Hero hero = new Hero(currentLocation, 100, 100, 15, 0.2f);
        user.setHero(hero);
        currentLocation.addHero(hero);

        JSONObject gameSessionData = new JSONObject();
        gameSessionData.put("action", "applyGameSessionData");

        gameSessionData.put("characters", currentLocation.getAllCharactersJSON());

        gameSessionData.put("userCharacterId", hero.getId());

        user.getWebSocket().send(gameSessionData.toJSONString());

        System.out.println("Created SingleGameSession" + id);
    }

    public void run(){
        try {
                user.setStatus(UserStatus.IN_GAME);

                System.out.println("SingleGameSession["+  id + "] perform some actions");

                while(state==GameSessionState.IN_PROGRESS){

                    TimeUnit.MILLISECONDS.sleep(8);

                    currentLocation.update(8);
                    JSONObject stateChange = new JSONObject();
                    stateChange.put("action", "updateGameSessionData");
                    stateChange.put("characters", currentLocation.getAllCharactersChangesJSON());

                    //System.out.println(stateChange.toJSONString());
                    user.getWebSocket().send(stateChange.toJSONString());

                    currentLocation.clearCharactersStateChanges();

                    //user.getWebSocket().send("{ \"x\": "+user.getHero().getX()+", \"y\": "+ user.getHero().getY() +"}");

                }
        }
        catch(InterruptedException e){

        }
        catch(NullPointerException e){

        }
        finally {
            System.out.println("SingleGameSession["+  id + "] finished");
            //user.setStatus(UserStatus.OUT_OF_SESSION);
        }
    }

    public void addUser(User user)
    {
        if(this.user==null){
            this.user = user;
            user.setCurrentGameSession(this);
        }
        else{
            //throw exception
        }
    }
    public void removeUser(User user)
    {

        state = GameSessionState.NOT_ACTIVE;
        this.user.setCurrentGameSession(null);
        this.user = null;

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
