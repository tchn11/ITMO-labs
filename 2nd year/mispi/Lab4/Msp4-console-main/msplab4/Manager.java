package msplab4;

import msplab4.commands.*;
import msplab4.mbeans.*;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Manager {

    private final Map<String, BaseCommand> commandMap = new HashMap<>();
    private final ValidPoint validPoint = new ValidPoint();
    private final MidIntervalMBean midIntervalMBean = new MidInterval();
    private final PointsCounterMBean pointsCounterMBean = new PointsCounter();
    private void addCommands(BaseCommand... commands) {
        for (BaseCommand command : commands) {
            commandMap.put(command.getName(), command);
        }
    }
    public void manage() {
        addCommands(new ExitCommand(), new AddCommand(), new ClearCommand());
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            mbs.registerMBean(midIntervalMBean, new ObjectName("msplab4.mbeans:type=MidInterval"));
            mbs.registerMBean(pointsCounterMBean, new ObjectName("msplab4.mbeans:type=PointsCounter"));
        } catch (JMException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine();
            if (input.trim().length() == 0) continue;
            exCommands(input.trim());
        }
    }

    public void exCommands(String input) {
        String[] com = input.split(" ");
        String key = com[0];
        String args = input.substring(com[0].length());
        BaseCommand baseCommand = commandMap.get(key);
        if (baseCommand != null) baseCommand.execute(args, this);
        else System.out.println("Command is incorrect");
    }
    public ValidPoint getValidPoint() {
        return validPoint;
    }
    public MidIntervalMBean getMidIntervalMBean() {return  midIntervalMBean;}
    public PointsCounterMBean getPointsCounterMBean(){return pointsCounterMBean;}
}
