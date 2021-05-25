import ru.ifmo.se.pokemon.*;

public class Simipour extends Panpour {
    public Simipour(String name, int level) {
        super(name, level);
        setStats(75, 98, 63, 98, 63, 101);
        setType(Type.WATER);
        setMove(new Facade(), new LowSweep(), new DoubleTeam(), new FocusBlast());
    }
}
