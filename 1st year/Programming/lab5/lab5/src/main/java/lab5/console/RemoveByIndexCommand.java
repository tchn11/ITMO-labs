package lab5.console;

import lab5.collection.CollectionManager;
import lab5.exeptions.EmptyIOException;

import static lab5.console.ConsoleManager.Print;
import static lab5.console.ConsoleManager.PrintErr;

public class RemoveByIndexCommand implements Commandable{
    CollectionManager collectionManager;

    /**
     * Constructor, just set variables for work
     * @param col Collection Manager
     */
    public RemoveByIndexCommand(CollectionManager col){
        collectionManager = col;
    }

    @Override
    public String getDescription() {
        return " index : удалить элемент, находящийся в заданной позиции коллекции (index)";
    }

    @Override
    public String getName() {
        return "remove_at";
    }

    @Override
    public boolean execute(String arg) {
        int index;
        try{
            if (arg.trim().equals(""))
                throw new EmptyIOException();
            index = Integer.parseInt(arg.trim());
        }
        catch (NumberFormatException e){
            PrintErr("индекс должен быть числом");
            return true;
        }
        catch (EmptyIOException e)
        {
            PrintErr("Должен присутствовать аргумент Index");
            return true;
        }
        if (collectionManager.getSize() <= index || index < 0) {
            Print("Нет такого индекса");
            return true;
        }
        collectionManager.removeByIndex(index);
        return true;
    }
}
