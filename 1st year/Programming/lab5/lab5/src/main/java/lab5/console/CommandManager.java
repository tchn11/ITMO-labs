package lab5.console;

import lab5.collection.CollectionManager;
import lab5.file.ScriptManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

import static lab5.console.ConsoleManager.Print;
import static lab5.console.ConsoleManager.PrintErr;

/**
 * Operates with commands. Choose what command should be called.
 */
public class CommandManager {
    private Scanner scanner;
    private Commandable[] commands;
    private CollectionManager collectionManager;
    private String History[];
    private Stack<String> openedScripts;
    private ConsoleManager consoleManager;
    /**
     * Constructor, set start values
     * @param consol Console Manager
     * @param col Collection manager
     * @param sc Scanner
     * @param comm Array of all commands (implements from Commandable)
     */
    public CommandManager(ConsoleManager consol, CollectionManager col, Scanner sc, Commandable[] comm){
        consoleManager = consol;
        scanner = sc;
        commands = comm;
        collectionManager = col;
        History = new String[7];
        for (int i = 0; i < 7; i++){
            History[i] = "";
        }
        openedScripts = new Stack<String>();
        consoleManager = consol;
    }

    /**
     * Console mode, take line from console and analyse what to do.
     */
    public void consoleMode(){
        boolean isRuning = true;
        while (isRuning) {
            String[] cmd = (scanner.nextLine().trim() + " ").split(" ",2);
            if (cmd[0].trim().equals("help")) {
                Print("help : вывести справку по доступным командам");
                Print("execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
                Print("history : вывести последние 7 команд (без их аргументов)");
                for (Commandable comman : commands) {
                    Print(comman.getName() + comman.getDescription());
                }
            }
            else if(cmd[0].trim().equals("history")){
                Print("история:");
                for (int i = 0; i<7; i++){
                    if (History[i]=="") break;
                    Print(History[i]);
                }
            }
            else if(cmd[0].trim().equals("execute_script")){
                if (!cmd[1].trim().equals("")) {
                    ScriptMode(cmd[1].trim());
                    openedScripts.clear();
                }
                else{
                    PrintErr("Введите имя файла");
                }
            }
            else if (!cmd[0].trim().equals("")){
                boolean isFindCommand = false;
                for (Commandable comman : commands) {
                    if (cmd[0].trim().equals(comman.getName())) {
                        isRuning =  comman.execute(cmd[1]);
                        isFindCommand = true;
                        break;
                    }
                }
                if (!isFindCommand){
                    Print("Нет такой команды");
                }
            }
            if(!cmd[0].trim().equals("")) {
                for (int i = 6; i > 0; i--) {
                    History[i] = History[i - 1];
                }
                History[0] = cmd[0];
            }
        }
    }

    /**
     * Script mode. Open script and do it.
     * @param file Script file
     */
    private void ScriptMode(String file) {
        ScriptManager scriptManager = new ScriptManager(file.trim());
        openedScripts.add(file.trim());
        boolean isRuning = true;
        while (isRuning) {
            String nextLine = scriptManager.nextLine();
            if (nextLine == null)
                break;
            String[] cmd = (nextLine.trim() + " ").split(" ",2);
            if (cmd[0].trim().equals("help")) {
                for (Commandable comman : commands) {
                    Print(comman.getName() + comman.getDescription());
                }
            }
            else if(cmd[0].trim().equals("history")){
                Print("история:");
                for (int i = 0; i<7; i++){
                    if (History[i]=="") break;
                    Print(History[i]);
                }
            }
            else if(cmd[0].trim().equals("execute_script")){
                if (!cmd[1].trim().equals("")) {
                    if (openedScripts.contains(cmd[1].trim())){
                        PrintErr("Попытка рекурсивно вызвать скрипт");
                    }
                    else {
                        ScriptMode(cmd[1].trim());
                    }
                }
                else{
                    PrintErr("Введите имя файла");
                }
            }
            else if (!cmd[0].trim().equals("")){
                boolean isFindCommand = false;
                for (Commandable comman : commands) {
                    if (cmd[0].trim().equals(comman.getName())) {
                        if (comman.getName().trim().equals("add")){
                            consoleManager.ChangeScanner(scriptManager.getScriptReader());
                            try{
                                isRuning =  comman.execute(cmd[1]);
                                isFindCommand = true;
                            }catch (NoSuchElementException e){
                                PrintErr("Ошибка чтения элемента из файла");
                            }
                            consoleManager.ChangeScanner(new Scanner(System.in));
                        }
                        else {
                            isRuning = comman.execute(cmd[1]);
                            isFindCommand = true;
                        }
                        break;
                    }
                }
                if (!isFindCommand){
                    Print("Нет такой команды: " + cmd[0]+ " " + cmd[1]);
                }
            }
            if(!cmd[0].trim().equals("")) {
                for (int i = 6; i > 0; i--) {
                    History[i] = History[i - 1];
                }
                History[0] = cmd[0];
            }

        }
        Print("Скрипт выполнен");
    }

}
