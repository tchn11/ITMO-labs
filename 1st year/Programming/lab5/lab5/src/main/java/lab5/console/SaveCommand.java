package lab5.console;

import lab5.collection.CollectionManager;

public class SaveCommand implements Commandable{
    CollectionManager collectionManager;

    @Override
    public boolean execute(String arg) {
        collectionManager.SaveCollection();
        return true;
    }
    /**
     * Constructor, just set variables for work
     * @param col Collection Manager
     */
    public SaveCommand(CollectionManager col){
        collectionManager = col;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return " : сохранить коллекцию в файл";
    }
}
