public class Narrator implements IWriter
{
	@Override
	public void Write(String str)
	{
		System.out.println(str);
	}

	public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		return true;
	}

	public int hashCode() {
		return super.hashCode();
	}

	public String toString() {
		return "Рассказчик";
	}

}
