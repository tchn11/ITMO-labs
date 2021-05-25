package lab5.console;

import lab5.collection.CollectionManager;

/**
 * Command 'add'. Adds new element to collection
 */
public class AddCommand implements Commandable{
    CollectionManager collectionManager;
    ConsoleManager consoleManager;

    /**
     * Constructor, just set variables for work
     * @param col Collection Manager
     * @param cons Console Manager
     */
    public AddCommand(CollectionManager col, ConsoleManager cons){
        collectionManager = col;
        consoleManager = cons;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return " {element} : добавить новый элемент в коллекцию";
    }

    @Override
    public boolean execute(String arg) {
        collectionManager.add(consoleManager.askGroup(collectionManager.generateNextId()));
        return true;
    }
}
