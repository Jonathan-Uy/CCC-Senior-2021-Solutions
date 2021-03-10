#include <bits/stdc++.h>

using namespace std;
const int maxN = 5e6+5;

int M, N, K, x, total;
bitset<maxN> row, col;
char c;

int main(){
	scanf("%d %d %d", &M, &N, &K);
	for(int i = 0; i < K; i++){
		scanf(" %c %d", &c, &x);
		if(c == 'R')		row.flip(x);
		else if(c == 'C')	col.flip(x);
	}
	
	for(int i = 1; i <= M; i++)
		for(int j = 1; j <= N; j++)
			if(row[i]^col[j])
				total++;
	
	printf("%d\n", total);
}
