package Classifier;

public class FloydWarshall {

    public static int inf = 100000;
    
    public static int[][] floydWarshall(int[][] current) {
    	int[][] graph = new int[current.length][current.length];
    	for (int i = 0; i < current.length; i++) {
    		graph[i][i] = 0;
    		for (int j = i+1; j < current.length; j++) {
    			graph[i][j] = current[i][j];
    			graph[j][i] = current[i][j];
    			if(graph[i][j] == 0){
    				graph[i][j] = inf;
    				graph[j][i] = inf;
    			}
    		}
    	}
    	
        int[][] distances = new int[graph.length][graph.length];
        int n = graph.length;
        for(int i=0; i<n; i++)
        	for(int j=0; j<n; j++)
        		distances[i][j] = graph[i][j];

        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);


        return distances;
    }
}

