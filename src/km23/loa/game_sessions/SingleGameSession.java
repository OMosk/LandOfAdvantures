package km23.loa.game_sessions;

import km23.loa.User;

import java.util.concurrent.TimeUnit;

/**
 * Created by mosk on 13.04.14.
 */
public class SingleGameSession extends GameSession {
    protected User user;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SingleGameSession(){

    }
    public SingleGameSession(User user){

        this.user = user;
        System.out.println("Created SingleGameSession" + id);
    }
    public void run(){
        try {
                while(state==GameSessionState.IN_PROGRESS){
                    System.out.println("SingleGameSession["+  id + "] perform some actions");
                    TimeUnit.MILLISECONDS.sleep(100);

                }
        }
        catch(InterruptedException e){
            //System.out.println(e + e.getLocalizedMessage());
        }


    }
    public void addUser(User user)
    {
        if(this.user==null)this.user = user;
        else{
            //throw exception
        }
    }
    public void removeUser(User user)
    {
        this.user = null;
        state = GameSessionState.NOT_ACTIVE;

    }
}
