import java.io.*;
import java.util.*;

public class DailyCommute {
    static int N;
    static int[] dist, S, pos;
    static int[] lo, hi, min, map;
    
    public static void pullUp(int i){
        if(lo[i] != hi[i])  min[i] = Math.min(min[2*i], min[2*i+1]);
    }
    
    public static void initialize(int i, int l, int r){
        lo[i] = l; hi[i] = r;
        if(l == r){
            min[i] = pos[l]+dist[l];
            map[l] = i;
            return;
        }
        int m = (l+r)/2;
        initialize(2*i, l, m);
        initialize(2*i+1, m+1, r);
        pullUp(i);
    }
    
    public static void update(int a, int b){
        int temp = pos[S[a]];
        pos[S[a]] = pos[S[b]];
        pos[S[b]] = temp;
        
        temp = S[a];
        S[a] = S[b];
        S[b] = temp;
        
        temp = map[S[a]];
        min[temp] = pos[S[a]]+dist[S[a]];
        while(temp > 0){
            pullUp(temp);
            temp >>= 1;
        }
        
        temp = map[S[b]];
        min[temp] = pos[S[b]]+dist[S[b]];
        while(temp > 0){
            pullUp(temp);
            temp >>= 1;
        }
        
        System.out.println(min[1]);
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int W = Integer.parseInt(st.nextToken());
        int D = Integer.parseInt(st.nextToken());
        
        ArrayList<Integer>[] G = new ArrayList[N+1];
        boolean[] vis = new boolean[N+1];
        dist = new int[N+1];
        for(int i = 1; i <= N; i++)
            G[i] = new ArrayList<>();
        
        for(int i = 0; i < W; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            G[b].add(a);
        }
        
        Queue<Integer> Q = new LinkedList<>();
        Q.add(N);
        vis[N] = true;
        while(!Q.isEmpty()){
            int u = Q.remove();
            for(int v : G[u]){
                if(!vis[v]){
                    dist[v] = dist[u]+1;
                    vis[v] = true;
                    Q.add(v);
                }
            }
        }
        
        S = new int[N+1];
        pos = new int[N+1];
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= N; i++){
            S[i] = Integer.parseInt(st.nextToken());
            pos[S[i]] = i-1;
            if(!vis[i])
                dist[i] = 2*N;
        }
        
        lo = new int[4*N];
        hi = new int[4*N];
        min = new int[4*N];
        map = new int[4*N];
        initialize(1, 1, N);
        for(int i = 0; i < D; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            update(a, b);
        }
    }
}