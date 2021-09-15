package Lab2.beans;

import java.util.ArrayList;
import java.util.List;

public class RawBean {
    private List<Raw> raws;

    public RawBean() {
        raws = new ArrayList<>();
    }

    public List<Raw> getRaws() {
        return raws;
    }
}
