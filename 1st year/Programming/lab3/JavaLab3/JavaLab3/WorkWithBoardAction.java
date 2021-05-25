public class WorkWithBoardAction extends Action
{
	@Override
	public Actions GetAction()
	{
		return Actions.DO;
	}
	
	public String DoAction()
	{
		return "Записывали на грифельные доски";
	}
}
