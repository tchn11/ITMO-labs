public class SitAction extends Action
{
    @Override
    public Actions GetAction()
    {
        return Actions.DO;
    }

    public String DoAction()
    {
        return "сидят на скамье";
    }
}
