/**
 * Created with IntelliJ IDEA.
 * User: nazar
 * Date: 11/2/15
 * Time: 10:41 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.*;
import java.io.*;

public class BellmanFord {

    public BellmanFord() throws FileNotFoundException {

        try {

            Scanner iFile = new Scanner(new FileReader("pathInformation.txt"));

            LineNumberReader lnr = new LineNumberReader(new FileReader(new File("pathInformation.txt")));
            lnr.skip(Long.MAX_VALUE);
            int M = lnr.getLineNumber() + 1;
            int N = 4;

            lnr.close();

            int[] d = new int[N];
            Edge[] edges = new Edge[M];

            // initialization
            for (int i = 1; i < N; i++) {
                d[i] = Integer.MAX_VALUE;
            }

            // read all edges
            for (int i = 0; i < M; i++) {
                edges[i] = new Edge(iFile.nextInt(), iFile.nextInt(), iFile.nextInt());
            }

            // Calculation
            for (int i = 0; i < N - 1; i++) {
                for (int j = 0; j < M; j++) {
                    Edge e = edges[j];
                    if (d[e.d] > d[e.s] + e.w) {
                        d[e.d] = d[e.s] + e.w;
                    }
                }
            }

            boolean flag = false;

            System.out.println();
            System.out.println("Destination    Link-Cost (Using BellmanFord Algorithm)");
            System.out.println("From Router-0");
            System.out.println("-------------------------------");
            for (int i = 0; !flag && i < N; i++) {
                System.out.println(i+"                  "+d[i]);
            }

            System.out.println();
            iFile.close();

        } catch (IOException ex) {
            System.out.println("File IOE while implementing BellmanFord");
        }
    }

}

class Edge {

    public int s, d, w;

    public Edge(int s, int d, int w) {
        this.s = s;
        this.d = d;
        this.w = w;
    }

}

