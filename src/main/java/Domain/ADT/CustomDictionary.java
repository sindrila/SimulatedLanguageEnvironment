package Domain.ADT;

import Domain.Utilities.Wrappers.DictionaryWrapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;

public class CustomDictionary<T, V> implements DictionaryWrapper<T, V> {
    private final HashMap<T, V> dictionary;

    public CustomDictionary() {
        this.dictionary = new HashMap<>();
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
        if (this.isEmpty()) {
            return "empty\n";
        }
        StringBuilder output = new StringBuilder();
        TreeMap<T, V> sorted = new TreeMap<>(this.dictionary);
        for (T key : sorted.keySet()) {
            output.append(key.toString()).append(" --> ").append(this.dictionary.get(key).toString()).append('\n');
        }

        return output.toString();
    }
}
