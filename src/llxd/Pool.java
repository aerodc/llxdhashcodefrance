package llxd;

public class Pool {
	private int capacity;
	private int id;
	public Pool() {
		this.id = -1;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
