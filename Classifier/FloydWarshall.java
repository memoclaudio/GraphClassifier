package Classifier;
import java.util.Arrays;

public class FloydWarshall {

    // graph represented by an adjacency matrix
    private int[][] graph;

    private boolean negativeCycle;

    public FloydWarshall(int[][] current) {
    	graph = new int[current.length][current.length];
    	for (int i = 0; i < current.length; i++) {
    		graph[i][i] = 0;
    		for (int j = i+1; j < current.length; j++) {
    			graph[i][j] = current[i][j];
    			graph[j][i] = current[i][j];
    			if(graph[i][j] == 0)
    				graph[i][j] = 10000000;

    		}
    	}
    }


    public boolean hasNegativeCycle() {
        return this.negativeCycle;
    }


    // all-pairs shortest path
    public int[][] floydWarshall() {
        int[][] distances;
        int n = this.graph.length;
        distances = Arrays.copyOf(this.graph, n);

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
                }
            }

            if (distances[k][k] < 0.0) {
                this.negativeCycle = true;
            }
        }

        return distances;
    }
}

