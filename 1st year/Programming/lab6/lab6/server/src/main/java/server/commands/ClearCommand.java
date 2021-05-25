package server.commands;

import server.Main;
import server.collection.CollectionManager;
import messages.AnswerMsg;

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
    public boolean execute(String arg, Object obArg, AnswerMsg ans) {
        collectionManager.getMyCollection().clear();
        ans.AddAnswer("Успешно очищено");
        Main.logger.info("Коллекция успешно очищена");
        return true;
    }
}
