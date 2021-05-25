package server.commands;

import server.Main;
import server.collection.CollectionManager;
import general.exeptions.EmptyIOException;
import messages.AnswerMsg;

public class FilterStartsWithNameCommand implements Commandable{
    CollectionManager collectionManager;

    /**
     * Constructor, just set variables for work
     * @param col Collection Manager
     */
    public FilterStartsWithNameCommand(CollectionManager col)
    {
        collectionManager = col;
    }

    @Override
    public boolean execute(String arg, Object obArg, AnswerMsg ans) {
        try{
            if (arg.trim().equals(""))
                throw new EmptyIOException();
        }catch (EmptyIOException e){
            ans.AddErrorMsg("Необходим аргумент name");
            Main.logger.error("Необходим аргумент name");
            return false;
        }
        String list = collectionManager.StartsWithName(arg);
        if (list.equals("")){
            ans.AddAnswer("Нет такого элемента");
        }
        else{
            ans.AddAnswer(list);
        }
        Main.logger.info("Пльзователь удалил элементы");
        return true;
    }

    @Override
    public String getName() {
        return "filter_starts_with_name";
    }

    @Override
    public String getDescription() {
        return " name : вывести элементы, значение поля name которых начинается с заданной подстроки";
    }
}
