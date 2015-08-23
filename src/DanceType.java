
public class DanceType {
	public int id;
	public String name;

	public DanceType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "<dance:" + this.id + ">";
		// return String.format("<dance:%s>", this.id);
	}
}
