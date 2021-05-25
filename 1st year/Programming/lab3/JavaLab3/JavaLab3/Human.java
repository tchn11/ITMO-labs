public abstract class Human
{
	protected String Name;
	protected IWriter Writer;
	public abstract void WriteAction(IAction act);

	@Override
	public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		Human others = (Human) obj;
		return this.Name == others.Name;
	}

	public int hashCode() {
		return this.Name.hashCode() * this.Writer.hashCode();
	}

	public String toString() {
		return this.Name;
	}
}
