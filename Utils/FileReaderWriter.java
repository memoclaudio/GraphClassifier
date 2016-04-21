package Utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Jama.Matrix;

public class FileReaderWriter {
	
	public static ArrayList<int[][]> read(String path) throws IOException{
		ArrayList<int[][]> retMatrix = new ArrayList<int[][]>();

		File[] listOfFile = new File(path).listFiles();
		for(File file : listOfFile){
			if(file.isFile()){
				String filename = path + file.getName();
//				System.out.println(filename);
				ArrayList<ArrayList<Integer>> complete = new ArrayList<ArrayList<Integer>>();
				BufferedReader br = new BufferedReader(new FileReader(filename));
				String line = null;
				while((line = br.readLine()) != null){
					ArrayList<Integer> tmp = new ArrayList<Integer>();
					String[] field = line.split(" ");
					for(int i=0; i<field.length; i++){
						int current = Integer.parseInt(field[i]);
						tmp.add(current);
					}
					complete.add(tmp);
				}
				br.close();
								
				int[][] tmp = new int[complete.size()][complete.size()];
				for(int i=0; i<complete.size(); i++)
					for(int j=0; j<complete.size(); j++)
						tmp[i][j] = complete.get(i).get(j);
				
				retMatrix.add(tmp);
			}
		}
		
		return retMatrix;
	}
	
	public static void writeKernel(String filename, Matrix matrix) throws FileNotFoundException{
		int n = matrix.getRowDimension();
		int m = matrix.getColumnDimension();
		PrintWriter writer = new PrintWriter(filename);
		writer.println(n + " " + m);
		
		for(int i=0; i<n; i++){
			for(int j=0; j<m-1; j++)
				writer.print(matrix.get(i,j) + " ");
			writer.println(matrix.get(i,m-1));
		}
		writer.close();
	}

}
