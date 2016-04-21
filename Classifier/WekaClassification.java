package Classifier;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Jama.Matrix;
import Utils.FileReaderWriter;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.PrecomputedKernelMatrixKernel;
import weka.core.Instances;

public class WekaClassification {
	
	public static SMO generateClassifier(Instances data, String graphsDirectory, double C, double epsilon) throws Exception{

		String tmpFile = buildKernel(graphsDirectory);

		PrecomputedKernelMatrixKernel kernel = new PrecomputedKernelMatrixKernel();
		kernel.setKernelMatrixFile(new File(tmpFile));
		SMO smo = new SMO();
		smo.setKernel(kernel);
		smo.setC(C);
		smo.setEpsilon(epsilon);
		smo.buildClassifier(data);
		return smo;
	}
	
	
	public static String buildKernel(String graphsDirectory){
		String filename = "tmp.matrix";
		try {
			ArrayList<int[][]> graphs = FileReaderWriter.read(graphsDirectory);
			ArrayList<int[][]> shortMatrix = new ArrayList<int[][]>();
			int maxPath = 0;
			for(int[][] graph : graphs){
				int[][] distMatrix = FloydWarshall.floydWarshall(graph);
				shortMatrix.add(distMatrix);
				int tmpMax = findMax(distMatrix);
				if(tmpMax > maxPath){
					maxPath = tmpMax;
				}
			}
			
			Matrix sp = new Matrix((int)(maxPath+1), graphs.size());
			for(int i=0; i<maxPath+1; i++)
				for(int j=0; j<graphs.size(); j++)
					sp.set(i, j, 0);
			for(int i=0; i<shortMatrix.size(); i++){
				int[][] currentMatrix = shortMatrix.get(i);
				for(int x=0; x<currentMatrix.length; x++){
					for(int y=x+1; y<currentMatrix[x].length; y++){
						if(currentMatrix[x][y] != FloydWarshall.inf){
							sp.set(currentMatrix[x][y], i, sp.get(currentMatrix[x][y], i)+1);
						}
					}
				}
			}
			Matrix kernel = sp.transpose().times(sp);
			FileReaderWriter.writeKernel(filename, kernel);
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
				if(matrix[i][j] > max && matrix[i][j] != FloydWarshall.inf)
					max = matrix[i][j];
			}
		}
		return max;
	}

}
