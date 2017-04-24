#include <stdio.h>
#include <math.h>
#include <string.h>
#define MAX_N 200
#define MAX_LEN (1 << 30) /* 2^30 */
int N;
struct pos {
    int x;
    int y;
    double degree;
};
struct pos hull[MAX_N];
int g_id;
struct pos grp_p[2][MAX_N];
int grp_s_idx[2][MAX_N];
int grp_n[2];
/* return a, b, c  of ax + by + c = 0 */
void get_param_linear_fun(struct pos* p1, struct pos* p2, int* abc)
{
    abc[0] = p2->y - p1->y;
    abc[1] = p1->x - p2->x;
    abc[2] = -(abc[1] * p1->y) -(abc[0] * p1->x);
}
#define hx(idx) (hull[grp_s_idx[g_id][idx]].x)
#define hy(idx) (hull[grp_s_idx[g_id][idx]].y)
#define gx(idx) (grp_p[g_id][grp_s_idx[g_id][idx]].x)
#define gy(idx) (grp_p[g_id][grp_s_idx[g_id][idx]].y)
#define g_dgr(idx) (grp_p[g_id][grp_s_idx[g_id][idx]].degree)
#define getp(idx) (grp_p[g_id][grp_s_idx[g_id][idx]])
#define min(a, b) a < b ? a : b
int comp_dgr(const void* a, const void* b)
{
    double a_ = grp_p[g_id][*(int *)a].degree;
    double b_ = grp_p[g_id][*(int *)b].degree;
    if (a_ < b_) {
        return 1;
    }
    return -1;
}
double get_distance(struct pos* p1, struct pos* p2)
{
    int dx = p1->x - p2->x;
    int dy = p1->y - p2->y;
    double dist = sqrt((dx * dx) + (dy * dy));
    return dist;
}
int convex(int* abc, struct pos* p) {
    int a = abc[0];
    int b = abc[1];
    int c = abc[2];
    long m = ((long)a * p->x) + ((long)b * p->y) + c;
    if (m > 1) m = 1;
    if (m < -1) m = -1;
    if (m * c >= 0)
        return 1;
    return 0;
}
int abc[3];
int stack[MAX_N + 1];
double get_outline(int id)
{
    int i;
    int min_x = MAX_LEN;
    int min_y = MAX_LEN;
    int gn = grp_n[id];
    g_id = id;
    if (gn < 2) {
        return 0.0;
    }
    for (i = 0; i < gn; i++) {
        if (min_x > hx(i)) {
            min_x = hx(i);
            min_y = hy(i);
        } else if (min_x == hx(i)) {
            if (min_y > hy(i)) {
                min_y = hy(i);
            }
        }
    }
    int Ox = min_x;
    int Oy = min_y;
    for (i = 0; i < gn; i++) {
        int x = hx(i) - Ox;
        int y = hy(i) - Oy;
        gx(i) = x;
        gy(i) = y;
        if (x == 0 && y == 0)
            g_dgr(i) = 3.14 +1.0; //for first index after sorting
        else
            g_dgr(i) = atan2(y, x);
    }
    qsort(grp_s_idx[id], gn, sizeof(grp_s_idx[id][0]), comp_dgr);

    int stk_n = 0;
    stack[stk_n++] = 0;
    stack[stk_n++] = 1;
    int curr = 2;
    while (curr <= gn) {
        int z = stack[stk_n - 2];
        int before = stack[stk_n - 1];
        get_param_linear_fun(&getp(z), &getp(before), abc);
        if (!convex(abc, &getp(curr % gn))) {
            while (stk_n >= 2) {
                stk_n--;
                if (stk_n == 1) {
                    break;
                }
                before = stack[stk_n - 1];
                z = stack[stk_n - 2];
                get_param_linear_fun(&getp(z), &getp(before), abc);
                if (convex(abc, &getp(curr % gn))) {
                    break;
                }
            }
        }
        stack[stk_n++] = curr++;
    }
    stk_n--;
    double len = 0.0;
    for (i = 0; i < stk_n; i++) {
        int a = stack[i];
        int b = stack[(i + 1) % stk_n];
        len += get_distance(&getp(a), &getp(b));
    }
    return len;
}
void seperate(int p1, int p2)
{
    int i, j;
    grp_n[0] = 0;
    grp_n[1] = 0;
    if (p1 == p2) {
        for (i = 0, j = 0; i < N; i++) {
            if (j == p1)
                j++;
            grp_s_idx[0][i] = j++;
        }
        grp_n[0] = N - 1;
        return;
    }
    get_param_linear_fun(&hull[p1], &hull[p2], abc);
    int a = abc[0];
    int b = abc[1];
    int c = abc[2];
    for (i = 0; i < N; i++) {
        long m = ((long)a * hull[i].x) + ((long)b * hull[i].y) + c;
        if (m >= 0) {
            grp_s_idx[0][grp_n[0]++] = i;
        } else {
            grp_s_idx[1][grp_n[1]++] = i;
        }
    }
}
double find_min_len(void)
{
    int i, j;
    double len;
    double min_outline = MAX_LEN;
    for (i = 0; i < N; i++) {
        for (j = 0; j < N; j++) {
            seperate(i, j);
            len = get_outline(0);
            len += get_outline(1);
            min_outline = min(min_outline, len);
        }
    }
    return min_outline;
}
int main(void)
{
    int T, i;
    scanf("%d", &T);
    while (T--) {
        scanf("%d", &N);
        for (i = 0; i < N; i++) {
            scanf("%d%d", &hull[i].x, &hull[i].y);
        }
        printf("%.5lf\n", round(find_min_len() * 100000) / 100000);
    }
}