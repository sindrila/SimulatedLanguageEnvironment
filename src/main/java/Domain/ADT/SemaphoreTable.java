package Domain.ADT;

import Domain.Utilities.Wrappers.SemaphoreTableInterface;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class SemaphoreTable implements SemaphoreTableInterface {
    private HashMap<Integer, Pair<Integer, List<Integer>>> semaphoreTable;
    private int freeLocation;

    public SemaphoreTable() {
        this.semaphoreTable = new HashMap<>();
    }

    @Override
    public void put(int key, Pair<Integer, List<Integer>> value) throws ADTException {
        synchronized (this) {
            if (semaphoreTable.containsKey(key)) {
                throw new ADTException("SemaphoreTable.put(): table already contains the key" + key);
            }
            semaphoreTable.put(key, value);
        }
    }

    @Override
    public Pair<Integer, List<Integer>> get(int key) throws ADTException {
        synchronized (this) {
            if (semaphoreTable.containsKey(key)) {
                return semaphoreTable.get(key);
            }
            throw new ADTException("SemaphoreTable.put(): table does not contain given key.");
        }
    }

    @Override
    public boolean containsKey(int key) {
        synchronized (this) {
            return this.semaphoreTable.containsKey(key);
        }
    }

    @Override
    public int getFreeAddress() {
        synchronized (this) {
            return ++freeLocation;
        }
    }

    @Override
    public void setFreeAddress(int freeAddress) {
        synchronized (this) {
            this.freeLocation = freeAddress;
        }

    }

    @Override
    public void update(int key, Pair<Integer, List<Integer>> value) throws ADTException {
        synchronized (this) {
            if (!this.semaphoreTable.containsKey(key))
                throw new ADTException("SemaphoreTable.update(): table does not contain given key.");
            this.semaphoreTable.replace(key, value);
        }
    }

    @Override
    public void setSemaphoreTable(HashMap<Integer, Pair<Integer, List<Integer>>> givenSemaphoreTable) {
        synchronized (this) {
            this.semaphoreTable = givenSemaphoreTable;
        }
    }

    @Override
    public HashMap<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable() {
        synchronized (this) {
            return this.semaphoreTable;

        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (this) {
            return this.semaphoreTable.isEmpty();

        }


    }   @Override
    public String toString() {
        if (this.isEmpty()) {
            return "empty\n";
        }
        StringBuilder output = new StringBuilder();
        synchronized (this) {
            TreeMap<Integer, Pair<Integer, List<Integer>>> sorted = new TreeMap<>(this.semaphoreTable);
            for (Integer key : sorted.keySet()) {
                output.append(key.toString()).append(" --> ").append(this.semaphoreTable.get(key).toString()).append('\n');
            }
        }

        return output.toString();
    }
}
