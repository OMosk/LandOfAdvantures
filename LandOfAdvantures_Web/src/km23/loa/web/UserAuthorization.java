package km23.loa.web;

/**
 * Created by mosk on 14.05.14.
 */

import km23.loa.web.util.LOADataBaseConnection;
import km23.loa.web.util.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UserAuthorization {


    protected boolean authorized = false;
    protected String login;

    public boolean checkUserLoginPassword(String login, String password){
        boolean result = false;
        try{
            ResultSet users = LOADataBaseConnection.query("SELECT * FROM `users` WHERE `user` = '" + login + "' AND `password_hash` = '" + getMD5Hash(password) + "'");
            users.last();
            if(users.getRow()>=1)result = true;
            users.beforeFirst();
        }
        catch (SQLException e){
            Logger.log(e.getMessage());
        }
        return result;
    }
    public void authorizeUser(String login, String session_id, String session_hash){
        try{
            LOADataBaseConnection.query("UPDATE `users` SET `session_id` = '" + session_id + "', 'session_hash' = '" + session_hash + "' WHERE `user` = '" + login + "' LIMIT 1");
            this.login = login;
            authorized = true;
        }
        catch (SQLException e){
            Logger.log(e.getMessage());
        }
    }
    //public boolean checkUserAu
    protected String getMD5Hash(String string){
        String md5Hash = null;
        byte[] stringBytes = null;
        try {
            stringBytes = string.getBytes("UTF-8");
        }
        catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashDigits = md.digest(stringBytes);
            StringBuilder sb = new StringBuilder(2*hashDigits.length);
            for(byte b : hashDigits){
                sb.append(String.format("%02x", b&0xff));
            }

            md5Hash = sb.toString();
//            try {
//                //md5Hash = new String(hashDigits, "UTF-8");
//
//
//
//            }
//            catch(UnsupportedEncodingException e){
//                e.printStackTrace();
//            }
        }
        catch (NoSuchAlgorithmException e){
            //Logger.log(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("md5 " + string + " =" + md5Hash);
        System.out.println("md5 " + string + " =");
        return md5Hash;
    }
    public boolean isAuthorized() {
        return authorized;
    }

    public String getLogin() {
        return login;
    }
}
