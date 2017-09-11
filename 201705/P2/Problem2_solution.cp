#include <bits/stdc++.h>
using namespace std;

#define MAXN 100005

int T, N;
int A[MAXN];

bool proc(int r)
{
	for (int i=1,l=1;i<=N;i++){
		while (l < i && A[l]+r < A[i]-r) l++;
		if (i > 2 && (i-l) < 2) return 0;
		if (i > 1 && (i-l) < 1) return 0;
	}
	return 1;
}

int main()
{
	for (scanf("%d", &T);T--;){
		scanf("%d", &N);
		for (int i=1;i<=N;i++) scanf("%d", A+i);
		sort(A+1, A+N+1);
		int s = 0, e = (int)1e9, ans;
		while (s <= e){
			int m = s+e >> 1;
			if (proc(m)) e = m-1, ans = m;
			else s = m+1;
		}
		printf("%d\n", ans);
	}
}

