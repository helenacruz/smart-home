package tecnico.ai.smarthome.ui;

public class Request {
	private int id;
	private int category;
	private int value;
	
	public Request(int id2, int category2, int value2) {
		this.id=id2;
		this.category=category2;
		this.value=value2;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
