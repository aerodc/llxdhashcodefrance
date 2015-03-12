package llxd;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;

public class ServerDistributer {
	
	class IdRatio
	{
		public Integer id;
		public Double ratio;
	}
	
	public void sortServer(List<Server> servers)
	{
		Map<Integer, Server> serverById = new HashMap<Integer, Server>();
		
		List<IdRatio> ratios = new ArrayList<IdRatio>();
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
	}
	
	public boolean putServer(Server server, int[][] dc, int lastUsedRow)
	{
		boolean succeed = false;
		for (int r = lastUsedRow + 1; r < 16; ++r)
		{
			
		}
		return succeed;
	}
}
