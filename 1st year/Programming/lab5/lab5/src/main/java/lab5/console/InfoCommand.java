package lab5.console;

import lab5.collection.CollectionManager;

import static lab5.console.ConsoleManager.Print;

public class InfoCommand implements Commandable{
    CollectionManager collectionManager;

    /**
     * Constructor, just set variables for work
     * @param col Collection Manager
     */
    public InfoCommand(CollectionManager col){
        collectionManager = col;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return ": вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

    @Override
    public boolean execute(String arg) {
        Print(collectionManager.getInfo());
        return true;
    }
}
