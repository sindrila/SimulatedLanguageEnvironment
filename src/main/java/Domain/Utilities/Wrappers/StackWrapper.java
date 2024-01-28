package Domain.Utilities.Wrappers;

import Domain.ADT.ADTException;

import java.util.Deque;

public interface StackWrapper<T> {
    T pop() throws ADTException;
    void push(T element) throws ADTException;
    Boolean isEmpty();
    public Deque<T> getStack();
}
