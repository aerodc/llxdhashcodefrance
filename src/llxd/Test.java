package llxd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Test {
	
	
   public static void main(String[] args) {      
		DataCenter dc = new DataCenter(16, 100);
		ReadData(dc);
   }
   
   public static void ReadData(DataCenter dc) {
	   BufferedReader br = null;
	   
		try {

			String line;

			br = new BufferedReader(new FileReader("dc.in"));

			line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] strs = line.split(" ");
				
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
   }
}
