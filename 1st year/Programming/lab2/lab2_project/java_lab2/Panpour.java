import ru.ifmo.se.pokemon.*;

public class Panpour extends Pokemon {
    public Panpour(String name, int level) {
        super(name, level);
        setStats(50, 53, 48, 53, 48, 64);
        setType(Type.WATER);
        setMove(new Facade(), new LowSweep(), new DoubleTeam());
    }
}
