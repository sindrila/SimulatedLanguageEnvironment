package Domain.ADT;
import Domain.Utilities.Wrappers.DictionaryWrapper;
import Domain.Utilities.Wrappers.HeapInterface;

import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;

public class Heap<T, V> implements HeapInterface<T, V> {
    private int newFreeLocation;
    private HashMap<T, V> dictionary;

    public Heap() {
        this.newFreeLocation = 0;
        this.dictionary = new HashMap<>();
    }
    @Override
    public int getNewFreeLocation() {

        return ++newFreeLocation;
    }

    @Override
    public void setContent(HashMap<T, V> newContent) {
        this.dictionary = newContent;
    }

    @Override
    public HashMap<T, V> getAllPairs() {
        return this.dictionary;
    }

    @Override
    public Collection<V> getAllValues() {
        return this.dictionary.values();
    }

    public HashMap<T, V> getDictionary() {
        return dictionary;
    }

    @Override
    public boolean isEmpty() {
        return this.dictionary.isEmpty();
    }

    @Override
    public Collection<V> getAllElements() {
        return this.dictionary.values();
    }

    @Override
    public V get(T key) throws ADTException {
        if (!this.dictionary.containsKey(key))
            throw new ADTException("CustomDictionary.get(): Variable " + key + " is not defined");
        return this.dictionary.get(key);
    }

    @Override
    public void put(T key, V value) throws ADTException {
        if (this.dictionary.containsKey(key))
            throw new ADTException("CustomDictionary.put(): Variable \"" + key + "\" is already defined");
        this.dictionary.put(key, value);
    }

    @Override
    public void remove(T key) throws ADTException {
        if (!this.dictionary.containsKey(key))
            throw new ADTException("CustomDictionary.remove(): Variable \"" + key + "\" is not defined");
        this.dictionary.remove(key);
    }

    @Override
    public boolean isDefined(T key) {
        return this.dictionary.containsKey(key);
    }

    @Override
    public void update(T key, V value) throws ADTException {
        if (!(this.dictionary.containsKey(key)))
            throw new ADTException("CustomDictionary.update(): Variable \"" + key + "\" is not defined");
        this.dictionary.put(key, value);
    }

    @Override
    public DictionaryWrapper<T, V> deepCopy() {
        CustomDictionary<T, V> copy = new CustomDictionary<>();
        this.dictionary.forEach((key, value) -> {
            try {
                copy.put(key, value);
            } catch (ADTException e) {
                throw new RuntimeException(e);
            }
        });
        return copy;
    }

    @Override
    public String toString() {
        if (this.isEmpty())
            return "empty\n";
        StringBuilder output = new StringBuilder();
        for (T key : this.dictionary.keySet())
            output = new StringBuilder(output + key.toString() + " --> " + this.dictionary.get(key).toString() + '\n');
        return output.toString();
    }
}
