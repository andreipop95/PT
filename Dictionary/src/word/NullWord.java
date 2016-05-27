package word;

public class NullWord extends AbstractWord{

	@Override
	public String getName() {
		return "The searched word isn't available in the dictionary";
	}

	@Override
	public void setName(String name) {
		this.name = "not available";
	}

	@Override
	public boolean isNull() {
		return true;
	}

}
