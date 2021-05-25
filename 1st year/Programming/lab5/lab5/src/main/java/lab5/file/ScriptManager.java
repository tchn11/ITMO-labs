package lab5.file;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static lab5.console.ConsoleManager.PrintErr;

/**
 * Operates the script file for opening/reading.
 */
public class ScriptManager {
    private Scanner scriptReader;

    /**
     * Constructor, open script file
     * @param path Path to script file
     */
    public ScriptManager(String path){
        try {
            scriptReader = new Scanner(new FileReader(path));
        } catch (FileNotFoundException e) {
            PrintErr("Фаил не найден:" + path);
        }
    }

    /**
     * Read next line from script
     * @return Line in String
     */
    public String nextLine(){
        try {
            return scriptReader.nextLine().trim();
        } catch (NullPointerException e){
            return "exit";
        } catch (NoSuchElementException e){
            return "exit";
        }
    }

    public Scanner getScriptReader() {
        return scriptReader;
    }
}
