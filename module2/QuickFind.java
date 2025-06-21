/*
algorithm: quick-find (quick find but slow union)
initialize: O(N)
union: O(N)
find(connected()): O(1)
Quick-find defect: union() is very expensive
Ex. Takes N^2 array accesses to process sequence of N union commands on N objects
*/

public class QuickFind implements UF {

    private final int[] id;

    public QuickFind(int n) {
        id = new int[n];
        init();
    }

    @Override
    public void union(int p, int q) {
        int val = id[p];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == val) {
                id[i] = id[q];
            }
        }
    }

    /*
    @Override
    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid;
            }
        }
    }
    */

    @Override
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    private void init() {
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }
    }

}
