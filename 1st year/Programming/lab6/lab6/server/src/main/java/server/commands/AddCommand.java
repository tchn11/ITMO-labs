package server.commands;

import general.data.RowStudyGroup;
import general.data.StudyGroup;
import messages.AnswerMsg;
import server.Main;
import server.collection.CollectionManager;

import java.util.Date;

/**
 * Command 'add'. Adds new element to collection
 */
public class AddCommand implements Commandable{
    CollectionManager collectionManager;

    /**
     * Constructor, just set variables for work
     * @param col Collection Manager
     */
    public AddCommand(CollectionManager col){
        collectionManager = col;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return " {element} : добавить новый элемент в коллекцию";
    }

    @Override
    public boolean execute(String arg, Object obArg, AnswerMsg ans) {
        RowStudyGroup sg = (RowStudyGroup) obArg;
        StudyGroup studyGroup = new StudyGroup(collectionManager.generateNextId(), sg);
        collectionManager.add(studyGroup);
        Main.logger.info("Добавлен новый элемент в коллекцию: " + studyGroup.toString());
        ans.AddAnswer("Успешно добавлен элемент в коллекцию:\n" + studyGroup.toString());
        return true;
    }
}
