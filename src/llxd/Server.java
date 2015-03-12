package llxd;

public class Server {
	public int size;
	public int capacity;
	public int id;
	
	public int row;
	public int slot;
	
	private int poolId;
	
	public void SetPoolId(int _poolId)
	{
		this.poolId = _poolId;
	}
	
	public int GetPoolId() {
		return this.poolId;
	}
	
	public Server(int _size, int _capacity)
	{
		this.size= _size;
		this.capacity = _capacity;
	}
}
