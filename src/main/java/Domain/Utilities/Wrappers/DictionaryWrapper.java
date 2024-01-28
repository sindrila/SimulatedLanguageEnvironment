package Domain.Utilities.Wrappers;
import Domain.ADT.ADTException;

import java.util.Collection;
import java.util.HashMap;

public interface DictionaryWrapper<T,V> {
    boolean isEmpty();
    Collection<V> getAllElements();
    V get(T key) throws ADTException;
    void put(T key, V value) throws ADTException;
    void remove(T key) throws ADTException;
    boolean isDefined(T key);

    HashMap<T, V> getDictionary();

    void update(T key, V value) throws ADTException;

    DictionaryWrapper<T, V> deepCopy();
}
