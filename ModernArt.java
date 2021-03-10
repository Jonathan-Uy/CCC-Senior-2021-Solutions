import java.io.*;
import java.util.*;

public class ModernArt {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int M = Integer.parseInt(br.readLine());
        int N = Integer.parseInt(br.readLine());
        int K = Integer.parseInt(br.readLine());
        StringTokenizer st;
        
        boolean[] row = new boolean[M+1];
        boolean[] col = new boolean[N+1];
        for(int i = 0; i < K; i++){
            st = new StringTokenizer(br.readLine());
            char c = st.nextToken().charAt(0);
            int x = Integer.parseInt(st.nextToken());
            if(c == 'R')        row[x] ^= true;
            else if(c == 'C')   col[x] ^= true;
        }
        
        int R = 0;
        int C = 0;
        for(int i = 1; i <= M; i++)
            if(row[i])
                R++;
        for(int i = 1; i <= N; i++)
            if(col[i])
                C++;
        
        System.out.println(C*M + R*N - 2*C*R);
    }
}