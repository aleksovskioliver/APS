package mk.finki.ukim.code;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.StreamSupport;
class CBHT<K extends Comparable<K>, E> {

    // An object of class CBHT is a closed-bucket hash table, containing
    // entries of class MapEntry.
    private SLLNode<MapEntry<K,E>>[] buckets;

    @SuppressWarnings("unchecked")
    public CBHT(int m) {
        // Construct an empty CBHT with m buckets.
        buckets = (SLLNode<MapEntry<K,E>>[]) new SLLNode[m];
    }

    private int hash(K key) {
        // Translate key to an index of the array buckets.
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public SLLNode<MapEntry<K,E>> search(K targetKey) {
        // Find which if any node of this CBHT contains an entry whose key is
        // equal
        // to targetKey. Return a link to that node (or null if there is none).
        int b = hash(targetKey);
        for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (targetKey.equals(((MapEntry<K, E>) curr.element).key))
                return curr;
        }
        return null;
    }

    public void insert(K key, E val) {      // Insert the entry <key, val> into this CBHT.
        MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
        int b = hash(key);
        for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (key.equals(((MapEntry<K, E>) curr.element).key) && (val.equals(((MapEntry<K, E>) curr.element).value))) {
                // Make newEntry replace the existing entry ...
                curr.element = newEntry;
                return;
            }
        }
        // Insert newEntry at the front of the 1WLL in bucket b ...
        buckets[b] = new SLLNode<MapEntry<K,E>>(newEntry, buckets[b]);
    }

    public void delete(K key) {
        // Delete the entry (if any) whose key is equal to key from this CBHT.
        int b = hash(key);
        for (SLLNode<MapEntry<K,E>> pred = null, curr = buckets[b]; curr != null; pred = curr, curr = curr.succ) {
            if (key.equals(((MapEntry<K,E>) curr.element).key)) {
                if (pred == null)
                    buckets[b] = curr.succ;
                else
                    pred.succ = curr.succ;
                return;
            }
        }
    }

    public String toString() {
        String temp = "";
        for (int i = 0; i < buckets.length; i++) {
            temp += i + ":";
            for (SLLNode<MapEntry<K,E>> curr = buckets[i]; curr != null; curr = curr.succ) {
                temp += curr.element.toString() + " ";
            }
            temp += "\n";
        }
        return temp;
    }

}
class MapEntry<K extends Comparable<K>,E> implements Comparable<K> {

    // Each MapEntry object is a pair consisting of a key (a Comparable
    // object) and a value (an arbitrary object).
    K key;
    E value;

    public MapEntry (K key, E val) {
        this.key = key;
        this.value = val;
    }

    public int compareTo (K that) {
        // Compare this map entry to that map entry.
        @SuppressWarnings("unchecked")
        MapEntry<K,E> other = (MapEntry<K,E>) that;
        return this.key.compareTo(other.key);
    }

    public String toString () {
        return "<" + key + "," + value + ">";
    }
}
class SLLNode<E> {
    protected E element;
    protected SLLNode<E> succ;

    public SLLNode(E elem, SLLNode<E> succ) {
        this.element = elem;
        this.succ = succ;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}
public class OBHTRodendeni{
    public static boolean isPrime(int m) {
        boolean prime = true;
        for(int i=2;i<Math.sqrt(m);i++) {
            if(m%i==0) prime = false;
        }
        return prime;
    }
    public static int findM(int n) {
        int m = (int)(n / 0.7);
        while(!isPrime(m)) m++;
        return m;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        CBHT<String,String> tabela = new CBHT<>(findM(N));

        for (int i=0;i<N;i++){
            String line = br.readLine();
            String parts[] = line.split("\\s+");
            String ime_prez = parts[0] + " " + parts[1];
            String dates[] = parts[2].split("/");
            String datum = dates[0]+ "/" + dates[1];
            ime_prez += " " + dates[2];
            tabela.insert(datum,ime_prez);
        }

        String baran[] = br.readLine().split("/");
        String d_m = baran[0]+"/"+baran[1];
        int y = Integer.parseInt(baran[2]);

        SLLNode<MapEntry<String ,String>> node = tabela.search(d_m);
        String s="";

        if (node==null){
            System.out.println("Nema vraboteni sho slavat toj den");
        }
        else {
            while (node!=null){
                String in = node.element.value;
                String informacii[] = in.split(" ");
                String Ime_Prezime = informacii[0] + " " + informacii[1];
                int godina = Integer.parseInt(informacii[2]);
//                s+= + "\n";

                System.out.println(Ime_Prezime + " " + (y-godina));
                node = node.succ;
            }
        }

    }
}
//class Person{
//    String ime;
//    String prezime;
//    int godina;
//
//    public Person(String ime, String prezime, int godina) {
//        this.ime = ime;
//        this.prezime = prezime;
//        this.godina = godina;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s %s ",ime,prezime);
//    }
//}