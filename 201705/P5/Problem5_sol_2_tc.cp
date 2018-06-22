#include <stdio.h>
#include <vector>
#include <algorithm>
#include <math.h>
#define M 100009
#define INF 1e8
#define MOD 1000000007
using namespace std;

typedef long long ll;
typedef pair<long,double> pld;

struct Point{
    ll x,y;
    int pos;
    Point(){}
    Point(ll a,ll b):x(a),y(b){}
}s[M],v[M];

int n,k,m;
vector<int> st;
int rn[M];

// commit : change output form
int sum;
//

int ccw(Point a,Point b,Point c){
    ll ret=(b.x-a.x)*(c.y-a.y)-(c.x-a.x)*(b.y-a.y);
    if (ret>0) return 1;
    else if (ret<0) return -1;
    else return 0;
}

bool coma(Point da,Point db){
    int ca=ccw(Point(0,0),Point((ll)INF+1,-1),da), cb=ccw(Point(0,0),Point((ll)INF+1,-1),db);
    if (ca*cb<0) return ca>cb;
    else{
        return ccw(Point(0,0),da,db)>=0;
    }
}

ll absl(ll a){return a>0?a:-a;}

int get_pos(Point da){
    int ret=0;
    int le=0,ri=st.size()-1,i,ii;
    while(le<=ri){
        i=(le+ri)>>1;
        ii=(i+1)%(int)st.size();
        if (coma(da,Point(s[st[ii]].x-s[st[i]].x,s[st[ii]].y-s[st[i]].y))){
            ret=i;
            ri=i-1;
        }
        else le=i+1;
    }
    ii=(ret+1)%(int)st.size();
    ll dx=s[st[ii]].x-s[st[ret]].x; ll dy=s[st[ii]].y-s[st[ret]].y;
    if (dx*da.y!=dy*da.x) return s[st[ret]].pos;
    else{
        if (s[st[ii]].x < s[st[ret]].x || (s[st[ii]].x==s[st[ret]].x && s[st[ii]].y < s[st[ret]].y))
            return s[st[ii]].pos;
        else return s[st[ret]].pos;
    }
}
int main(){
    int T; scanf("%d",&T); while(T--){
        int i;
        scanf("%d %d %d",&n,&k,&m);
        //initial
        sum=0;
        st.clear();
        for (i=1;i<=n;i++) rn[i]=0;
        //

    for (i=1;i<=k;i++){
        scanf("%lld %lld",&s[i].x,&s[i].y); s[i].pos=i;
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
//    printf("%d\n",(int)st.size());

    int prea=-1,preb=-1;
    for (i=1;i<=m;i++){
        int a,b;
        scanf("%d %d",&a,&b);
//        printf("%d %d\n",rn[a],rn[b]);
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

        
        int pos1=get_pos(Point(v[b].x-v[a].x,v[b].y-v[a].y));
        int pos2=get_pos(Point(v[a].x-v[b].x,v[a].y-v[b].y));
        
//        if (pos1!=a && pos1!=b) printf("%d\n",pos1);
//        else printf("%d\n",pos2);
        if (pos1!=a && pos1!=b) sum=(sum+pos1)%MOD;
        else sum=(sum+pos2)%MOD;
    }
    printf("%d\n",sum);
    }
    return 0;
}
