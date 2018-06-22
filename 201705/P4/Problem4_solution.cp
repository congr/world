#include <stdio.h>
#include <algorithm>
#include <vector>
using namespace std;

int main()
{
	freopen ("input.txt","r",stdin);
	freopen ("output.txt","w",stdout);

	const int V = 5;
	const long long mod = 1000000007;

	int Test; scanf ("%d",&Test); while (Test--){
		int A[V+1] = {0,};
		for (int i=1;i<=V;i++){
			scanf ("%d",&A[i]);
			if (A[i] < 1 || A[i] > 99999) fprintf (stderr,"no");
			A[i] += A[i-1];
		}

		long long S = A[V], N = -1, X, L, P;

		long long n = 11 / S, r = 11 % S, l = 2; bool ed = false;
		auto upd = [&](){
			n = (n + r / S) % mod;
			r %= S;
		};
		for (int i=0;i<S;i++){
			for (int x=1;x<=9;x++){
				for (int k=0;k<V;k++){
					if ((r * x % S) == A[k]){
						n = n * x;
						r = r * x;
						upd();

						N = n; X = x; L = l; P = k;
						ed = true;
						break;
					}
				}
				if (ed) break;
			}
			if (ed) break;

			n = n * 10;
			r = r * 10 + 1;
			upd();
			l++;
		}
		if (N == -1) fprintf (stderr,"wrong");

		N = (N * V + P + mod - 1) % mod;
		printf ("%lld %lld(%lld)\n",N,X,L);
	}
	return 0;
}