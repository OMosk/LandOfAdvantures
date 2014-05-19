package km23.loa.web;

/**
 * Created by mosk on 15.05.14.
 */
import km23.loa.web.util.LOADataBaseConnection;
import km23.loa.web.util.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRegistration {
    public boolean isLoginFree(String login){
        boolean result = false;
        try {
            ResultSet resultSet = LOADataBaseConnection.query("SELECT * FROM `users` WHERE `user`='" + login+"'");
            //resultSet.last();
            if(!resultSet.next())result = true;
            //resultSet.beforeFirst();
        }
        catch (SQLException e){
            //Logger.log(e.getMessage());
            e.printStackTrace();
        }
        catch (NullPointerException e){
            //Logger.log(e.getMessage());
            e.printStackTrace();
        }
        finally {
            System.out.println("SELECT * FROM `users` WHERE `user`='" + login+"'");
        }
        return result;
    }
    public boolean registerUser(String login, String password){
        try{
            LOADataBaseConnection.query("INSERT INTO `users`(`user`, `password_hash`) VALUES('"+login+"', '"+ getMD5Hash(password) +"')");
        }
        catch(SQLException e){
            Logger.log(e.getMessage());
        }
        return isLoginFree(login);
    }
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

}
