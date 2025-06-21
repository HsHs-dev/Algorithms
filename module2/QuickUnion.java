/*
algorithm: quick-union
initialize: O(N)
union: O(N)
find(connected()): O(N)
Quick-union defect:
  - Trees can get tall
  - Find too expensive (could be N array accesses)
*/

public class QuickUnion implements UF {

    private final int[] id;

    public QuickUnion(int n) {
        id = new int[n];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }
    }

    @Override
    public void union(int p, int q) {
        int proot = root(p);
        int qroot = root(q);
        id[proot] = qroot;
    }
   @Override
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    private int root(int i) {
        while (i != id[i]) {
            i = id[i];
        }
        return i;
    }


}
