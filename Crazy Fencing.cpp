#include <bits/stdc++.h>

using namespace std;
typedef long long ll;
const int maxN = 1e4+5;

int N, h[maxN], w[maxN];
ll total;

int main(){
	scanf("%d", &N);
	for(int i = 0; i <= N; i++)
		scanf("%d", &h[i]);
	for(int i = 0; i < N; i++)
		scanf("%d", &w[i]);
	for(int i = 0; i < N; i++)
		total += (h[i]+h[i+1]) * w[i];
	printf("%lld%s", (total/2), (total&1 ? ".5\n" : "\n"));
}
