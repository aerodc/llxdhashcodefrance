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
		for (int r = 0; r < row; ++r)
		{
			for (int s = 0; s < slot; ++s)
			{
				dc[r][s] = ServerDistributer.EMPTY;
			}
		}
	}

	public void printDc() {
		for (int i=0; i < 16; i++) {
			for (int j=0; j<100; j++) {
				if (dc[i][j] == ServerDistributer.EMPTY)
					System.out.print("_");
				else if (dc[i][j] == ServerDistributer.UNAVAILABLE)
					System.out.print(" ");
				else 
					System.out.print("+");
			}
			System.out.println();
		}
	}
	
	public void printDc2() {
		for (int i=0; i < 16; i++) {
			for (int j=0; j<100; j++) {
				System.out.print(dc[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public void printDc3() {
		for (int i=0; i < 16; i++) {
			for (int j=0; j<100; j++) {
				if (dc[i][j] == ServerDistributer.EMPTY ) {
					System.out.format("%3c ", 'E');
				} else if ( dc[i][j] == ServerDistributer.UNAVAILABLE) {
					System.out.format("%3c ", 'U');
				} else {
					System.out.format("%3d ", serverList.get(dc[i][j]-1).GetPoolId());
				}
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
	
	public int[][] getDC()
	{
		return this.dc;
	}
}
