public class ThinkJuriAction extends Action
{

	@Override
	public Actions GetAction()
	{
		return Actions.THINK;
	}
	
	public String DoAction()
	{
		return "Это скамья присяжных. В скамейке то все и дело, кто на нее присел, тот и присяжный";
	}
}
