package lab5.console;

import lab5.collection.CollectionManager;
import lab5.exeptions.EmptyIOException;

import static lab5.console.ConsoleManager.Print;
import static lab5.console.ConsoleManager.PrintErr;


public class FilterLessThenExpelledCommand implements Commandable{
    CollectionManager collectionManager;
    /**
     * Constructor, just set variables for work
     * @param coll Collection Manager
     */
    public FilterLessThenExpelledCommand(CollectionManager coll)
    {
        collectionManager = coll;
    }

    @Override
    public boolean execute(String arg) {
        int max;
        try{
            if (arg.trim().equals(""))
                throw new EmptyIOException();
            max = Integer.parseInt(arg.trim());
        }
        catch (NumberFormatException e){
            PrintErr("expelledStudents должен быть числом");
            return true;
        }
        catch (EmptyIOException e)
        {
            PrintErr("Должен присутствовать аргумент expelledStudents");
            return true;
        }
        String list = collectionManager.LessExpelled(max);
        if(list.equals("")){
            Print("Нет таких элементов.");
        }
        else {
            Print(list);
        }
        return true;
    }

    @Override
    public String getName() {
        return "filter_less_than_expelled_students";
    }

    @Override
    public String getDescription() {
        return " expelledStudents : вывести элементы, значение поля expelledStudents которых меньше заданного";
    }
}
