package lab5.console;

import lab5.collection.CollectionManager;
import lab5.exeptions.EmptyIOException;

import static lab5.console.ConsoleManager.PrintErr;

public class RemoveAllByStudentsCountCommand implements Commandable{
    CollectionManager collectionManager;
    /**
     * Constructor, just set variables for work
     * @param col Collection Manager
     */
    public RemoveAllByStudentsCountCommand(CollectionManager col){
        collectionManager = col;
    }

    @Override
    public boolean execute(String arg) {
        int num;
        try{
            if (arg.trim().equals(""))
                throw new EmptyIOException();
            num = Integer.parseInt(arg.trim());
        }
        catch (NumberFormatException e){
            PrintErr("studentsCount должен быть числом");
            return true;
        }
        catch (EmptyIOException e)
        {
            PrintErr("Должен присутствовать аргумент studentsCount");
            return true;
        }
        collectionManager.DeleteByStudentsCount(num);
        return true;
    }

    @Override
    public String getName() {
        return "remove_all_by_students_count";
    }

    @Override
    public String getDescription() {
        return " studentsCount : удалить из коллекции все элементы, значение поля studentsCount которого эквивалентно заданному";
    }
}
