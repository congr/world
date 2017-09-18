#include <stdio.h>

int N,P[2020],S[2020],D[2020][2020],nxtf[2020];

int max(int a, int b){return a > b ? a : b;}

int main()
{
	freopen ("input.txt","r",stdin);
	freopen ("output.txt","w",stdout);

	int T; scanf ("%d",&T); while (T--){
		scanf ("%d",&N);
		for (int i=0;i<N;i++) scanf ("%d",&P[i]);
		for (int i=0;i<N;i++) scanf ("%d",&S[i]);

		for (int i=0;i<N;i++) if (S[i] == 1){
			int j = i;
			while (1){
				nxtf[j] = i;
				j = (j + N - 1) % N;
				if (S[j] == 1) break;
			}
		}

		int ans = 0;
		for (int i=0;i<N;i++) for (int j=0;j<N;j++) D[i][j] = 0;
		for (int l=1;l<N;l++){
			for (int i=0;i<N;i++){
				int j = (i + l) % N;
				D[i][j] = max(D[(i+1)%N][j],D[i][(j+N-1)%N]);
				if (S[i] + S[j] == 1){
					int c = 0;
					if (l >= 2){
						c = D[(i+1)%N][(j+N-1)%N];
						for (int k=nxtf[(i+1)%N];k!=nxtf[j];k=nxtf[(k+1)%N]){
							if ((k + 1) % N != j) c = max(c,D[(i+1)%N][k]+D[(k+1)%N][(j+N-1)%N]);
							if ((k + N - 1) % N != i) c = max(c,D[(i+1)%N][(k+N-1)%N]+D[k][(j+N-1)%N]);
						}
					}
					D[i][j] = max(D[i][j],c+P[i]*P[j]);
					
					for (int k=nxtf[i];k!=nxtf[(j+1)%N];k=nxtf[(k+1)%N]){
						if (k != j) D[i][j] = max(D[i][j],D[i][k]+D[(k+1)%N][j]);
						if (k != i) D[i][j] = max(D[i][j],D[i][(k+N-1)%N]+D[k][j]);
					}
				}
				else if (S[i] + S[j] == 2){
					for (int k=i;k!=j;k=(k+1)%N){
						D[i][j] = max(D[i][j],D[i][k]+D[(k+1)%N][j]);
					}
				}
				if (ans < D[i][j])
					ans = D[i][j];
			}
		}

		printf ("%d\n",ans);
	}
	return 0;
}