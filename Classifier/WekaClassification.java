package Classifier;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Jama.Matrix;
import Utils.FileReaderWriter;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.PrecomputedKernelMatrixKernel;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaClassification {

	private SMO smo;
	
	public WekaClassification(String instancesPath, String graphsDirectory, double C, double epsilon) throws Exception{
		 DataSource source = new DataSource(instancesPath);
		 Instances data = source.getDataSet();
		 data.setClassIndex(data.numAttributes()-1);

		 String tmpFile = buildKernel(graphsDirectory);
		 
		 PrecomputedKernelMatrixKernel kernel = new PrecomputedKernelMatrixKernel();
		 kernel.setKernelMatrixFile(new File(tmpFile));
		 smo = new SMO();
		 smo.setKernel(kernel);
		 smo.setC(C);
		 smo.setEpsilon(epsilon);
		 smo.buildClassifier(data);
	}
	
	
	public static String buildKernel(String graphsDirectory){
		String filename = "tmp.matrix";
		try {
			ArrayList<int[][]> graphs = FileReaderWriter.read(graphsDirectory);
			ArrayList<int[][]> shortMatrix = new ArrayList<int[][]>();
			int maxPath = 0;
			int minPath = Integer.MAX_VALUE;
			for(int[][] graph : graphs){
				FloydWarshall floyd = new FloydWarshall(graph);
				int[][] distMatrix = floyd.floydWarshall();
				shortMatrix.add(distMatrix);
				int tmpMax = findMax(distMatrix);
				int tmpMin = findMin(distMatrix);
				if(tmpMax > maxPath)
					maxPath = tmpMax;
				if(tmpMin < minPath)
					minPath = tmpMin;
			}
			System.out.println(maxPath + " " + minPath);
			
			System.out.println(maxPath+1 + " " + graphs.size());
			
			Matrix sp = new Matrix((int)(maxPath+1), graphs.size());
			for(int i=0; i<maxPath+1; i++)
				for(int j=0; j<graphs.size(); j++)
					sp.set(i, j, 0);
			for(int i=0; i<shortMatrix.size(); i++){
				int[][] currentMatrix = shortMatrix.get(i);
				for(int x=0; x<currentMatrix.length; x++){
					for(int y=x+1; y<currentMatrix[x].length; y++){
						if(currentMatrix[x][y] != Integer.MAX_VALUE){
							sp.set(currentMatrix[x][y], i, sp.get(currentMatrix[x][y], i)+1);
						}
					}
				}
			}
			Matrix kernel = sp.transpose().times(sp);
			FileReaderWriter.writeFile(filename, kernel);
		} catch (IOException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		
		return filename;
	}
	
	private static int findMax(int[][] matrix){
		int max = 0;
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[i].length; j++){
				if(matrix[i][j] > max)
					max = matrix[i][j];
			}
		}
		return max;
	}
	
	private static int findMin(int[][] matrix){
		int min = Integer.MAX_VALUE;
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[i].length; j++){
				if(matrix[i][j] < min)
					min = matrix[i][j];
			}
		}
		return min;
	}
}
