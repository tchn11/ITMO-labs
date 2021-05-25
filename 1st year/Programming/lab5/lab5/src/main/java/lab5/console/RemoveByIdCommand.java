package lab5.console;

import lab5.collection.CollectionManager;
import lab5.exeptions.EmptyIOException;

import static lab5.console.ConsoleManager.Print;
import static lab5.console.ConsoleManager.PrintErr;

public class RemoveByIdCommand implements Commandable{
    CollectionManager collectionManager;
    /**
     * Constructor, just set variables for work
     * @param col Collection Manager
     */
    public RemoveByIdCommand(CollectionManager col){
        collectionManager = col;
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return " id : удалить элемент из коллекции по его id";
    }

    @Override
    public boolean execute(String arg) {
        int id;
        try{
            if (arg.trim().equals(""))
                throw new EmptyIOException();
            id = Integer.parseInt(arg.trim());
        }
        catch (NumberFormatException e){
            PrintErr("ID должен быть числом");
            return true;
        }
        catch (EmptyIOException e)
        {
            PrintErr("Должен присутствовать аргумент ID");
            return true;
        }
        if (!collectionManager.checkID(id)) {
            Print("Нет такого ID");
            return true;
        }
        //Print("1");
        collectionManager.removeID(id);
        //Print("2");
        return true;
    }
}