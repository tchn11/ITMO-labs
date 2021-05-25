package lab5.console;

import lab5.collection.CollectionManager;

import static lab5.console.ConsoleManager.Print;

public class ShowCommand implements Commandable{
    CollectionManager collectionManager;
    /**
     * Constructor, just set variables for work
     * @param col Collection Manager
     */
    public ShowCommand(CollectionManager col){
        collectionManager = col;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return " : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public boolean execute(String arg) {
        Print("Коллекция:");
        Print(collectionManager.getList());
        return true;
    }
}
