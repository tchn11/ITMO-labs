package lab5.console;

import lab5.collection.CollectionManager;
import lab5.data.StudyGroup;

import static lab5.console.ConsoleManager.Print;

public class AddIfMaxCommand implements Commandable{
    CollectionManager collectionManager;
    ConsoleManager consoleManager;
    /**
     * Constructor, just set variables for work
     * @param coll Collection Manager
     * @param cons Console Manager
     */
    public AddIfMaxCommand(CollectionManager coll, ConsoleManager cons){
        collectionManager = coll;
        consoleManager = cons;
    }

    @Override
    public boolean execute(String arg) {
        int Id = consoleManager.parseInteger("Введите ID.");
        if (collectionManager.checkID(Id))
        {
            Print("Такой ID уже есть");
            return true;
        }
        StudyGroup sg = consoleManager.askGroup(Id);
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
