#include <stdio.h>
#include <algorithm>
#include <vector>
#include <math.h>
#include <iostream>
#define M 100002
#define EPS 1e-7
#define MOD 1000000007
#define make_pair mp
using namespace std;
typedef long long ll;
typedef pair<ll,double> pld;

struct Point{
    ll x,y;
    int pos;
}s[M],v[M];
int rn[M];
int n,k,m;
vector<int> st;

// commit : change output form
int sum;
//


pld get_dist(Point a,Point b,Point X){
    ll ret1=(b.y-a.y)*X.x-(b.x-a.x)*X.y+a.y*b.x-a.x*b.y;
    if (ret1<0) ret1*=-1;
    double ret2=sqrt((b.y-a.y)*(b.y-a.y)+(b.x-a.x)*(b.x-a.x));
    return {ret1,ret2};
}
ll absl(ll a){return a>0?a:-a;}
int ccw(Point a,Point b,Point c){
    ll ret=(b.x-a.x)*(c.y-a.y)-(c.x-a.x)*(b.y-a.y);
    if (ret>0) return 1;
    else if (ret<0) return -1;
    else return 0;
}
int main(){
    int T; scanf("%d",&T); while(T--){
        int i,j;
        scanf("%d %d %d",&n,&k,&m);
        //initial
        sum=0;
        st.clear();
        for (i=1;i<=n;i++) rn[i]=0;
        //
    for (i=1;i<=k;i++){
        scanf("%lld %lld",&s[i].x,&s[i].y);
        s[i].pos=i;
        v[i]=s[i];
    }
    for (i=1;i<=k;i++) if (s[1].y > s[i].y || (s[1].y==s[i].y && s[1].x > s[i].x))
        swap(s[1],s[i]);
    sort(s+2,s+k+1,[&](Point a,Point b){
        int ret=ccw(s[1],a,b);
        if (ret) return ret>0;
        else return absl(a.x-s[1].x)+(a.y-s[1].y) < absl(b.x-s[1].x)+(b.y-s[1].y);
    });
    st.push_back(1);
    for (i=2;i<=k;i++){
        while(st.size() > 1 && ccw(s[st[st.size()-2]],s[st[st.size()-1]],s[i]) <= 0) st.pop_back();
        st.push_back(i);
    }
    for (i=0;i<st.size();i++){
        rn[s[st[i]].pos]=i+1;
//        printf("%d ",s[st[i]].pos);
    }
//    printf("\n");
    
    int prea=-1,preb=-1;
    for (i=1;i<=m;i++){
        int a,b;
        scanf("%d %d",&a,&b);
        if (!rn[a] || !rn[b]){
//            printf("0\n");
            continue;
        }
        if (prea!=-1){
            if (a!=prea && a!=preb && b!=prea && b!=preb){
//                printf("0\n");
                continue;
            }
        }
        if (rn[a]%st.size()+1!=rn[b] && rn[b]%st.size()+1!=rn[a]){
//            printf("0\n");
            continue;
        }
        prea=a; preb=b;

        pld dist={0,1}; int ans=0;
        for (j=1;j<=k;j++){
            pld ret = get_dist(v[a],v[b],v[j]);
            if ((double)ret.first/ret.second > (double)dist.first/dist.second){
                dist=ret;
                ans=j;
            }
            else if ((double)ret.first/ret.second-EPS < (double)dist.first/dist.second && (double)dist.first/dist.second < (double)ret.first/ret.second+EPS){
                if (v[j].x < v[ans].x || (v[j].x==v[ans].x && v[j].y < v[ans].y)) ans=j;
            }
        }
        //        printf("%d\n",ans);
        sum=(sum+ans)%MOD;
    }
    printf("%d\n",sum);
    }
    return 0;
}

