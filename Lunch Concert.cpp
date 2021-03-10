#include <bits/stdc++.h>

using namespace std;
typedef long long ll;
const int maxP = 1e6;
const int SIZE = 4*maxP;

// Segment Tree O((N+P)logP)
// Fails on Subtask 3

int N, p, d, lo[SIZE], hi[SIZE];
ll w, ans, delta[SIZE][3];

void build(int i, int l, int r){
	lo[i] = l; hi[i] = r;
	if(l == r)	return;
	int m = (l+r)/2;
	build(2*i, l, m);
	build(2*i+1, m+1, r);
}

void push(int i){
	for(int j = 0; j < 3; j++){
		if(delta[i][j]){
			delta[2*i][j] += delta[i][j];
			delta[2*i+1][j] += delta[i][j];
			delta[i][j] = 0;
		}
	}
}

void increment(int type, int i, int l, int r, ll v){
	if(l > hi[i] || r < lo[i])  return;
	if(l <= lo[i] && hi[i] <= r){
		delta[i][type] += v;
		return;
	}
	
	increment(type, 2*i, l, r, v);
	increment(type, 2*i+1, l, r, v);
}

void flush(int i){
	if(lo[i] == hi[i]){
		ll sum0 = delta[i][0] * (hi[i]-lo[i]+1);
		ll sum1 = delta[i][1] * lo[i];
		ll sum2 = delta[i][2] * (N-lo[i]+1);
		ans = min(ans, sum0+sum1+sum2);
	} else {
		push(i);
		flush(2*i);
		flush(2*i+1);
	}
}

int main(){
	scanf("%d", &N);
	build(1, 0, maxP);
	for(int i = 0; i < N; i++){
		scanf("%d %lld %d", &p, &w, &d);
		if(p+d < maxP){
			increment(0, 1, p+d+1, maxP, -w*(p+d));
			increment(1, 1, p+d+1, maxP, w);
		}
		if(p-d > 0){
			increment(0, 1, 0, p-d-1, -w*(N-p+d+1));
			increment(2, 1, 0, p-d-1, w);
		}
	}
	
	ans = LONG_MAX;
	flush(1);
	printf("%lld\n", ans);
}
