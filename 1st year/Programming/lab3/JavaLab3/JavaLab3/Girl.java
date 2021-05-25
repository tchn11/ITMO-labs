public class Girl extends Human
{
	public Girl(String nm, IWriter wrt)
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
				Writer.Write(Name + " Увидела " + str);
				break;
			case DO:
				Writer.Write(Name + " " + str);
				break;
			case SAY:
				Writer.Write(Name + " Сказала " + str);
				break;
			case THINK:
				Writer.Write(Name + " Подумала " + str);
				break;
		}
	}
}
