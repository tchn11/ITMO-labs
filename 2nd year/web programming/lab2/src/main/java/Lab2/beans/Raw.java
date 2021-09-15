package Lab2.beans;

import java.io.Serializable;

public class Raw implements Serializable {
    private double x_val;
    private double y_val;
    private double r_val;
    private String current_time;
    private String execution_time;
    private boolean result;

    public Raw(double x, double y, double r, String current, String execution, boolean res){
        x_val = x;
        y_val = y;
        r_val = r;
        current_time = current;
        execution_time = execution;
        result = res;
    }

    public double getR_val() {
        return r_val;
    }

    public double getX_val() {
        return x_val;
    }

    public double getY_val() {
        return y_val;
    }

    public String getCurrent_time() {
        return current_time;
    }

    public String getExecution_time() {
        return execution_time;
    }

    public boolean isResult() {
        return result;
    }
}
