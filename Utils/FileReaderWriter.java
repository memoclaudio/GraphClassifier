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
				System.out.println(filename);
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
								
				int[][] tmp = new int[complete.size()][complete.get(0).size()];
				for(int i=0; i<complete.size(); i++){
					for(int j=0; j<complete.get(i).size(); j++)
						tmp[i][j] = complete.get(i).get(j);
				}
				retMatrix.add(tmp);
			}
		}
		
		return retMatrix;
	}
	
	public static void writeFile(String filename, Matrix matrix) throws FileNotFoundException{
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
	
//	public static double[][] readInitMatrix(String filename) throws NumberFormatException, IOException{
//		ArrayList<ArrayList<Double> > retVal = new ArrayList<ArrayList<Double> >();
//		BufferedReader br = new BufferedReader(new FileReader(filename));
//		String line = null;
//		while((line = br.readLine()) != null){
//			ArrayList<Double> tmp = new ArrayList<Double>();
//			String[] field = line.split(" ");
//			for(int i=0; i<field.length; i++){
//				double current = Double.parseDouble(field[i]);
//				tmp.add(current);
//			}
//			retVal.add(tmp);
//		}
//		br.close();
//		double[][] finalMatrix = new double[retVal.size()][];
//		for(int i=0; i<retVal.size(); i++)
//			finalMatrix[i] = new double[retVal.get(i).size()];
//		for(int i=0; i<retVal.size(); i++)
//			for(int j=0; j<retVal.get(i).size(); j++)
//				finalMatrix[i][j] = retVal.get(i).get(j);
//		return finalMatrix;
//	}
}
