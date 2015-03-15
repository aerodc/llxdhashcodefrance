package llxd;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;

public class ServerDistributer {
	
	static final int ROW_COUNT = 16;
	static final int SLOT_COUNT = 100;
	static final int EMPTY = -2;
	static final int UNAVAILABLE = -1;
	
	public List<IdRatio> ratios = new ArrayList<IdRatio>();
	
	public void work(List<Server> servers, int[][] dc)
	{
		Map<Integer, Server> serverById = new HashMap<Integer, Server>();
		
		for (Server s : servers)
		{
			serverById.put(s.id, s);
			IdRatio ir = new IdRatio();
			ir.id = s.id;
			ir.ratio = (double)s.capacity / (double)s.size;
			ratios.add(ir);
		}
		Comparator<IdRatio> c = new Comparator<IdRatio>() {

			@Override
			public int compare(IdRatio o1, IdRatio o2) {
				return o2.ratio.compareTo(o1.ratio);
			}
			
		}; 
		ratios.sort(c);
		
		for (IdRatio ir : ratios)
		{
			System.out.print(ir.id);
			System.out.print(" ");
			System.out.println(ir.ratio);
		}
		
		int lastUsedRow = 0;
		for (IdRatio ir : ratios)
		{
			Server s = serverById.get(ir.id);
			boolean succeed  = putServer(s, dc, lastUsedRow);
			if (succeed )
			{
				lastUsedRow = s.row;
			}
		}
	}
	
	public boolean tryOneRow(Server server, int[] currentRow, int rowNumber)
	{
		final int targetLength = server.size;
		
		for (int i = 0; i < SLOT_COUNT - targetLength; ++i)
		{
			boolean ok = true;
			for(int j = i ; j < i + targetLength; ++j)
			{
				if (EMPTY != currentRow[j])
				{
					ok = false;
				}
			}
			
			if (ok)
			{
				server.row = rowNumber;
				server.slot = i;
				for(int j = i ; j < i + targetLength; ++j)
				{
					currentRow[j] = server.id;
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean putServer(Server server, int[][] dc, int lastUsedRow)
	{
		for (int r = lastUsedRow + 1; r < ROW_COUNT; ++r)
		{
			int[] currentRow = dc[r];
			boolean ok = tryOneRow(server, currentRow, r);
			if (ok)
			{
				return true;
			}
		}
		
		for (int r = 0 ; r < lastUsedRow + 1; ++r)
		{
			int[] currentRow = dc[r];
			boolean ok = tryOneRow(server, currentRow, r);
			if (ok)
			{
				return true;
			}		
		}
		return false;
	}
}
