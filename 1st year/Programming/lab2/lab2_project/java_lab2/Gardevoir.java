import ru.ifmo.se.pokemon.*;

public class Gardevoir extends Kirlia {
    public Gardevoir(String name, int level) {
        super(name, level);
        setStats(68, 65, 65, 125, 115, 80);
        setType(Type.PSYCHIC, Type.FAIRY);
        setMove(new Growl(), new DrainingKiss(), new Charm(), new Facade());
    }
}
