#include <bits/stdc++.h>

using namespace std;
const int maxN = 2e5+1;
const int SIZE = 4*maxN;

// O(DlogN)

int N, W, D, a, b, ans, dist[maxN], S[maxN], pos[maxN];
int lo[SIZE], hi[SIZE], mn[SIZE], mp[maxN];
bool vis[maxN];
vector<int> G[maxN];
queue<int> Q;

void pull(int i){
	if(lo[i] != hi[i])	mn[i] = min(mn[2*i], mn[2*i+1]);
}

void build(int i, int l, int r){
	lo[i] = l; hi[i] = r;
	if(l == r){
		mn[i] = pos[l]+dist[l];
		mp[l] = i;
		return;
	}
	int m = (l+r)/2;
	build(2*i, l, m);
	build(2*i+1, m+1, r);
	pull(i);
}

int main(){
	scanf("%d %d %d", &N, &W, &D);
	for(int i = 0; i < W; i++){
		scanf("%d %d", &a, &b);
		G[b].push_back(a);
	}
	
	Q.push(N);
	vis[N] = true;
	while(!Q.empty()){
		int u = Q.front(); Q.pop();
		for(int v : G[u]){
			if(!vis[v]){
				dist[v] = dist[u] + 1;
				vis[v] = true;
				Q.push(v);
			}
		}
	}
	
	for(int i = 1; i <= N; i++)
		if(!vis[i])
			dist[i] = 1e9;
	
	for(int i = 1; i <= N; i++){
		scanf("%d", &S[i]);
		pos[S[i]] = i-1;
	}
	
	build(1, 1, N);
	for(int d = 0, i; d < D; d++){
		scanf("%d %d", &a, &b);
		swap(pos[S[a]], pos[S[b]]);
		swap(S[a], S[b]);
		
		i = mp[S[a]];
		mn[i] = pos[S[a]]+dist[S[a]];
		while(i){
			pull(i);
			i >>= 1;
		}
		
		i = mp[S[b]];
		mn[i] = pos[S[b]]+dist[S[b]];
		while(i){
			pull(i);
			i >>= 1;
		}
		
		printf("%d\n", mn[1]);
	}
}
