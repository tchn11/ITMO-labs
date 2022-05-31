package msplab4.commands;

import msplab4.Manager;

public class ClearCommand extends BaseCommand{
    public ClearCommand() {
        super("clear", "clear");
    }

    @java.lang.Override
    public void execute(java.lang.String args, Manager manager) {
        manager.getPointsCounterMBean().clear();
    }
}
