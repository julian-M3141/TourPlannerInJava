package tourplanner.businessLayer.tourMap;

import java.io.File;
import java.util.Random;

public class FileNameGenerator {
    static String getUniqueFilename(String postfix,String path){
        String filename="";
        final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random r = new Random();
        do{
            filename = "";
            for(int i=0;i<10;++i){
                filename += alphabet.charAt(r.nextInt(alphabet.length()));
            }
            filename += "."+postfix;
        }while (new File(path+filename).exists());
        return filename;
    }
}
