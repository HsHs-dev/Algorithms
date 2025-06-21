public interface UF {

    /** add connection between p and q */
    void union(int p, int q);

    /** are the two items p and q connected */
    boolean connected(int p, int q);
}