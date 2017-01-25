package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Артем on 10.01.2017.
 */
public class Util {
    public static String loadFileAsString(String path){
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line=br.readLine())!=null)
                stringBuilder.append(line + "\n");

            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public static int parseInt(String number){
        try{
            return Integer.parseInt(number);
        }catch(NumberFormatException e){
            e.printStackTrace();
            return 0;
        }
    }
}
