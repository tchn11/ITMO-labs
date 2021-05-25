package server.collection;

import general.data.StudyGroup;
import server.Main;
import server.file.FileManager;

import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
        Main.logger.info("Добавлен элемент:");
        Main.logger.info(sg.toString());
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
        if (myCollection.empty())
            return "Колекция пуста";
        return myCollection.stream()
                .reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
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
        Stream str = myCollection.stream()
                .map(studyGroup -> studyGroup.getId().equals(sg.getId()) ? sg : studyGroup);

                str.collect(Collectors.toCollection(Stack::new));
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
        myCollection.removeIf(studyGroup -> studyGroup.getId().equals(id));
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
    public void removeByIndex(int index){
        myCollection.remove(index);

    }
    /**
     * Add if ID of element bigger then max in collection
     * @param sg Element
     */
    public void AddIfMax(StudyGroup sg) {
        if (myCollection.stream().map(studyGroup -> studyGroup.getNum()).max(Long::compareTo).get() < sg.getNum())
            myCollection.add(sg);
    }

    /**
     * Return list of element that have ExpelledStudents less then argument
     * @param max Max ExpelledStudents
     * @return List of elements
     */
    public String LessExpelled(int max){
        return myCollection.stream()
                .filter(studyGroup -> studyGroup.getExpelledStudents() < max)
                .reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2)
                .trim();
    }

    /**
     * Return list of elements Name of that starts with argument
     * @param start Start of name
     * @return List of elements
     */
    public String StartsWithName(String start){
        return myCollection.stream()
                .filter(studyGroup -> studyGroup.getName().startsWith(start))
                .reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2)
                .trim();
    }

    /**
     * Delete elements StudentsCount is equals argument
     * @param num StudentsCount that should be deleted
     */
    public void DeleteByStudentsCount(int num){
        myCollection.removeIf(studyGroup -> studyGroup.getStudentsCount() == num);
    }
}
