/*
algorithm: weighted quick-union
initialize: O(N)
union: O(lg N)
find(connected()): O(lg N)
*/

public class WeightedQuickUnion implements UF {
    private final int[] id;
    private final int[] sizes;

    public WeightedQuickUnion(int n) {
        id = new int[n];
        sizes = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            sizes[i] = 1;
        }
//        sizes = new int[n];
//        for (int i = 0; i < n; i++) {
//            sizes[i] = 1;
//        }
    }

    @Override
    public void union(int p, int q) {
        int proot = root(p);
        int qroot = root(q);
        if (sizes[proot] >= sizes[qroot]) {
            id[qroot] = proot;
            sizes[proot] += sizes[qroot];
        } else {
            id[proot] = qroot;
            sizes[qroot] += sizes[proot];
        }
    }
    @Override
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    private int root(int i) {
        while (i != id[i]) {
//            path compression
//            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

}
