package lab5.console;

import lab5.collection.CollectionManager;

import static lab5.console.ConsoleManager.Print;

public class ClearCommand implements Commandable{
    CollectionManager collectionManager;
    /**
     * Constructor, just set variables for work
     * @param coll Collection Manager
     */
    public ClearCommand(CollectionManager coll){
        collectionManager = coll;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return ": очистить коллекцию";
    }

    @Override
    public boolean execute(String arg) {
        collectionManager.getMyCollection().clear();
        Print("Успешно очищено");
        return true;
    }
}
