public class RepeatWordAction extends Action
{

	@Override
	public Actions GetAction()
	{
		return Actions.SAY;
	}
	
	public String DoAction()
	{
		return "Не без гордости слово Присяжные";
	}
}
