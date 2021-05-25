package server.commands;

import general.data.RowStudyGroup;
import server.Main;
import server.collection.CollectionManager;
import general.data.StudyGroup;
import general.exeptions.EmptyIOException;
import messages.AnswerMsg;

public class UpdateIdCommand implements Commandable{
    CollectionManager collectionManager;
    /**
     * Constructor, just set variables for work
     * @param col Collection Manager
     */
    public UpdateIdCommand(CollectionManager col){
        collectionManager = col;
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
    public boolean execute(String arg, Object obArg, AnswerMsg ans) {

        Integer id;
        try{
            if (arg.trim().equals(""))
                throw new EmptyIOException();
            id = Integer.parseInt(arg.trim());
        }
        catch (NumberFormatException e){
            ans.AddErrorMsg("ID должен быть числом");
            Main.logger.error("Ошибка ввода: ID должен быть числом");
            return false;
        }
        catch (EmptyIOException e)
        {
            ans.AddErrorMsg("Должен присутствовать аргумент ID");
            Main.logger.error("Ошибка ввода: Должен присутствовать аргумент ID");
            return false;
        }
        if (!collectionManager.checkID(id)) {
            ans.AddErrorMsg("Нет такого ID");
            Main.logger.error("Ошибка ввода: Нет такого ID");
            return false;
        }
        RowStudyGroup sg = (RowStudyGroup) obArg;
        collectionManager.updateElement(new StudyGroup(id, sg));
        Main.logger.info("Успешно изменен элемент коллекции");
        ans.AddAnswer("Успешно изменено");
        return true;
    }
}
