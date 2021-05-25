package server.commands;

import server.Main;
import server.collection.CollectionManager;
import general.exeptions.EmptyIOException;
import messages.AnswerMsg;

public class FilterLessThenExpelledCommand implements Commandable{
    CollectionManager collectionManager;
    /**
     * Constructor, just set variables for work
     * @param coll Collection Manager
     */
    public FilterLessThenExpelledCommand(CollectionManager coll)
    {
        collectionManager = coll;
    }

    @Override
    public boolean execute(String arg, Object obArg, AnswerMsg ans) {
        int max;
        try{
            if (arg.trim().equals(""))
                throw new EmptyIOException();
            max = Integer.parseInt(arg.trim());
        }
        catch (NumberFormatException e){
            ans.AddErrorMsg("expelledStudents должен быть числом");
            Main.logger.error("expelledStudents должен быть числом");
            return false;
        }
        catch (EmptyIOException e)
        {
            ans.AddErrorMsg("Должен присутствовать аргумент expelledStudents");
            Main.logger.error("Должен присутствовать аргумент expelledStudents");
            return false;
        }
        String list = collectionManager.LessExpelled(max);
        if(list.equals("")){
            ans.AddAnswer("Нет таких элементов.");
        }
        else {
            ans.AddAnswer(list);
        }
        Main.logger.info("Пользователь получил список элементов");
        return true;
    }

    @Override
    public String getName() {
        return "filter_less_than_expelled_students";
    }

    @Override
    public String getDescription() {
        return " expelledStudents : вывести элементы, значение поля expelledStudents которых меньше заданного";
    }
}
