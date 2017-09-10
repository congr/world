#include<stdio.h>
#include<algorithm>
using namespace std;

typedef pair<int,int> pii;

const int MAXN = 10000 + 1;
const int MAXK = 2050 + 1;

pii tools[] = { {1, 1}, {4, 3}, {5, 2}, {9, 3}, {10, 2}};

int TC;

bool D[MAXN][4];
bool possible(int N, int K){
    for(int i = 0; i <= N; i++)D[i][0] = false;
    D[0][0] = true;
    for(int k = 1; k <= K; k++){
        for(int n = 1; n <= N; n++){
            D[n][k&3] = false;
            for(int i = 0; i < 5; i++) {
                if(n - tools[i].first >= 0 && k - tools[i].second >= 0 && D[n - tools[i].first][(k - tools[i].second + 4) & 3]) {
                    D[n][k&3] = true;
                }
            }
        }
    }
    return D[N][K&3];
}

int main(){
	scanf("%d", &TC);
	for(int tc = 1; tc <= TC; tc++){
		int N, K;
		scanf("%d%d", &N, &K);
		if(N < K){
			puts("X");
			continue;
		}
		if(K >= MAXK){
			puts("O");
			continue;
		}
		if(possible(N, K)){
			puts("O");
		}
		else{
			puts("X");
		}
	}
	return 0;
} 
