package msplab4.commands;

import msplab4.Manager;

public abstract class BaseCommand {
    protected final String name;
    protected final String doc;

    public BaseCommand(String name, String doc) {
        this.name = name;
        this.doc = doc;
    }
    public abstract void execute(String args, Manager manager);
    public String getName_Help() {
        return name + " " + doc;
    }
    public String getName() {
        return name;
    }

}
