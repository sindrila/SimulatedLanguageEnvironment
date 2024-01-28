package Domain.Utilities.Wrappers;
import Domain.ADT.ADTException;

import java.util.List;

public interface ListWrapper<T> {
    boolean isEmpty();
    int size();
    T get(int index) throws ADTException;
    void set(int index, T element) throws ADTException;
    void add(T element);
    void add(int index, T element) throws ADTException;
    T remove(int index) throws ADTException;
    List<T> toList();
}
