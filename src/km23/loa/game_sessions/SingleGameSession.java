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
                System.out.println("SingleGameSession["+  id + "] perform some actions");
                while(state==GameSessionState.IN_PROGRESS){

                    TimeUnit.MILLISECONDS.sleep(100);
                    //System.out.println("SingleGameSession["+  id + "] perform some actions");
                }
        }
        catch(InterruptedException e){
            System.out.println("SingleGameSession id[" + id + "] finished.");
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
