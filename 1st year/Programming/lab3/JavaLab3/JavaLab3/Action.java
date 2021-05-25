public abstract class Action implements IAction
{
    public abstract Actions GetAction();
    public abstract String DoAction();

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass())
            return false;
        Action others = (Action) obj;
        return this.DoAction()== others.DoAction() && this.GetAction() == others.GetAction();
    }

    public int hashCode() {
        return this.GetAction().hashCode() * this.DoAction().hashCode();
    }

    public String toString() {
        return this.DoAction();
    }
}
