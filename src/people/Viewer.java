package people;

public class Viewer extends User {

	public Viewer(String name) {
		super("Viewer", "", "");
	}

	public String toString() {
		return "Name : " + "Viewer";
	}
}
