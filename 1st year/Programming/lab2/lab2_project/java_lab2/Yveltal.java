import ru.ifmo.se.pokemon.*;

public class Yveltal extends Pokemon {
    public Yveltal(String name, int level) {
        super(name, level);
        setStats(126, 131, 95, 131, 98, 99);
        setType(Type.FLYING, Type.DARK);
        setMove(new ShadowBall(), new Facade(), new Roost(), new RockSlide());
    }
}
