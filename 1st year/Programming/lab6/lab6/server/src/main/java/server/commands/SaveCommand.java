package server.commands;

import server.Main;
import server.collection.CollectionManager;
import messages.AnswerMsg;

public class SaveCommand implements Commandable{
    CollectionManager collectionManager;

    @Override
    public boolean execute(String arg, Object obArg, AnswerMsg ans) {
        Main.logger.info("Пытаюсь сохранить коллецию");
        collectionManager.SaveCollection();
        ans.AddAnswer("Сохранено");
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
