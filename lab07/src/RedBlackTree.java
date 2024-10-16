public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        boolean isBlack_1 = node.isBlack;
        node.isBlack = node.left.isBlack;
        node.left.isBlack = isBlack_1;
        RBTreeNode<T> node_2 = node.left;
        if(node.left.right != null){
            RBTreeNode<T> node_1 = new RBTreeNode<>(true,node.left.right.item);
            node.left.right = node;
            node.left = node_1;
        }else{
            node.left.right = node;
            node.left = null;
        }
        return node_2;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        boolean isBlack_1 = node.isBlack;
        node.isBlack = node.right.isBlack;
        node.right.isBlack = isBlack_1;
        RBTreeNode<T> node_2 = node.right;
        if(node.right.left != null){
            RBTreeNode<T> node_1 = new RBTreeNode<>(true,node.right.left.item);
            node.right.left = node;
            node.right = node_1;
        }else{
            node.right.left = node;
            node.right = null;
        }
        return node_2;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }

    /**
     * Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        //Insert (return) new red leaf node.
        if(node == null){
            return new RBTreeNode<>(false,item);
        }if(node.item.compareTo(item) < 0){
            node.right = insert(node.right, item);
        }else if(node.item.compareTo(item) > 0){
            node.left = insert(node.left, item);
        }

        //Handle normal binary search tree insertion.
        //Rotate left operation
        if(isRed(node.left) && isRed(node.left.right)){
            node.left=rotateLeft(node.left);
        }
        if(isRed(node.right) && !isRed(node.left)){
            node = rotateLeft(node);
        }
        //Rotate right operation
        if(isRed(node.left) && isRed(node.left.left)){
            node = rotateRight(node);
        }
        //Color flip
        if(isRed(node.left) && isRed(node.right)){
            flipColors(node);
        }
        return node;
        //fix this return statement
    }

    public static void main(String[] args) {
        RedBlackTree<Integer> rbtree = new RedBlackTree<>();
        rbtree.insert(10);
        rbtree.insert(15);
    }
}
