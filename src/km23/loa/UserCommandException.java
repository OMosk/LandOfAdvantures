package km23.loa;

import java.lang.Exception;
/**
 * Created by mosk on 23.04.14.
 */
public class UserCommandException extends Exception {
    protected User user;
    //protected String message;
    public UserCommandException(User user, String message){
        super(message);
        this.user = user;
        //this.message = message;

    }
    public String toString(){
        return user+this.getLocalizedMessage() + this.getStackTrace();
    }
}
