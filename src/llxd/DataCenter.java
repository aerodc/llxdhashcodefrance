package llxd;

import java.util.Vector;

public class DataCenter {

	private Vector<Vector<Integer>> dc;
	
	public DataCenter(int row, int slot) {
		dc = new Vector<Vector<Integer>>(row);
		for (Vector<Integer> v : dc) {
			v = new Vector<Integer>(slot);
		}
	}

}
