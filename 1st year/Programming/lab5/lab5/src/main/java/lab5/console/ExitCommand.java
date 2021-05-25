package lab5.console;

public class ExitCommand implements Commandable{

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return " : завершить программу (без сохранения в файл)";
    }

    @Override
    public boolean execute(String arg) {
        return false;
    }
}
