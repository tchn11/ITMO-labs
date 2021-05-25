package server.file;


import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import general.data.StudyGroup;
import server.Main;

import java.io.*;
import java.lang.reflect.Type;
import java.util.NoSuchElementException;
import java.util.Stack;

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
        Main.logger.info("Открываю фаил " + path);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            Stack<StudyGroup> bufferCollection;
            Type collectionType = new TypeToken<Stack<StudyGroup>>(){}.getType();
            String json = reader.readLine();
            if (json == null || json.equals("")){
                return new Stack<StudyGroup>();
            }
            bufferCollection = gson.fromJson(json.trim(), collectionType);
            Main.logger.info("Коллекция успешно загружна");
            return bufferCollection;
        } catch (FileNotFoundException e) {
            Main.logger.error("Фаил не найден");
        } catch (NoSuchElementException e){
            Main.logger.error("Загрузочный фаил пуст");
        } catch (JsonParseException e){
            Main.logger.error("В файле другая коллекция");
        } catch (IOException e) {
            Main.logger.error("Ошибка доступа к файлу");
        }

        return new Stack<StudyGroup>();
    }

    /**
     * Save collection to a file
     * @param st Collection
     */
    public void saveCollection(Stack<StudyGroup> st){
        if (path == null){
            Main.logger.error("Нельзя сохранять");
            return;
        }
        Gson gson = new Gson();
        try{
            FileWriter fileWriter = new FileWriter(new File(path));
            String json = gson.toJson(st);
            fileWriter.write(json);
            Main.logger.info("Коллекция успешно сохранена");
            fileWriter.close();
        } catch (IOException e) {
            Main.logger.info("Фаил не найден");
        }
    }
}
