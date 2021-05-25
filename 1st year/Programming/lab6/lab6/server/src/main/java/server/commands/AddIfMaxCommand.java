package server.commands;

import general.data.RowStudyGroup;
import server.collection.CollectionManager;
import general.data.StudyGroup;
import messages.AnswerMsg;

public class AddIfMaxCommand implements Commandable{
    CollectionManager collectionManager;
    /**
     * Constructor, just set variables for work
     * @param coll Collection Manager
     */
    public AddIfMaxCommand(CollectionManager coll){
        collectionManager = coll;
    }

    @Override
    public boolean execute(String arg, Object obArg, AnswerMsg ans) {
        StudyGroup sg = new StudyGroup(collectionManager.generateNextId(), (RowStudyGroup) obArg);
        collectionManager.AddIfMax(sg);
        return true;
    }

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public String getDescription() {
        return " {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }
}
