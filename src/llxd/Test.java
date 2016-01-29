package llxd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		TestSort();
	}
	
	public static void printOut(ArrayList<Server> servers) {
		PrintWriter writer;
		try {
			writer = new PrintWriter("dc.out", "UTF-8");
			for (int i = 0; i < servers.size(); i++) {
				Server s = servers.get(i);
				if (s.row == -1 || s.slot == -1 || s.GetPoolId() == -1) {
					writer.println("x");
				} else {
					writer.println(s.row + " " + s.slot + " " + s.GetPoolId());
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static void TestSort() {
		DataCenter dc = new DataCenter(16, 100);
		ReadData(dc);
		ServerDistributer sd = new ServerDistributer();
		sd.work(dc.getServerList(), dc.dc);

		int[] poolSums = new int[45];
		int averagePoolCapacity = 455;
		int sum =0;
		int m=0,n=624;
		int poolNumber=0;
		boolean front = true;
		while (m!=n) {
			int index=-1;
			if (front) {
				index = m;
				m++;
			} else {
				index = n;
				n--;
			}
			int sId = sd.ratios.get(index).id;
			Server s = dc.getServerList().get(sId-1);
			if (s.row != -1) {
				if (front) {
					front = false;
				} else {
					front = true;
				}
				s.SetPoolId(poolNumber);
				sum+= s.capacity;
				if (sum > averagePoolCapacity) {
					System.out.println("PoolId: " + poolNumber + " sum : " + sum);
					poolSums[poolNumber] = sum;
					if (poolNumber < 44) {
						sum = 0;
						poolNumber++;
					}
				}
			}
		}
		
		dc.printDc3();
		
		printOut(dc.getServerList());
		
		// try to calculate score
		Server[][] dcServers = new Server[16][101];
		int[] serverNumbers = new int[16];
		for (int serverId = 0; serverId < 625; serverId++)
		{
			Server s = dc.getServerList().get(serverId);
			if (s.row != -1)
			{
				dcServers[s.row][serverNumbers[s.row]]=s;
				serverNumbers[s.row]++;				
			}
		}
		
		int score = 10000000;
		for (int r=0; r < 16; r++)
		{
			int[] sums = new int[45];
			for (int i=0; i<45;i++) sums[i] = poolSums[i];
			int len = serverNumbers[r];
			for (int i=0;i<len;i++)
			{
				Server s = dcServers[r][i];
				if (s.GetPoolId() != -1)
					sums[s.GetPoolId()]-= s.capacity;
			}
			int poolScore = 100000000;
			for (int i=0; i<45;i++)
			{
				if (sums[i] < poolScore)
				{
					poolScore = sums[i];
				}
			}
			if (poolScore < score)
			{
				score = poolScore;
			}
		}
		System.out.println("Final score is " + score);
	}

	public static void ReadData(DataCenter dc) {
		BufferedReader br = null;

		try {
			String line;

			br = new BufferedReader(new FileReader("dc.in"));

			br.readLine();
			int R, S, U, P, M;
			R = 16;
			S = 100;
			U = 80;
			P = 45;
			M = 625;

			int i = 1;
			ArrayList<Server> list = new ArrayList<Server>();
			while ((line = br.readLine()) != null) {
				String strs[] = line.split(" ");
				int x, y;
				x = Integer.parseInt(strs[0]);
				y = Integer.parseInt(strs[1]);
				if (i >= 1 && i <= 80) {
					dc.dc[x][y] = ServerDistributer.UNAVAILABLE;
				} else {
					Server s = new Server(i - 80, x, y);
					list.add(s);
				}
				i++;
			}
			dc.setServerList(list);
			dc.printDc();
			System.out.println();
			dc.printServers();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
