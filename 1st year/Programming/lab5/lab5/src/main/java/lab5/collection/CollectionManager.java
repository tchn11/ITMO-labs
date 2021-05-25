package lab5.collection;

import lab5.data.StudyGroup;
import lab5.file.FileManager;

import java.io.BufferedInputStream;
import java.io.Console;
import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

import static lab5.console.ConsoleManager.Print;

/**
 * Operates collection.
 */
public class CollectionManager {
    private Stack<StudyGroup> myCollection;
    private FileManager fileManager;
    private java.time.LocalDateTime initDate;

    /**
     * Constructor, set start values
     * @param fl File manager
     */
    public CollectionManager(FileManager fl)
    {
        initDate = java.time.LocalDateTime.now();
        fileManager = fl;
        myCollection = fileManager.readFile();
    }

    /**
     * Save collection to file
     */
    public void SaveCollection(){
        fileManager.saveCollection(myCollection);
    }

    /**
     * Return collection
     * @return Collection
     */
    public Stack<StudyGroup> getMyCollection()
    {
        return myCollection;
    }

    /**
     * Add element to collection
     * @param sg Element of collection
     */
    public void add(StudyGroup sg){
        myCollection.add(sg);
        Print("Добавлен элемент:");
        Print(sg.toString());
    }

    /**
     * Generate next ID for new elements
     * @return ID
     */
    public int generateNextId(){
        if (myCollection.isEmpty())
            return 1;
        else
            return myCollection.lastElement().getId() + 1;
    }

    /**
     * Get all elements by index
     * @return List of elements
     */
    public String getList(){
        String list = "";
        for (StudyGroup gr : myCollection){
            list += gr.toString() + "\n";
        }
        return list;
    }

    /**
     * Get information about collection
     * @return Information
     */
    public String getInfo(){
        return "Stack из элементов StudyGroup, размер: " + Integer.toString(myCollection.size()) + ", дата иницализации: " + initDate.toString();
    }

    /**
     * Update element
     * @param sg Element
     */
    public void updateElement(StudyGroup sg){
        for (StudyGroup group : myCollection){
            if(group.getId() == sg.getId())
            {
                Collections.replaceAll(myCollection, group, sg);
                Print("Успешно заменено.");
                return;
            }
        }
    }

    /**
     * Give info about is this ID used
     * @param ID ID
     * @return is it used or not
     */
    public boolean checkID(Integer ID){
        for (StudyGroup sg : myCollection)
        {
            if (sg.getId() == ID)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Delete element by ID
     * @param id ID
     */
    public void removeID(Integer id){
        for (StudyGroup sg : myCollection){
            if (sg.getId() == id){
                myCollection.remove(sg);
                Print("Успешно удалено");
                return;
            }
        }
    }

    /**
     * Get size of collection
     * @return Size of collection
     */
    public int getSize(){
        return myCollection.size();
    }

    /**
     * Remove element by Index
     * @param index Index
     */
    public void removeByIndex(Integer index){
        int ind = 0;
        for (StudyGroup st : myCollection){
            if(ind == index){
                myCollection.remove(st);
                Print("Успешно удалено");
                return;
            }
            ind++;
        }

    }

    /**
     * Add if ID of element bigger then max in collection
     * @param sg Element
     */
    public void AddIfMax(StudyGroup sg){
        for (StudyGroup studyGroup : myCollection){
            if (sg.getId() <= studyGroup.getId()){
                Print("Нельзя добавить.");
                return;
            }
        }
        myCollection.add(sg);
        Print("Успешно добавленно");
    }

    /**
     * Return list of element that have ExpelledStudents less then argument
     * @param max Max ExpelledStudents
     * @return List of elements
     */
    public String LessExpelled(int max){
        String list = "";

        for (StudyGroup sg : myCollection){
            if (sg.getExpelledStudents() < max){
                list += sg.toString() + "\n";
            }
        }
        return list;
    }

    /**
     * Return list of elements Name of that starts with argument
     * @param start Start of name
     * @return List of elements
     */
    public String StartsWithName(String start){
        String list = "";

        for (StudyGroup sg : myCollection){
            if (sg.getName().startsWith(start.trim())){
                list += sg.toString() + "\n";
            }
        }
        return list;
    }

    /**
     * Delete elements StudentsCount is equals argument
     * @param num StudentsCount that should be deleted
     */
    public void DeleteByStudentsCount(int num){
        Stack<StudyGroup> bufferCollection = new Stack<StudyGroup>();
        for (StudyGroup sg : myCollection){
            if (sg.getStudentsCount() == num){
                bufferCollection.add(sg);
            }
        }
        if(bufferCollection.size() != 0) {
            myCollection.removeAll(bufferCollection);
            Print("Удалено: " + bufferCollection.size());
        }
    }
}
