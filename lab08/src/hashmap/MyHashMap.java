package hashmap;

import net.sf.saxon.lib.SchemaURIResolver;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 * @author XiaoMa
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    public int item_size;
    public int bucket_size;
    public double loadFactor;
    public double curLoadFactor;
    /* Instance Variables */
    private Collection[] buckets;
    // You should probably define some more!

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }



    /** Constructors */
    public MyHashMap() {
        item_size = 0;
        bucket_size = 16;
        loadFactor = 0.75;
        buckets =new Collection[bucket_size];

    }

    public MyHashMap(int initialCapacity) {
        item_size = 0;
        bucket_size = initialCapacity;
        loadFactor = 0.75;
        buckets =new Collection[bucket_size];
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        item_size = 0;
        bucket_size = initialCapacity;
        this.loadFactor = loadFactor;
        buckets =new Collection[bucket_size];
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    @Override
    public void put(K key, V value) {
        Node node = new Node(key,value);
        int index = Math.floorMod(key.hashCode(), bucket_size);
        boolean con = false;
        if(buckets[index] == null){
            Collection<Node> item = createBucket();
            item.add(node);
            this.buckets[index] = item;
            this.item_size++;
        }else{
            Iterator<Node> it = buckets[index].iterator();
            while(it.hasNext()){
                Node currentNode = it.next();
                if(currentNode.key.equals(key)){
                    currentNode.value = value;
                    con = true;
                    break;
                }
            }
            if(!con){
                buckets[index].add(node);
                this.item_size++;
            }
        }
        curLoadFactor = (double) item_size / bucket_size;
        if(curLoadFactor > loadFactor){
            MyHashMap<K, V> hashmap = new MyHashMap<>(bucket_size*2, curLoadFactor);
            for(int i = 0; i < bucket_size; i++){
                if(buckets[i] != null){
                    Iterator<Node> It = buckets[i].iterator();
                    while(It.hasNext()){
                        Node current = It.next();
                        hashmap.put(current.key,current.value);
                    }
                }
            }
            this.buckets = hashmap.buckets;
            this.bucket_size = hashmap.bucket_size;
        }
    }

    @Override
    public V get(K key) {
        int index = Math.floorMod(key.hashCode(),bucket_size);
        if(buckets[index] == null){
            return null;
        }
        Iterator<Node> it = buckets[index].iterator();
        while(it.hasNext()){
            Node current = it.next();
            if(current.key.equals(key)){
                return current.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int index = Math.floorMod(key.hashCode(),bucket_size);
        if(buckets[index] == null){
            return false;
        }else{
            Iterator<Node> it = buckets[index].iterator();
            while(it.hasNext()){
                Node current = it.next();
                if(current.key.equals(key)){
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public int size() {
        return item_size;
    }

    @Override
    public void clear() {
        for(int i = 0; i < bucket_size; i++){
            buckets[i] = null;
        }
        item_size = 0;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
        //return Set.of();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
        //return null;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
        //return null;
    }

    public static void main(String[] args) {
        MyHashMap<String, Integer> h = new MyHashMap();
        h.put("mj",1);
        h.put("mx",2);
        h.put("mlj",3);
        System.out.println(h.containsKey("mj"));
    }
}
