#include <stdio.h>

int main()
{
	freopen ("input.txt","r",stdin);
	freopen ("output.txt","w",stdout);

	const int V = 5;
	const int mod = 100000007;

	int Test; scanf ("%d",&Test); while (Test--){
		int A[V] = {0,};
		for (int i=0;i<V;i++){
			scanf ("%d",&A[i]);
			if (A[i] < 1 || A[i] > 9) fprintf (stderr,"no");
		}

		for (int n=0,s=0;;n++){
			s += A[n%5];

			int t = s, l = 0, x = t % 10, g = 1;
			while (t){
				if (t % 10 != x) g = 0;
				t /= 10; l++;
			}

			if (l > 1 && x && g){
				printf ("%d %d(%d)\n",n,x,l);
				break;
			}
		}
	}
	return 0;
}