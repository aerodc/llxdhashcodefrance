package llxd;

public class Server {
	public int size;
	public int capacity;
	public int id;
	
	public int row;
	public int slot;
	
	private int poolId;
	
	public Server()
	{
		this.row = -1;
		this.slot = -1;
		this.poolId = -1;
	}
	
	public void SetPoolId(int _poolId)
	{
		this.poolId = _poolId;
	}
	
	public int GetPoolId() {
		return this.poolId;
	}
	
	public Server(int _id, int _size, int _capacity)
	{
		this.row = -1;
		this.slot = -1;
		this.poolId = -1;
		this.id = _id;
		this.size= _size;
		this.capacity = _capacity;
	}
}
