import java.io.*;
import java.util.*;

public class MathHomework {
    final static int BLOCK_SZ = 350, BLOCK_CNT = 430;
    static int[] upper, lower;
    
    public static int gcd(int a, int b) { return b == 0 ? a : gcd(b, a%b); }
    public static int lcm(int a, int b) { return a / gcd(a, b) * b; }
    
    public static void update(int x, int y, int z){
    	for(int blk = 0; blk < BLOCK_CNT; blk++){
    		int l = blk*BLOCK_SZ+1;
    		int r = (blk+1)*BLOCK_SZ;
    		if(x <= l && r <= y){
    			if(upper[blk] % z != 0)
    				upper[blk] = lcm(upper[blk], z);
    		} else if((l <= x && x <= r) || (l <= y && y <= r)){
    			int left = Math.max(x, l);
    			int right = Math.min(y, r);
    			for(int j = left; j <= right; j++)
    				if(lower[j] % z != 0)
    					lower[j] = lcm(lower[j], z);
    		}
    	}
    }
    
    public static boolean query(int x, int y, int z){
    	int g = lower[x];
    	for(int blk = 0; blk < BLOCK_CNT; blk++){
    		int l = blk*BLOCK_SZ+1;
    		int r = (blk+1)*BLOCK_SZ;
    		if(x <= l && r <= y){
    			g = gcd(g, upper[blk]);
    		} else if((l <= x && x <= r) || (l <= y && y <= r)){
    			int left = Math.max(x, l);
    			int right = Math.min(y, r);
    			for(int j = left; j <= right; j++)
    				g = gcd(g, lower[j]);
    		}
    	}
    	return g == z;
    }
    
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        
        upper = new int[BLOCK_CNT];
        lower = new int[BLOCK_CNT*BLOCK_SZ+1];
        Arrays.fill(upper, 1);
        Arrays.fill(lower, 1);
        
        int[][] op = new int[M][3];
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            op[i][0] = Integer.parseInt(st.nextToken());
            op[i][1] = Integer.parseInt(st.nextToken());
            op[i][2] = Integer.parseInt(st.nextToken());
            update(op[i][0], op[i][1], op[i][2]);
        }
        
        
    	for(int i = 1; i <= N; i++)
    		if(lower[i] % upper[(i-1)/BLOCK_SZ] != 0)
    			lower[i] = lcm(lower[i], upper[(i-1)/BLOCK_SZ]);
        
        for(int blk = 0; blk < BLOCK_CNT; blk++){
            int l = blk*BLOCK_SZ+1;
            int r = (blk+1)*BLOCK_SZ;
    		int g = lower[l];
    		for(int i = l+1; i <= r; i++)
    			g = gcd(g, lower[i]);
    		upper[blk] = g;
    	}
    	
    	for(int i = 0; i < M; i++){
    		if(!query(op[i][0], op[i][1], op[i][2])){
    			System.out.println("Impossible");
    			return;
    		}
    	}
    	
    	for(int i = 1; i < N; i++)
    		System.out.print(lower[i] + " ");
    	System.out.println(lower[N]);
    }
}