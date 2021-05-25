package server.commands;

import server.Main;
import server.collection.CollectionManager;
import general.exeptions.EmptyIOException;
import messages.AnswerMsg;

public class RemoveByIdCommand implements Commandable{
    CollectionManager collectionManager;
    /**
     * Constructor, just set variables for work
     * @param col Collection Manager
     */
    public RemoveByIdCommand(CollectionManager col){
        collectionManager = col;
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return " id : удалить элемент из коллекции по его id";
    }

    @Override
    public boolean execute(String arg, Object obArg, AnswerMsg ans) {
        int id;
        try{
            if (arg.trim().equals(""))
                throw new EmptyIOException();
            id = Integer.parseInt(arg.trim());
        }
        catch (NumberFormatException e){
            ans.AddErrorMsg("ID должен быть числом");
            Main.logger.error("ID должен быть числом");
            return false;
        }
        catch (EmptyIOException e)
        {
            ans.AddErrorMsg("Должен присутствовать аргумент ID");
            Main.logger.error("Должен присутствовать аргумент ID");
            return false;
        }
        if (!collectionManager.checkID(id)) {
            ans.AddAnswer("Нет такого ID");
            Main.logger.info("Нет такого ID");
            return true;
        }
        collectionManager.removeID(id);
        Main.logger.info("Успешно удален элемент с ID: " + Integer.toString(id));
        ans.AddAnswer("Успешно удалено");
        return true;
    }
}