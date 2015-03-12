package llxd;

import java.util.List;
import java.util.Set;

public class PoolDistributer1 {
	
	class Point
	{
		public int r;
		public int s;
	}
	
	class LastUsedCoordinate
	{
		public Point head;
		public Point tail;
	}
	
	public static final int CAPACITY1 = 500;
	public static final int ROW_COUNT = 16;
	public static final int SLOT_COUNT = 100;
	
	private static final int INDEX_POINT = 0;
	private static final int INDEX_CAPACITY = 1;
	private Object[] createTuple()
	{
		return new Object[2];
	}
	
	public Object[] tryBig(Point head, int[][] dc, Set<Integer> usedServer, int capacity)
	{
		Object [] t = createTuple();
		boolean ok = false;
//		for (int )
		return t;
	}
	
	public LastUsedCoordinate tryOnePool(Set<Integer> usedServer, int [][] dc, LastUsedCoordinate luc)
	{
		Point head = luc.head;
		Point tail = luc.tail;
		
		int capacity = 0;
		while (capacity < CAPACITY1)
		{
			// head, capacity = tryBig(head, dc, usedServer, capacity);
		}
		
		return null;
	}
	
	public void work(DataCenter dataCenter)
	{
		List<Server> ls = dataCenter.getServerList();
		int[][] dc = dataCenter.getDC();
		
		
	}
}
