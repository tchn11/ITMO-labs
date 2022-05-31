package msplab4.commands;

import msplab4.Manager;
import msplab4.Point;

public class AddCommand extends BaseCommand {
    public AddCommand() {
        super("add", "add point");
    }

    @Override
    public void execute(String args, Manager manager) {
        String[] coord = args.trim().split(" ");
        if (coord.length < 3) { System.out.println("Add coordinates!"); return; }
        try { float x = Float.parseFloat(coord[0]);
            float y = Float.parseFloat(coord[1]);
            float r = Float.parseFloat(coord[2]);
            Point point = manager.getValidPoint().checkPoint(x, y, r);
            manager.getMidIntervalMBean().addPoint();
            manager.getMidIntervalMBean().getMidInterval();
            manager.getPointsCounterMBean().addShot(point.status());
            System.out.println(point);
        } catch (NumberFormatException e) {
            System.out.println("Coordinates must be numbers");
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}
