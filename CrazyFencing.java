import java.io.*;
import java.util.*;

public class CrazyFencing {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        
        int[] h = new int[N+1];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i <= N; i++)
            h[i] = Integer.parseInt(st.nextToken());
        
        long total = 0;
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            int w = Integer.parseInt(st.nextToken());
            total += (h[i] + h[i+1]) * w;
        }
        
        if(total % 2 == 0)  System.out.printf("%d\n", total/2);
        else                System.out.printf("%d.5\n", total/2);
    }
}