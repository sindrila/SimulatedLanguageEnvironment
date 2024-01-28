package Domain.ADT;
import Domain.Utilities.Wrappers.StackWrapper;

import java.util.ArrayDeque;
import java.util.Deque;

public class CustomStack<T> implements StackWrapper<T> {
    private final Deque<T> stack;

    public CustomStack() {
        this.stack = new ArrayDeque<>();
    }

    public Deque<T> getStack() {
        return stack;
    }

    public int getSize()
    {
        return this.stack.size();
    }

    @Override
    public T pop() throws ADTException {
        if (this.stack.isEmpty())
            throw new ADTException("CustomStack.pop(): No elements inserted.");
        return this.stack.pop();
    }

    @Override
    public void push(T element) throws ADTException {
        if (element == null)
            throw new ADTException("CustomStack.push(): Given element is null.");
        this.stack.addFirst(element);
    }

    @Override
    public Boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public String toString() {
        if (this.isEmpty())
            return "empty\n";

        StringBuilder output = new StringBuilder(); // TODO: is not thread-safe
        for (T elem : this.stack.reversed()) {
            output.insert(0, elem.toString() + '\n');
        }
        return output.toString();
    }
}
