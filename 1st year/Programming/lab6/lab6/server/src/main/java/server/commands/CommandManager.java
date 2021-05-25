package server.commands;

import general.data.RowStudyGroup;
import general.data.StudyGroup;
import messages.AnswerMsg;
import messages.CommandMsg;
import messages.Status;
import server.Main;
import server.collection.CollectionManager;
import server.file.ScriptManager;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Operates with commands. Choose what command should be called.
 */
public class CommandManager {
    private Commandable[] commands;
    private CollectionManager collectionManager;
    private String History[];
    private Stack<String> openedScripts;
    /**
     * Constructor, set start values
     * @param col Collection manager
     * @param comm Array of all commands (implements from Commandable)
     */
    public CommandManager(CollectionManager col, Commandable[] comm){
        commands = comm;
        collectionManager = col;
        History = new String[7];
        for (int i = 0; i < 7; i++){
            History[i] = "";
        }
        openedScripts = new Stack<String>();
    }

    /**
     * Execute command
     * @param commandMsg Command witch should be executed
     * @param ans What should return
     */
    public void executeCommand(CommandMsg commandMsg, AnswerMsg ans){
        Main.logger.info("Выполняется команда " + commandMsg.getCommandName() + " " + commandMsg.getCommandStringArgument());
        if (commandMsg.getCommandName().trim().equals("help")) {
            ans.AddAnswer("help : вывести справку по доступным командам");
            ans.AddAnswer("execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
            ans.AddAnswer("history : вывести последние 7 команд (без их аргументов)");
            ans.AddAnswer("exit : завершить программу (без сохранения в файл)");
            for (Commandable comman : commands) {
                ans.AddAnswer(comman.getName() + comman.getDescription());
            }
            ans.AddStatus(Status.FINE);
        }
        else if(commandMsg.getCommandName().trim().equals("exit")){
            ans.AddStatus(Status.EXIT);
            return;
        }
        else if(commandMsg.getCommandName().trim().equals("history")){
            ans.AddAnswer("история:");
            for (int i = 0; i<7; i++){
                if (History[i]=="") break;
                ans.AddAnswer(History[i]);
            }
            ans.AddStatus(Status.FINE);
        }
        else if(commandMsg.getCommandName().trim().equals("execute_script")){
            if (!commandMsg.getCommandStringArgument().trim().equals("")) {
                ScriptMode(commandMsg.getCommandStringArgument().trim(), ans);
                openedScripts.clear();
                ans.AddStatus(Status.FINE);
            }
            else{
                ans.AddStatus(Status.ERROR);
                Main.logger.error("Введите имя файла");
            }
        }
        else if (!commandMsg.getCommandName().trim().equals("")){
            boolean isFindCommand = false;
            for (Commandable comman : commands) {
                if (commandMsg.getCommandName().trim().equals(comman.getName())) {
                    boolean stat =  comman.execute(commandMsg.getCommandStringArgument(), commandMsg.getCommandObjectArgument(),
                            ans);
                    if (stat){
                        ans.AddStatus(Status.FINE);
                    }else{
                        ans.AddStatus(Status.ERROR);
                    }
                    isFindCommand = true;

                    break;
                }
            }
            if (!isFindCommand){
                Main.logger.error("Нет такой команды");
                ans.AddErrorMsg("Нет такой команды");
                ans.AddStatus(Status.ERROR);
            }
        }
        if(!commandMsg.getCommandName().trim().equals("")) {
            for (int i = 6; i > 0; i--) {
                History[i] = History[i - 1];
            }
            History[0] = commandMsg.getCommandName();
        }

    }

    /**
     * Script mode. Open script and do it.
     * @param file Script file
     * @param ans Witch should return to user
     */
    private void ScriptMode(String file, AnswerMsg ans) {
        ScriptManager scriptManager = new ScriptManager(file.trim());
        openedScripts.add(file.trim());
        boolean isRuning = true;
        while (isRuning) {
            String nextLine = scriptManager.nextLine();
            if (nextLine == null)
                break;
            String[] cmd = (nextLine.trim() + " ").split(" ",2);
            if (cmd[0].trim().equals("exit")){
                break;
            }
            if (cmd[0].trim().equals("help")) {
                ans.AddAnswer("help : вывести справку по доступным командам");
                ans.AddAnswer("execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
                ans.AddAnswer("history : вывести последние 7 команд (без их аргументов)");
                ans.AddAnswer("exit : завершить программу (без сохранения в файл)");
                for (Commandable comman : commands) {
                    ans.AddAnswer(comman.getName() + comman.getDescription());
                }
            }
            else if(cmd[0].trim().equals("history")){
                ans.AddAnswer("история:");
                for (int i = 0; i<7; i++){
                    if (History[i]=="") break;
                    ans.AddAnswer(History[i]);
                }
            }
            else if(cmd[0].trim().equals("execute_script")){
                if (!cmd[1].trim().equals("")) {
                    if (openedScripts.contains(cmd[1].trim())){
                        ans.AddErrorMsg("Попытка рекурсивно вызвать скрипт");
                    }
                    else {
                        ScriptMode(cmd[1].trim(), ans);
                    }
                }
                else{
                    ans.AddErrorMsg("Введите имя файла");
                }
            }
            else if (!cmd[0].trim().equals("")){
                boolean isFindCommand = false;
                for (Commandable comman : commands) {
                    if (cmd[0].trim().equals(comman.getName())) {
                        RowStudyGroup rowStudyGroup = null;
                        if (cmd[0].trim().equals("add") | cmd[0].trim().equals("update")
                                | cmd[0].trim().equals("add_if_max")){
                            isFindCommand = true;
                            rowStudyGroup = scriptManager.askGroup();
                            if (rowStudyGroup == null){
                                ans.AddErrorMsg("Ошибка парсинга объекта из файла");
                                break;
                            }
                        }
                        comman.execute(cmd[1], rowStudyGroup, ans);
                        isFindCommand = true;
                    }
                }
                if (!isFindCommand){
                    ans.AddErrorMsg("Нет такой команды: " + cmd[0]+ " " + cmd[1]);
                }
            }
            if(!cmd[0].trim().equals("")) {
                for (int i = 6; i > 0; i--) {
                    History[i] = History[i - 1];
                }
                History[0] = cmd[0];
            }

        }
        ans.AddAnswer("Скрипт выполнен");
    }

}
