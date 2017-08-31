#include <stdio.h>
#include <vector>
#include <algorithm>
#define FOR(i,n,m) for (int i=(n);i<=(m);i++)
#define si(n) fscanf(in,"%d",&n)
#define sl(n) fscanf(in,"%lld",&n)
#define sd(n) fscanf(in,"%lf",&n)
#define NM 5005
#define MOD 1000000007
#define INF 0x7fffffff
#define vi vector<int>
//FILE *in = fopen("input.txt", "r"), *out = fopen("output.txt", "w");
FILE *in = stdin, *out = stdout;
#pragma comment(linker, "/STACK:16777216")
typedef long long int ll;
using namespace std;
int n, K;
int dy[5005];
int check[105];
int a[55];
void input() {
	si(n), si(K);
	FOR(i, 1, 100) check[i] = 0;
	FOR(i, 1, n) si(a[i]), check[a[i]] = 1;
}
void pro() {
	FOR(i, 1, K) dy[i] = 0;
	dy[0] = 1;
	FOR(i, 1, n)
		for (int j = K; j >= a[i]; j--)
			if (dy[j - a[i]]) dy[j] = 1;

FOR(i, 1, n)
printf("%d", dy[i]);

	int ans = 0;
	FOR(i, 1, K)
		if (dy[i] == 0)
			if (ans == 0) {
				ans = i;
				break;
			}
	if (ans) {
		int find = -1;
		FOR(i, 1, ans) {
			if (check[i]) continue;
			int flag = 0;
			FOR(j, 1, K) {
				if (dy[j]) continue;
				if (j >= i && dy[j - i]) continue;
				flag = 1; break;
			}
			if (flag == 0) {
				find = i;
				break;
			}
		}
		ans = find;
	}
	fprintf(out, "%d\n", ans);
}
int main() {
	int TT; si(TT);
	FOR(tt, 1, TT) {
		input();
		pro();
	}
	return 0;
}