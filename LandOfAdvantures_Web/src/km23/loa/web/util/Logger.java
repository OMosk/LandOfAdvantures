package km23.loa.web.util;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by mosk on 15.05.14.
 */
public class Logger {
    public static void log(String message){
        try
        {
            String filename= "/home/mosk/www/loa.log";
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write(message + "\n");//appends the string to the file
            fw.close();

        }
        catch(IOException ioe)
        {

        }
    }
}
