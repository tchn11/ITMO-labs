package lab5.console;

import lab5.collection.CollectionManager;
import lab5.data.StudyGroup;
import lab5.exeptions.EmptyIOException;

import static lab5.console.ConsoleManager.Print;
import static lab5.console.ConsoleManager.PrintErr;

public class UpdateIdCommand implements Commandable{
    CollectionManager collectionManager;
    ConsoleManager consoleManager;
    /**
     * Constructor, just set variables for work
     * @param col Collection Manager
     * @param cons Console Manager
     */
    public UpdateIdCommand(CollectionManager col, ConsoleManager cons){
        collectionManager = col;
        consoleManager = cons;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return " id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public boolean execute(String arg) {

        Integer id;
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
        StudyGroup sg = consoleManager.askGroup(id);
        collectionManager.updateElement(sg);
        return true;
    }
}
