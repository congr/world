#include <cstdio>
#include <algorithm>

#define MAX_N 1000
typedef long long lld;

using namespace std;

int N;

struct COO {
	int x, y;

	COO() {}

	COO(int &_x, int &_y) {
		x = _x;
		y = _y;
	}
};

int ccw(int ax, int ay, int bx, int by, int cx, int cy)
{
	lld k = (lld)(bx - ax)*(cy - ay) - (lld)(cx - ax)*(by - ay);
	if (k > 0) return 1;
	if (k) return -1;
	return 0;
}
lld abs(lld x){return x>0?x:-x;}
int ccw(const COO &a, const COO &b, const COO &c) { return ccw(a.x, a.y, b.x, b.y, c.x, c.y); }
long long area(int ax, int ay, int bx, int by, int cx, int cy)
{
	return abs((lld)(bx - ax)*(cy - ay) - (lld)(cx - ax)*(by - ay));
}
long long area(const COO &a, const COO &b, const COO &c) { return area(a.x, a.y, b.x, b.y, c.x, c.y); }


COO A[MAX_N + 1];


int main() {
	int i, j, k, l;

	int T;  scanf("%d", &T); while (T--) {
		long long answer = -1;

		scanf("%d", &N);
		for (i = 0; i < N; i++) scanf("%lld%lld", &A[i].x, &A[i].y);

		long long S;
		for (i = 0; i < N; i++) for (j = 0; j < N; j++) for (k = 0; k < N; k++) if (ccw(A[i], A[j], A[k]) > 0) for (l = 0; l < N; l++) if (ccw(A[j], A[k], A[l]) > 0 && ccw(A[k], A[l], A[i]) > 0 && ccw(A[l], A[i], A[j]) > 0) {
			S = area(A[i], A[j], A[k]) + area(A[k], A[l], A[i]);
			if (answer == -1) answer = S;
			else answer = min(answer, S);

			if (answer == S) printf("%lld [%d, %d], [%d, %d], [%d, %d], [%d, %d]\n", S, A[i].x, A[i].y, A[j].x, A[j].y, A[k].x, A[k].y, A[l].x, A[l].y);
		}
		if (answer == -1) puts("-1.0");
		else printf("%lld%s\n", answer / 2, answer & 1 ? ".5" : ".0");
		printf("%lld\n", answer);
	}

	return 0;
}
