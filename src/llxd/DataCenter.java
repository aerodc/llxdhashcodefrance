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

	public void printDc() {
		for (int i=0; i < 16; i++) {
			for (int j=0; j<100; j++) {
				if (dc[i][j] == 0)
					System.out.print("_");
				else
					System.out.print("x");
			}
			System.out.println();
		}
	}
	
	public void printServers() {
		for (Server s : this.serverList) {
			System.out.print(s.capacity + " ");
			for (int i=0;i<s.size;i++) {
				System.out.print("_");
			}
			System.out.println();
		}
	}
}
