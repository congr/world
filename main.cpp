#include <iostream>
#include <vector>
#include <queue>
#include <cmath>

using namespace std;

int N;
const int MAX = 8;
double dist[MAX][MAX];

const double INF = INFINITY ;

double shortestPath(vector<int> &path, vector<bool> &visited, double currentLength) {
    
    if (path.size() == N) return currentLength + dist[path[0]][path.back()];
    double ret = INF;
    
    for (int next =0; next<N;++next){
        if(visited[next]) continue;
        
        int here = path.
    }
}

bool isSame(vector<int> &vector1, vector<int> &vector2) {
    int N = vector1.size();
    for (int begin = 0; begin < N; ++begin) {
        bool same = true;
        for (int i = 0; i < N; ++i) {
            int ia = (begin + i) % N;
            if (vector1[ia] != vector2[i]) {
                same = false;
                break;
            }
        }
        if (same) return true;
    }
    return false;
}

int main() {
    int T;
    cin >> T;
    while (T--) {
        cin >> N;
        vector<int> vector1(N, 0);
        vector<bool> vector2(N, true);
        vector<int> vectorArray[10001];
        
        for (int i = 0; i < N; ++i) {
            cin >> vector1[i];
        }
        
        for (int i = 0; i < N; ++i) {
            int &a = vector1[i];
            int b;
            cin >> a >> b;
            vectorArray[a].push_back(b);
        }
        
        if (isSame(vector1, vector2))
            cout << "1" << endl;
        else
            cout << "0" << endl;
        
        vector<int> children[50001];
        vector<int> depth(N + 1, 1);
        queue<int> q;
        q.push(0);
        
        int res = 0;
        while (!q.empty()) {
            int cand = q.front();
            q.pop();
            int cur_depth = depth[cand];
            for (int i = 0; i < children[cand].size(); ++i) {
                int next = children[cand][i];
                q.push(next);
                depth[next] = cur_depth + 1;
                res = max(res, depth[next]);
            }
        }
    }
}