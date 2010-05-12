package ospf.simulate.util;

import java.util.ArrayList;

public class SpfA {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int[][] g = { { 0,  10,       MAXVALUE, 30,       100 },
				{ MAXVALUE, 0,        50,       MAXVALUE, MAXVALUE },
				{ MAXVALUE, MAXVALUE, 0,        MAXVALUE, 10 },
				{ MAXVALUE, MAXVALUE, 20,       0,        60 },
				{ MAXVALUE, MAXVALUE, MAXVALUE, MAXVALUE, 0 } };

		int startpoint = 0;
		ArrayList<Integer> testdist = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> testpaths = new ArrayList<ArrayList<Integer>>();
		dijkstra(g, startpoint, testdist, testpaths);

		System.out.println(testdist);
		System.out.println(testpaths);
		return;
		// TODO Auto-generated method stub

	}

	public static final int MAXVALUE = 2147483647;

	/**
	 * 求最短路径的算法 作者：王世泽
	 * 
	 * @param graph
	 *            邻接矩阵的二维数组
	 * @param start
	 *            开始点
	 * @param distance
	 *            到各点的最短路径
	 * @param paths
	 *            到各点的路径
	 * @return
	 */
	public static boolean dijkstra(int[][] graph, int start,
			ArrayList<Integer> distance, ArrayList<ArrayList<Integer>> paths) {
		int[] dist = new int[graph.length];
		int[] path = new int[graph.length];
		boolean[] S = new boolean[graph.length];
		int n = graph.length;
		int i, j, k, w, min;

		for (i = 0; i < n; i++) {
			dist[i] = graph[start][i];
			S[i] = false;
			if (i != start && dist[i] < MAXVALUE)
				path[i] = start;
			else
				path[i] = -1;
		}

		S[start] = true;
		dist[start] = 0;

		for (i = 0; i < n - 1; i++) {
			min = MAXVALUE;
			int u = start;
			for (j = 0; j < n; j++) {
				if (S[j] == false && dist[j] < min) {
					u = j;
					min = dist[j];
				}
			}

			S[u] = true;

			for (k = 0; k < n; k++) {
				w = graph[u][k];
				if (S[k] == false && w < MAXVALUE && dist[u] + w < dist[k]) {
					dist[k] = dist[u] + w;
					path[k] = u;
				}
			}
		}

		distance.clear();
		for (i = 0; i < n; i++)
			distance.add(dist[i]);

		paths.clear();
		for (i = 0; i < n; i++) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			j = i;
			while (j != start) {
				System.out.println("here");
				temp.add(0, j);
				j = path[j];
			}
			paths.add(temp);
		}

		return true;
	}
}
