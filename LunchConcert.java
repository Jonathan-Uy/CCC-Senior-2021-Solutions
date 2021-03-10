import java.io.*;
import java.util.*;

public class LunchConcert {
    static int N;
    static long[] P, W, D;
    
    public static long cost(long C){
        long cost = 0;
        for(int i = 0; i < N; i++){
            if(C < P[i]-D[i])       cost += W[i]*(P[i]-D[i]-C);
            else if(C > P[i]+D[i])  cost += W[i]*(C-P[i]-D[i]);
        }
        return cost;
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        
        P = new long[N];
        W = new long[N];
        D = new long[N];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            P[i] = Long.parseLong(st.nextToken());
            W[i] = Long.parseLong(st.nextToken());
            D[i] = Long.parseLong(st.nextToken());
        }
        
        long lo = -1;
        long hi = (long) (1e9+1);
        while(hi-lo > 1){
            long mid = lo + (hi-lo)/2;
            
            long lcost = cost(mid);
            long rcost = cost(mid+1);
            if(lcost < rcost)   hi = mid;
            else                lo = mid;
        }
        System.out.println(cost(lo+1));
    }
}