package lab5.console;

import lab5.collection.CollectionManager;
import lab5.exeptions.EmptyIOException;

import static lab5.console.ConsoleManager.Print;
import static lab5.console.ConsoleManager.PrintErr;

public class FilterStartsWithNameCommand implements Commandable{
    CollectionManager collectionManager;

    /**
     * Constructor, just set variables for work
     * @param col Collection Manager
     */
    public FilterStartsWithNameCommand(CollectionManager col)
    {
        collectionManager = col;
    }

    @Override
    public boolean execute(String arg) {
        try{
            if (arg.trim().equals(""))
                throw new EmptyIOException();
        }catch (EmptyIOException e){
            PrintErr("Необходим аргумент name");

        }
        String list = collectionManager.StartsWithName(arg);
        if (list.equals("")){
            Print("Нет такого элемента");
        }
        else{
            Print(list);
        }
        return true;
    }

    @Override
    public String getName() {
        return "filter_starts_with_name";
    }

    @Override
    public String getDescription() {
        return " name : вывести элементы, значение поля name которых начинается с заданной подстроки";
    }
}
