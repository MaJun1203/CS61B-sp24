public class UnionFind {
    // TODO: Instance variables

    private int[] srct;
    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        srct = new int[N];
        for(int i = 0; i < N; i++){
            srct[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        if(v < 0 || v > srct.length-1){
            throw new IllegalArgumentException("Some comment to describe the reason for throwing.");
        }
        return -srct[find(v)];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        if(v < 0 || v > srct.length-1){
            throw new IllegalArgumentException("Some comment to describe the reason for throwing.");
        }
        return srct[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if(v1 < 0 || v1 > srct.length-1 || v2 < 0 || v2 > srct.length){
            throw new IllegalArgumentException("Some comment to describe the reason for throwing.");
        }
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        if(v < 0 || v > srct.length-1){
            throw new IllegalArgumentException("Some comment to describe the reason for throwing.");
        }
        if(srct[v] < 0){
            return v;
        }
        srct[v] = find(srct[v]);
        return srct[v];
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if(v1 < 0 || v1 > srct.length-1 || v2 < 0 || v2 > srct.length){
            throw new IllegalArgumentException("Some comment to describe the reason for throwing.");
        }
        if(sizeOf(v1) > sizeOf(v2)){
            srct[find(v1)] +=srct[find(v2)];
            srct[find(v2)] = find(v1);
        }else if(sizeOf(v1) < sizeOf(v2)){
            srct[find(v2)] +=srct[find(v1)];
            srct[find(v1)] = find(v2);
        }
    }

    public static void main(String[] args) {
        UnionFind uf = new UnionFind(10);
        uf.union(0,1);
        uf.union(2,3);
        //System.out.println(uf.find(1));
        //System.out.println(uf.find(0));
        uf.union(2,4);
        uf.union(4,1);
        //System.out.println(uf.find(1));
        uf.union(1,5);
        uf.union(6,7);
        uf.union(7,8);
        uf.union(8,0);
    }
}
