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
	
	public static final int ROW = 16;
	public static final int SLOT = 100;
	public static final int POOLNUMBER = 45;
	public static final int SERVERNUMBER = 625;
	
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
		// Start from here
		// step 1 read from file
		DataCenter dc = new DataCenter(16, 100);
		ReadData(dc);
		
		// step 2 put server in slots by orders of idRatio... 具体忘了
		ServerDistributer sd = new ServerDistributer();
		sd.work(dc.getServerList(), dc.dc);

		// step 3 attribute pool Id for servers
		// 轮流从dc首尾选取server，如果当前pool的sum超过平均值则进行下一个pool
		int[] poolSums = new int[POOLNUMBER];
		int averagePoolCapacity = 455;
		int sum =0;
		int m=0,n=SERVERNUMBER-1;
		int poolNumber=0;
		boolean front = true;
		boolean allPoolsReachedAverage = false;
		boolean firstComplement = true;
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
				if (allPoolsReachedAverage)
				{
					// attribute extra server to pool 33 and 39
					if (firstComplement)
					{
						s.SetPoolId(39);
						poolSums[39] += s.capacity;
						System.out.println("PoolId: " + 39 + " sum : " + poolSums[39]);
						firstComplement = false;
					}
					else
					{
						s.SetPoolId(33);
						poolSums[33] += s.capacity;
						System.out.println("PoolId: " + 33 + " sum : " + poolSums[33]);
					}
				}
				else
				{
					s.SetPoolId(poolNumber);
					sum+= s.capacity;
					if (sum > averagePoolCapacity) {
						System.out.println("PoolId: " + poolNumber + " sum : " + sum);
						poolSums[poolNumber] = sum;
						if (poolNumber < POOLNUMBER-1) {
							sum = 0;
							poolNumber++;
						}
						else
						{
							allPoolsReachedAverage = true;
						}
					}	
				}
			}
		}
		
		dc.printDc3();
		
		// step 4 write output into dc.out
		printOut(dc.getServerList());
		
		// try to calculate score
		Server[][] dcServers = new Server[ROW][SLOT+1];
		int[] serverNumbers = new int[ROW];
		for (int serverId = 0; serverId < SERVERNUMBER; serverId++)
		{
			Server s = dc.getServerList().get(serverId);
			if (s.row != -1)
			{
				dcServers[s.row][serverNumbers[s.row]]=s;
				serverNumbers[s.row]++;				
			}
		}
		
		int score = Integer.MAX_VALUE;
		int smallestRow = -1;
		for (int r=0; r < ROW; r++)
		{
			System.out.print("When pool " + r + " is down");
			int[] sums = new int[POOLNUMBER];
			for (int i=0; i<POOLNUMBER;i++) sums[i] = poolSums[i];
			int len = serverNumbers[r];
			for (int i=0;i<len;i++)
			{
				Server s = dcServers[r][i];
				if (s.GetPoolId() != -1)
					sums[s.GetPoolId()]-= s.capacity;
			}
			int poolScore = Integer.MAX_VALUE;
			int smallestPool = -1;
			for (int i=0; i<POOLNUMBER;i++)
			{
				if (sums[i] < poolScore)
				{
					poolScore = sums[i];
					smallestPool = i;
				}
			}
			System.out.println(" Smallest pool is " + smallestPool);
			if (poolScore < score)
			{
				score = poolScore;
				smallestRow = r;
			}
		}
		System.out.println("Final score is " + score);
		System.out.println("Smallest row is " + smallestRow);
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
