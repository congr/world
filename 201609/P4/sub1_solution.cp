#include <stdio.h>
#include <vector>
#include <algorithm>
#include <queue>
#include <stdlib.h>
#define NM 1009
using namespace std;

struct pp{
    int pos,data;
    pp(){}
    pp(int a,int b){
        pos=a; data=b;
    }
    bool operator<(const pp &q)const{
        return data > q.data;
    }
};

int ans,n,m,s,t,h[NM],ts,chk[NM];
vector<pp> v[NM];

int go(int here,int H,int c){
    if (here==n) return abs(t-H);
    int ret=1e9;
    chk[here]=1;

    for (int i=0;i<v[here].size();i++){
        pp there = v[here][i];
        if (chk[there.pos]) continue;
        
        if (H <= h[there.pos] && c==1) ret=min(ret,there.data+go(there.pos,H,0));
        else if (c==1) ret=min(ret,there.data+H-h[there.pos]+go(there.pos,h[there.pos],0));

        if (h[here]-there.data <= 0) continue;

        if (H-there.data >= h[there.pos]) ret=min(ret,H-h[there.pos]+go(there.pos,h[there.pos],c));
        else ret=min(ret,there.data+(1-min(1,H-there.data))+go(there.pos,max(1,H-there.data),c));
    }
    chk[here]=0;
    return ret;
}

int main(){
    int T; scanf("%d",&T); while(T--){
        int i;
        scanf("%d %d",&n,&m);
        for (i=1;i<=n;i++) scanf("%d",&h[i]);
        for (i=1;i<=n;i++) v[i].clear();
        for (i=1;i<=m;i++){
            int a,b,c;
            scanf("%d %d %d",&a,&b,&c);
//            if (h[a]-c>0) v[a].push_back(pp(b,c));
//            if (h[b]-c>0) v[b].push_back(pp(a,c));
            v[a].push_back(pp(b,c));
            v[b].push_back(pp(a,c));
        }
        scanf("%d %d",&s,&t);
        ans = go(1,s,1);
        if (ans==1e9) ans=-1;
        printf("#%d %d\n",++ts,ans);
    }
    return 0;
}
