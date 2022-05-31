package msplab4.commands;

import msplab4.Manager;

public class ExitCommand extends BaseCommand {
    public ExitCommand() {
        super("exit", "exit");
    }

    @Override
    public void execute(String args, Manager manager) {
        System.exit(0);
    }
}
