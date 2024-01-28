package Domain.ADT;

import Domain.Utilities.Wrappers.ListWrapper;

import java.util.ArrayList;
import java.util.List;

public class CustomList<T> implements ListWrapper<T> {
    ArrayList<T> list;
    public CustomList() {
        this.list = new ArrayList<>();
    }
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public T get(int index) throws ADTException {
        if (index >= this.list.size())
            throw new ADTException("CustomList.get(): Index out of range.");
        return this.list.get(index);
    }

    @Override
    public void set(int index, T element) throws ADTException {
        if (index >= this.list.size())
            throw new ADTException("CustomList.set(): Index out of range.");
        this.list.add(index, element);
    }

    @Override
    public void add(T element) {
        this.list.add(element);
    }

    @Override
    public void add(int index, T element) throws ADTException {
        if (index > this.list.size())
            throw new ADTException("CustomList.add(): Index out of range.");
        this.list.add(index, element);
    }

    @Override
    public T remove(int index) throws ADTException {
        if (index >= this.list.size())
            throw new ADTException("CustomList.remove(): Index out of range.");
        return this.list.remove(index);
    }

    @Override
    public List<T> toList() {
        return list;
    }

    public ArrayList<T> getElements() {
        return this.list;
    }

    @Override
    public String toString(){
        if (this.isEmpty())
            return "empty\n";
        StringBuilder output = new StringBuilder();
        for (T element : this.list.reversed())
            output.insert(0, element.toString() + '\n');
        return output.toString();
    }
}
