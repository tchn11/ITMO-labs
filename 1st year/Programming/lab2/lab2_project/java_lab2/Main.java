import ru.ifmo.se.pokemon.*;

public class Main {

    public static void main(String[] args) {
        Battle field = new Battle();
        field.addAlly(new Yveltal("A", 10));
        field.addAlly(new Panpour("B", 10));
        field.addAlly(new Simipour("C", 10));
        field.addFoe(new Ralts("D", 10));
        field.addFoe(new Kirlia("E", 10));
        field.addFoe(new Gardevoir("F", 10));
        field.go();
    }
}
