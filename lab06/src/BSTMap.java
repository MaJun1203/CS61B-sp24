import edu.princeton.cs.algs4.In;

import java.util.Iterator;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V>{

    private int size = 0;
    private Entry node;
    private BSTMap<K, V> left;
    private  BSTMap<K, V> right;
    public BSTMap(){
        node = null;
        left = null;
        right = null;
    }
    @Override
    public void put(K key, V value) {
        put_helper(this,key,value);
    }

    public void put_helper(BSTMap<K,V> a, K key, V value) {
        Entry item = new Entry(key,value);
        if(node == null){
            node = item;
            a.size++;
        }else if(node.key.compareTo(key) < 0){
            if(right == null){
                right = new BSTMap<>();
                right.node = item;
                a.size++;
            }else{
                right.put_helper(a,key,value);
            }
        }else if(node.key.compareTo(key) > 0){
            if(left == null){
                left = new BSTMap<>();
                left.node = item;
                a.size++;
            }else{
                left.put_helper(a,key,value);
            }
        }else{
            node.value = value;
        }
    }


    @Override
    public V get(K key) {
        if(node == null){
            return null;
        }
        if(node.key.compareTo(key) < 0){
            if(right == null){
                return null;
            }
            return right.get(key);
        }else if(node.key.compareTo(key) > 0){
            if(left == null){
                return null;
            }
            return left.get(key);
        }else{
            return node.value;
        }
    }

    @Override
    public boolean containsKey(K key) {
        if(node == null){
            return false;
        }
        if (node.key.compareTo(key) < 0){
            if(right == null){
                return false;
            }
            return right.containsKey(key);
        }else if(node.key.compareTo(key) > 0){
            if(left == null){
                return false;
            }
            return left.containsKey(key);
        }else{
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        node = null;
        right = null;
        left = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
    private class Entry{
        K key;
        V value;
        Entry(K k, V v){
            key = k;
            value = v;
        }
    }

    public static void main(String[] args) {
//        BSTMap lf = new BSTMap();
//        lf.put("XiaoMa",20);
//        lf.put("MM",48);
//        lf.put("BB",48);
//        lf.put("JJ",25);
//        System.out.println(lf.size());
//        lf.put("XiaoMa",21);
//        System.out.println(lf.size());
        BSTMap<String, String> b = new BSTMap<>();
        b.put("d", "parmesan");
        b.put("a", "mozzarella");
        b.put("c", "swiss");
        b.put("b", "pepper jack");
        b.put("e", "gouda");
        b.put("b", "provolone");
        System.out.println(b.size());
        BSTMap<String, Integer> a = new BSTMap<>();
        for (int i = 0; i < 10; i++) {
            a.put("hi" + i, 1+i);
            System.out.println(a.containsKey("hi" + i));
        }

    }
}
