package km23.loa.web.util;


import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.util.Scanner;
import java.io.File;
/**
 * Created by mosk on 14.05.14.
 */
public class LOADataBaseConnection {
    protected static LOADataBaseConnection instance = null;
    protected Connection connection = null;
    protected Statement statement = null;
    protected ResultSet resultSet = null;
    protected LOADataBaseConnection(){
        File dbConfFile = null;
        Scanner scanner = null;
        String connectionParametersString = null;
        JSONObject connectionParameters = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        try
        {
            //dbConfFile = new File(GlobalParameters.getServletContext().getContextPath() + "/WEB-INF/conf/db.json");
            dbConfFile = new File(GlobalParameters.getServletContext().getRealPath("/WEB-INF/conf/db.json"));
            scanner = new Scanner(dbConfFile);
            connectionParametersString = scanner.useDelimiter("\\Z").next();
        }
        catch (FileNotFoundException e){
            //Logger.log(e.getMessage());
            e.printStackTrace();
        }
        finally {
            if(scanner!=null) scanner.close();
        }

        if(connectionParametersString!=null){
            try {
                connectionParameters = (JSONObject)jsonParser.parse(connectionParametersString);
            }
            catch (ParseException e){
                Logger.log(e.getMessage());
                e.printStackTrace();
            }
        }

        String host = (String)connectionParameters.get("host");
        String user = (String)connectionParameters.get("user");
        String password = (String)connectionParameters.get("password");
        String dbName = (String)connectionParameters.get("db_name");
        System.out.println("jdbc:mysql://" + host + "/" + dbName + "?user=" + user + "&password=" + password);
//        String host = "localhost";
//        String user = "loa";
//        String password = "loa";
//        String dbName = "loa";

        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + dbName + "?user=" + user + "&password=" + password);
            //statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        }
        catch(Exception e){
            Logger.log(e.getMessage());
            e.printStackTrace();
        }
    }

    public static ResultSet query(String queryString) throws SQLException{
        ResultSet result = null;
        try{
            if(instance==null){
                instance = new LOADataBaseConnection();
            }
            //instance.statement.execute(queryString);
            //instance.resultSet = instance.statement.getResultSet();
            instance.statement = instance.connection.createStatement();
            result = instance.resultSet = instance.statement.executeQuery(queryString);
            System.out.println("ResultSet==null is" + instance.resultSet==null);
            //return instance.resultSet;
        }
        catch(SQLException e){
            Logger.log(e.getMessage());
            System.out.println("Query:" + queryString);
            e.printStackTrace();
            throw  e;
            //return null;
        }
        return result;
    }
    public static ResultSet getResultSet(){
        if(instance == null) return null;
        else return instance.resultSet;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        //if(instance==)
        resultSet.close();
        statement.close();
        connection.close();
    }
}
