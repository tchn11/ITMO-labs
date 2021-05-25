package server.commands;

import server.Main;
import server.collection.CollectionManager;
import general.exeptions.EmptyIOException;
import messages.AnswerMsg;

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
    public boolean execute(String arg, Object obArg, AnswerMsg ans) {
        int index;
        try{
            if (arg.trim().equals(""))
                throw new EmptyIOException();
            index = Integer.parseInt(arg.trim());
        }
        catch (NumberFormatException e){
            ans.AddErrorMsg("индекс должен быть числом");
            Main.logger.error("индекс должен быть числом");
            return false;
        }
        catch (EmptyIOException e)
        {
            ans.AddErrorMsg("Должен присутствовать аргумент Index");
            Main.logger.error("Должен присутствовать аргумент Index");
            return false;
        }
        if (collectionManager.getSize() <= index || index < 0) {
            ans.AddAnswer("Нет такого индекса");
            Main.logger.info("Нет такого индекса");
            return true;
        }
        collectionManager.removeByIndex(index);
        Main.logger.info("Успешно удален элемент c индексом " + Integer.toString(index));
        ans.AddAnswer("Успешно удалено");
        return true;
    }
}
