package Classifier;
import java.util.Arrays;

public class FloydWarshall {

    public static int[][] floydWarshall(int[][] current, boolean sym) {
    	int[][] graph = new int[current.length][current.length];
    	for (int i = 0; i < current.length; i++){
    		graph[i][i] = 0;
    		for (int j = i+1; j < current.length; j++) {
    			if(sym){
	    			graph[i][j] = current[i][j];
	    			graph[j][i] = current[i][j];
    			}
    			if(graph[i][j] == 0)
    				graph[i][j] = 10000000;
    		}
    	}
    	
        int[][] distances;
        int n = graph.length;
        distances = Arrays.copyOf(graph, n);

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
                }
            }

        }

        return distances;
    }
    
}

