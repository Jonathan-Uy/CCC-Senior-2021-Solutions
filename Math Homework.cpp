#include <bits/stdc++.h>

using namespace std;
typedef pair<int,int> pii;
const int maxN = 150001;
const int BLOCK_SZ = 350;
const int BLOCK_CNT = 430;

// O(Msqrt(N))

int N, M, op[maxN][3];
int upper[BLOCK_CNT], lower[maxN];

pii range(int block_num){
	return {block_num*BLOCK_SZ+1, (block_num+1)*BLOCK_SZ};
}

void init(){
	fill(upper, upper+BLOCK_CNT, 1);
	fill(lower, lower+maxN, 1);
}

int lcm(int a, int b){
	return (a / __gcd(a,b)) * b;
}

void update(int x, int y, int z){
	for(int blk = 0; blk < BLOCK_CNT; blk++){
		pii r = range(blk);
		if(x <= r.first && r.second <= y){
			if(upper[blk] % z)
				upper[blk] = lcm(upper[blk], z);
		} else if((r.first <= x && x <= r.second) || (r.first <= y && y <= r.second)){
			int left = max(x, r.first);
			int right = min(y, r.second);
			for(int j = left; j <= right; j++)
				if(lower[j] % z)
					lower[j] = lcm(lower[j], z);
		}
	}
}

bool query(int x, int y, int z){
	int g = lower[x];
	for(int blk = 0; blk < BLOCK_CNT; blk++){
		pii r = range(blk);
		if(x <= r.first && r.second <= y){
			g = __gcd(g, upper[blk]);
		} else if((r.first <= x && x <= r.second) || (r.first <= y && y <= r.second)){
			int left = max(x, r.first);
			int right = min(y, r.second);
			for(int j = left; j <= right; j++)
				g = __gcd(g, lower[j]);
		}
	}
	return g == z;
}

int main(){
	init();
	scanf("%d %d", &N, &M);
	for(int i = 0; i < M; i++){
		scanf("%d %d %d", &op[i][0], &op[i][1], &op[i][2]);
		update(op[i][0], op[i][1], op[i][2]);
	}
	
	for(int i = 1; i <= N; i++)
		if(lower[i] % upper[(i-1)/BLOCK_SZ])
			lower[i] = lcm(lower[i], upper[(i-1)/BLOCK_SZ]);
	
	for(int blk = 0; blk < BLOCK_CNT; blk++){
		pii r = range(blk);
		int g = lower[r.first];
		for(int i = r.first+1; i <= r.second; i++)
			g = __gcd(g, lower[i]);
		upper[blk] = g;
	}
	
	for(int i = 0; i < M; i++){
		if(!query(op[i][0], op[i][1], op[i][2])){
			printf("Impossible\n");
			return 0;
		}
	}
	
	for(int i = 1; i <= N; i++)
		printf("%d%c", lower[i], (" \n")[i==N]);
}
