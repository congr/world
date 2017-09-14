#include <stdio.h>
#include <algorithm>
#include <vector>
using namespace std;

int getInt()
{
	int x;
	scanf ("%d",&x);
	return x;
}

int main()
{
	freopen ("input.txt","rb",stdin);
	freopen ("output.txt","wb",stdout);

	int Test = getInt(); while (Test--){
		vector<int> r,b,p;
		int L = getInt(), M = getInt(), N = getInt();
		for (int i=0;i<L;i++) r.push_back(getInt());
		for (int i=0;i<M;i++) b.push_back(getInt());
		for (int i=0;i<N;i++) p.push_back(getInt());
		sort(r.begin(),r.end());
		sort(b.begin(),b.end());
		sort(p.begin(),p.end());

		long long ans = 0;
		if (p.empty()){
			if (!r.empty()) ans += r.back() - r[0];
			if (!b.empty()) ans += b.back() - b[0];
		}
		else{
			if (!r.empty()){
				if (r[0] < p[0]) ans += p[0] - r[0];
				if (p.back() < r.back()) ans += r.back() - p.back();
			}
			if (!b.empty()){
				if (b[0] < p[0]) ans += p[0] - b[0];
				if (p.back() < b.back()) ans += b.back() - p.back();
			}

			int i = 0, j = 0;
			while (i < r.size() && r[i] <= p[0]) i++;
			while (j < b.size() && b[j] <= p[0]) j++;
			for (int k=1;k<p.size();k++){
				int rm = 0, rl = p[k-1];
				while (i < r.size() && r[i] <= p[k]){
					rm = max(rm, r[i] - rl);
					rl = r[i];
					i++;
				}
				rm = max(rm, p[k] - rl);

				int bm = 0, bl = p[k-1];
				while (j < b.size() && b[j] <= p[k]){
					bm = max(bm, b[j] - bl);
					bl = b[j];
					j++;
				}
				bm = max(bm, p[k] - bl);

				ans += 2ll * (p[k] - p[k-1]) + min(0ll, 1ll * (p[k] - p[k-1]) - rm - bm);
			}
		}

		printf ("%lld\n",ans);
	}

	return 0;
}