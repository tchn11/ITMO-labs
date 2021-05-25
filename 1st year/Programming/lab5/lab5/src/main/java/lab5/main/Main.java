package lab5.main;

import lab5.collection.CollectionManager;
import lab5.console.*;
import lab5.file.FileManager;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import static lab5.console.ConsoleManager.Print;
/**
 * Main application class. Runs the program.
 * @author Konanykhina Antonina
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        System.setOut(new PrintStream(System.out, true, "windows-1251"));
        String path = System.getenv().get("WAY_OF_THE_NINJA");
        if (path == null){
            Print("Нет переменной с загрузочным файлом");
        }
        FileManager fl = new FileManager(path);
        CollectionManager coll = new CollectionManager(fl);
        Scanner scanner = new Scanner(System.in);
        ConsoleManager cons = new ConsoleManager(scanner);
        CommandManager com  = new CommandManager(cons, coll, scanner, new Commandable[]{
                new ClearCommand(coll), new AddCommand(coll, cons), new ShowCommand(coll),
                new InfoCommand(coll), new UpdateIdCommand(coll, cons), new RemoveByIdCommand(coll),
                new ExitCommand(), new RemoveByIndexCommand(coll), new AddIfMaxCommand(coll, cons),
                new RemoveAllByStudentsCountCommand(coll), new FilterStartsWithNameCommand(coll),
                new FilterLessThenExpelledCommand(coll), new SaveCommand(coll)});
        com.consoleMode();

    }

    public void func(boolean a1, boolean a2, boolean a3, boolean a4){

    }
}
