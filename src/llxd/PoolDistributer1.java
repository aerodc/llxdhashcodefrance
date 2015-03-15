package llxd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PoolDistributer1 {

	class Point {
		public int r;
		public int s;
	}

	class LastUsedCoordinate {
		public Point head;
		public Point tail;
	}

	public static final int CAPACITY1 = 500;
	public static final int ROW_COUNT = 16;
	public static final int SLOT_COUNT = 100;
	public static final int POOL_COUNT = 45;
	private static final int INDEX_POINT = 0;
	private static final int INDEX_CAPACITY = 1;

	private Object[] createTuple() {
		return new Object[2];
	}

	public Object[] tryBig(Point head, int[][] dc, Set<Integer> usedServer,
			int capacity, Map<Integer, Server> serverById, int pool) {
		Object[] t = createTuple();
		t[INDEX_POINT] = head;
		boolean ok = false;
		for (int s = head.s; s < SLOT_COUNT; ++s) {
			for (int r = 0; r < ROW_COUNT; ++r) {
				Integer serverId = dc[r][s];
				if (!usedServer.contains(serverId) && serverId >= 0) {
					usedServer.add(serverId);
					ok = true;
					Point newHead = new Point();
					newHead.r = r;
					newHead.s = s;
					Server server = serverById.get(serverId);
					t[INDEX_POINT] = newHead;
					t[INDEX_CAPACITY] = new Integer(capacity + server.capacity);
					server.SetPoolId(pool);
					return t;
				}
			}
		}
		return null;
	}

	public Object[] trySmall(Point head, int[][] dc, Set<Integer> usedServer,
			int capacity, Map<Integer, Server> serverById, int pool) {
		Object[] t = createTuple();
		boolean ok = false;
		t[INDEX_POINT] = head;
		for (int s = head.s; s < SLOT_COUNT; ++s) {
			for (int r = ROW_COUNT - 1; r >= 0; --r) {
				Integer serverId = dc[r][s];
				if (!usedServer.contains(serverId) && serverId >= 0) {
					usedServer.add(serverId);
					ok = true;
					Point newHead = new Point();
					newHead.r = r;
					newHead.s = s;
					Server server = serverById.get(serverId);
					t[INDEX_POINT] = newHead;
					t[INDEX_CAPACITY] = new Integer(capacity + server.capacity);
					server.SetPoolId(pool);
					return t;
				}
			}
		}
		return null;
	}

	public LastUsedCoordinate tryOnePool(Set<Integer> usedServer, int[][] dc,
			LastUsedCoordinate luc, Map<Integer, Server> serverById, int pool) {
		LastUsedCoordinate newLuc = new LastUsedCoordinate();
		newLuc.head = luc.head;
		newLuc.tail = luc.tail;
		int capacity = 0;
		while (capacity < CAPACITY1) {
			boolean tryBigFailed = false;
			// head, capacity = tryBig(head, dc, usedServer, capacity);
			Object[] tBig = tryBig(newLuc.head, dc, usedServer, capacity,
					serverById, pool);
			if (null != tBig) {
				Integer newCapacity = (Integer) tBig[INDEX_CAPACITY];
				Point newHead = (Point) tBig[INDEX_POINT];
				newLuc.head = newHead;
				capacity = capacity + newCapacity;
			} else {
				tryBigFailed = true;
			}
			boolean trySmallFailed = false;
			Object[] tSmall = trySmall(newLuc.tail, dc, usedServer, capacity,
					serverById, pool);
			if (null != tSmall) {
				Integer newCapacity = (Integer) tSmall[INDEX_CAPACITY];
				Point newTail = (Point) tSmall[INDEX_POINT];
				newLuc.tail = newTail;
				capacity = capacity + newCapacity;
			} else {
				trySmallFailed = true;
			}

			if (tryBigFailed && trySmallFailed) {
				break;
			}
		}

		return newLuc;
	}

	public void work(DataCenter dataCenter) {
		List<Server> servers = dataCenter.getServerList();
		int[][] dc = dataCenter.getDC();
		Set<Integer> usedServer = new HashSet<Integer>();
		LastUsedCoordinate luc = new LastUsedCoordinate();
		luc.head = new Point();
		luc.head.r = 0;
		luc.head.s = 0;

		luc.tail = new Point();
		luc.head.r = ROW_COUNT - 1;
		luc.head.s = SLOT_COUNT - 1;
		Map<Integer, Server> serverById = new HashMap<Integer, Server>();

		for (Server s : servers) {
			serverById.put(s.id, s);
		}

		for (int i = 0; i < POOL_COUNT; ++i)
		{
			luc = tryOnePool(usedServer, dc, luc, serverById, i);
		}
	}
}
