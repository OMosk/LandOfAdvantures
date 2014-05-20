package km23.loa.web;

import km23.loa.web.util.LOADataBaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mosk on 20.05.14.
 */
public class UsersData {
    protected List<Map<String, Object>> users = new ArrayList<>();
    public UsersData(){
        try {
            ResultSet resultSet = LOADataBaseConnection.query("SELECT * FROM `users`");
            while(resultSet.next()){
                Map<String, Object> user = new HashMap<String, Object>();
                //for(int i = 0; resultSet.getMetaData().getColumnName(i)!=null; i++){
                for(int i = 1; i<=resultSet.getMetaData().getColumnCount(); i++){
                    user.put(resultSet.getMetaData().getColumnName(i), resultSet.getObject(i));
                }
                users.add(user);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> getUsers() {
        return users;
    }
}
