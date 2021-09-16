package com.demo.DailyTest;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
public class MinWeightCover {

    private static class HeapNode implements Comparable{
        int i,cn;
        int[] x,c;

        public int compareTo(Object o){
            HeapNode heapNode = (HeapNode) o;
            int result = Integer.compare(cn, heapNode.cn);

            return result;
        }
    }

    private static int u,v;
    private static int n,e;
    private static int[][] a;
    private static int[] p;

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        while (true){
            n = input.nextInt();
            e = input.nextInt();

            a = new int[n+1][n+1];
            p = new int[n+1];

            for(int i=0; i<=n; i++)
                for(int j=0; j<=n; j++)
                    a[i][j] = 0;

            for(int i=1; i<=n; i++)
                p[i] = input.nextInt();

            for(int i=1; i<=e; i++){
                u = input.nextInt();
                v = input.nextInt();
                a[u][v] = 1;
                a[v][u] = 1;
            }

            System.out.println(minCover(a,p,n));
            for(int i=1; i<=n; i++)
                System.out.print(p[i]+" ");
        }
    }

    private static class VC{
        int[][] a;
        int[] w,bestx;
        int n,bestn;

        private void BBVC(){
            Queue<HeapNode> H = new PriorityQueue<>(100000);
            HeapNode E = new HeapNode();
            E.x = new int[n+1];
            E.c = new int[n+1];
            for(int j=1; j<=n; j++) {E.x[j]=E.c[j]=0;}
            int i=1,cn=0;
            while (true){
                if(i > n){
                    if(cover1(E)){
                        for(int j=1; j<=n; j++) bestx[j]=E.x[j];
                        bestn = cn;
                        break;
                    }
                }else{
                    if(!cover1(E)) addLiveNode(H,E,cn,i,1);
                    addLiveNode(H,E,cn,i,0);
                }
                if(H.size() == 0) break;
                E = H.poll();
                cn = E.cn;
                i = E.i+1;
            }
        }

//        private boolean cover(HeapNode E){
//            for(int j=1; j<=n; j++)
//                if(E.x[j]==0 && E.c[j]==0) return false;
//
//            return true;
//        }

        private boolean cover1(HeapNode E){
            for(int i=1; i<=n; i++)
                for(int j=1; j<=n; j++)
                    if(a[i][j]>0 && E.x[i]==0 && E.x[j]==0)
                        return false;

            return true;
        }

        private void addLiveNode(Queue<HeapNode> H, HeapNode E, int cn, int i, int ch){
            HeapNode N = new HeapNode();
            N.x = new int[n+1];
            N.c = new int[n+1];
            for(int j=1; j<=n; j++){
                N.x[j] = E.x[j];
                N.c[j] = E.c[j];
            }
            N.x[i] = ch;
            if(ch > 0){
                N.cn = cn+w[i];
                for(int j=1; j<=n; j++)
                    if(a[i][j] > 0) N.c[j]++;
            }else N.cn=cn;
            N.i = i;
            H.add(N);
        }
    }

    private static int minCover(int[][] a, int[] v, int n){
        VC Y = new VC();
        Y.w = new int[n+1];
        for(int j=1; j<=n; j++) {Y.w[j]=v[j];}
        Y.a=a; Y.n=n; Y.bestx=v;
        Y.BBVC();

        return Y.bestn;
    }
}
