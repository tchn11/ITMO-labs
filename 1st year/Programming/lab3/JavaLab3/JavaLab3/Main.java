public class Main
{
    public static void main(String[] args)
    {
        Narrator nar = new Narrator();
        Girl Alice = new Girl("Алиса", nar);
        Juror Jurors = new Juror("Присяжные", nar);

        for (int i = 0; i < 3; i++)
            Alice.WriteAction(new RepeatWordAction());
        Alice.WriteAction(new ThinkGirlsAction());
        Alice.WriteAction(new SeeAction());
        Alice.WriteAction(new ThinkJuriAction());
        Jurors.WriteAction(new SitAction());
        Jurors.WriteAction(new WorkWithBoardAction());
        //System.out.println(Jurors.hashCode());
    }
}
