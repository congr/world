#include <stdio.h>
#include <vector>
#include <algorithm>
#include <stdlib.h>
#include <queue>
#define NM 1000
using namespace std;

struct pp{
    int pos,data;
    pp(){}
    pp(int a,int b){
        pos=a; data=b;
    }
    bool operator<(const pp &q)const{
        return data < q.data;
    }
};

vector<pp> v[NM];
int n,m,h[NM],s,t;
int d[NM],chk[NM];
int ts,ans;

int dij(){
    int i,j;
    for (i=1;i<=n;i++) d[i]=-1e9,chk[i]=0;
    priority_queue<pp> q;

    q.push(pp(1,s)); d[1]=s;
    while(!q.empty()){
        pp here = q.top(); q.pop();
        if (chk[here.pos]) continue;
        chk[here.pos]=1;
        for (j=0;j<v[here.pos].size();j++){
            pp there = v[here.pos][j];

            if (h[here.pos]-there.data <= 0) continue;

            if (chk[there.pos]) continue;
            int H = min(h[there.pos],d[here.pos]-there.data);
            if (d[there.pos] < H){
                d[there.pos] = H;
                q.push(pp(there.pos,d[there.pos]));
            }
        }
    }
    return s-d[n]+abs(t-d[n]);
}

int main(){
    bool flag=true;
    int T; scanf("%d",&T); while(T--){
        int i,j;
        scanf("%d %d",&n,&m);
        for (i=1;i<=n;i++) v[i].clear();
        for (i=1;i<=n;i++) scanf("%d",&h[i]);
        for (i=1;i<=m;i++){
            int a,b,c;
            scanf("%d %d %d",&a,&b,&c);
            v[a].push_back(pp(b,c));
            v[b].push_back(pp(a,c));
        }
        scanf("%d %d",&s,&t);
        ans=dij();
        if (ans > 1e9) ans=-1;
        printf("#%d %d\n",++ts,ans);
    }
}
