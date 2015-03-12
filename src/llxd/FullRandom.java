package llxd;

import java.util.ArrayList;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

public class FullRandom {

	DataCenter dc;
	
	public FullRandom(DataCenter dc) {
		this.dc = dc;
	}
	
	public void RunRandom() {
		System.out.println("Run here");
		
		for (int i=0;i<dc.getServerList().size();i++) {
			Random r = new Random();
			int low = 0;
			int high = 45;
			int pullId = r.nextInt(high - low) + low;
			Server s = dc.getServerList().get(i);
			s.SetPoolId(pullId);
			if (s.row == -1) {
				System.out.println("x");
			} else {
				System.out.println(s.row + " " + s.slot + " " + pullId);
			}
		}
		
	}
}
