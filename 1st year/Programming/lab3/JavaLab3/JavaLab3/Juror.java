public class Juror extends Human
{
	public Juror(String nm, IWriter wrt)
	{
		Name = nm;
		Writer = wrt;
	}

	@Override
	public void WriteAction(IAction act)
	{
		Actions action = act.GetAction();
		String str = act.DoAction();
		switch (action)
		{
			case SEE:
				Writer.Write(Name + " Увидели " + str);
				break;
			case DO:
				Writer.Write(Name + " " + str);
				break;
			case SAY:
				Writer.Write(Name + " Сказали " + str);
				break;
			case THINK:
				Writer.Write(Name + " Подумали " + str);
				break;
		}
	}
}
