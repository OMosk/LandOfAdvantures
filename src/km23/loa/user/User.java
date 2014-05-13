package km23.loa.user;

import km23.loa.characters.Hero;
import km23.loa.game_sessions.GameSession;
import org.java_websocket.WebSocket;
import org.json.simple.JSONObject;

/**
 * Created by mosk on 13.04.14.
 */
public class User{


    protected String name = "defaultName";
    protected WebSocket webSocket;
    static protected int count = 0;
    protected final int id = ++count;
    protected Hero hero;

    protected UserStatus status = UserStatus.NEW_USER;
    //protected JSONParser parser = new JSONParser();
    GameSession currentGameSession;
    UserControl control = new UserControl();

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


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void handleInput(JSONObject command){
        //Press
        if(((String)command.get("action")).equals("pressedUp")) control.up = true;
        if(((String)command.get("action")).equals("pressedDown")) control.down = true;
        if(((String)command.get("action")).equals("pressedLeft")) control.left = true;
        if(((String)command.get("action")).equals("pressedRight")) control.right = true;

        if(((String)command.get("action")).equals("pressedSpeedup")) control.speedup = true;

        if(((String)command.get("action")).equals("pressedWeaponAttack")) control.weaponAttack = true;
        if(((String)command.get("action")).equals("pressedMagicAttack")) control.magicAttack = true;

        //Release
        if(((String)command.get("action")).equals("releasedUp")) control.up = false;
        if(((String)command.get("action")).equals("releasedDown")) control.down = false;
        if(((String)command.get("action")).equals("releasedLeft")) control.left = false;
        if(((String)command.get("action")).equals("releasedRight")) control.right = false;

        if(((String)command.get("action")).equals("releasedSpeedup")) control.speedup = false;

        if(((String)command.get("action")).equals("releasedWeaponAttack")) control.weaponAttack = false;
        if(((String)command.get("action")).equals("releasedMagicAttack")) control.magicAttack = false;

        if(control.up && !control.left && !control.right && !control.down) hero.setDirection(0);
        else
        if(control.up && !control.left && control.right && !control.down) hero.setDirection(1);
        else
        if(!control.up && !control.left && control.right && !control.down) hero.setDirection(2);
        else
        if(!control.up && !control.left && control.right && control.down) hero.setDirection(3);
        else
        if(!control.up && !control.left && !control.right && control.down) hero.setDirection(4);
        else
        if(!control.up && control.left && !control.right && control.down) hero.setDirection(5);
        else
        if(!control.up && control.left && !control.right && !control.down) hero.setDirection(6);
        else
        if(control.up && control.left && !control.right && !control.down) hero.setDirection(7);

        //if(control.up || control.right || control.down || control.left) hero.setMoving(true);
        //else hero.setMoving(false);
        if((control.up || control.right || control.down || control.left) && !hero.isMoving()) hero.setMoving(true);
        else if(!(control.up || control.right || control.down || control.left) && hero.isMoving()) hero.setMoving(false);

        //System.out.println(hero.getDirection() + "  moving" + hero.isMoving());

        if(control.speedup) hero.setVelocity(hero.getDefaultVelocity() * 2);
        else hero.setVelocity(hero.getDefaultVelocity());



    }
    public void joinGameSession(GameSession session){
        this.currentGameSession = session;
    }
    public void leaveGameSession(){
        currentGameSession.removeUser(this);
    }
    public String toString()
    {
        return name + "[" + id + "]";
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

}
