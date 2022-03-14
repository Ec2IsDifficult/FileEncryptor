package sample;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    public static String KeyStorePath = "src/keyStore.txt";
    public static String keyStorePassword = "soda";
    public static String readFileAsString(Path plaintextInformation){
        String out = "";
        try{
            out = Files.readString(plaintextInformation);
        }catch(Exception e){
            e.printStackTrace();
        }
        return out;
    }


    public static byte[] readAllBytes(Path plaintextInformation){
        byte[] bytesRead = {};
        try{
            bytesRead = Files.readAllBytes(plaintextInformation);
        }catch(Exception e){
            e.printStackTrace();
        }
        return bytesRead;
    }

    public static byte[] readAllBytes(String transformation, String plaintextInformation){
        byte[] bytesRead = {};
        try{
            bytesRead = Files.readAllBytes(Paths.get(plaintextInformation));
        }catch(Exception e){
            e.printStackTrace();
        }
        return bytesRead;
    }

    public static void write(Path path, byte[] output) {
        String outfile = "";
        try{
            Files.write(path, output);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
