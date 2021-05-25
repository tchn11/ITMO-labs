package lab5.file;


import lab5.data.StudyGroup;

import java.io.*;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonParseException;

import static lab5.console.ConsoleManager.Print;
import static lab5.console.ConsoleManager.PrintErr;

/**
 * Operates the main collection file for saving/loading.
 */
public class FileManager {
    private String path;
    /**
     * Constructor, just save variable for all class.
     * @param pth Path to collection file.
     */
    public FileManager(String pth){
        path = pth;
    }

    /**
     * Read collection from a file
     * @return Collection
     */
    public Stack<StudyGroup> readFile()
    {
        if (path == null){
            return new Stack<StudyGroup>();
        }
        Gson gson;
        gson = new Gson();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            Stack<StudyGroup> bufferCollection;
            Type collectionType = new TypeToken<Stack<StudyGroup>>(){}.getType();
            String json = reader.readLine();
            if (json == null || json.equals("")){
                return new Stack<StudyGroup>();
            }
            bufferCollection = gson.fromJson(json.trim(), collectionType);
            Print("Коллекция успешно загружна");
            return bufferCollection;
        } catch (FileNotFoundException e) {
            PrintErr("Фаил не найден");
        } catch (NoSuchElementException e){
            PrintErr("Загрузочный фаил пуст");
        } catch (JsonParseException e){
            PrintErr("В файле другая коллекция");
        } catch (IOException e) {
            PrintErr("Ошибка доступа к файлу");
        }

        return new Stack<StudyGroup>();
    }

    /**
     * Save collection to a file
     * @param st Collection
     */
    public void saveCollection(Stack<StudyGroup> st){
        if (path == null){
            Print("Нельзя сохранять");
            return;
        }
        Gson gson = new Gson();
        try{
            FileWriter fileWriter = new FileWriter(new File(path));
            String json = gson.toJson(st);
            fileWriter.write(json);
            Print("Коллекция успешно сохранена");
            fileWriter.close();
        } catch (IOException e) {
            PrintErr("Фаил не найден");
        }
    }
}
