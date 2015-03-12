package llxd;

import java.util.ArrayList;

public class DataCenter {

	public int[][] dc;
	
	private ArrayList<Server> serverList;
	
	public ArrayList<Server> getServerList() {
		return serverList;
	}

	public void setServerList(ArrayList<Server> serverList) {
		this.serverList = serverList;
	}

	public DataCenter(int row, int slot) {
		dc = new int[row][slot];
	}

}
