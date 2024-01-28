package Domain.Utilities.Wrappers;

import Domain.ADT.ADTException;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public interface SemaphoreTableInterface {
    void put(int key, Pair<Integer, List<Integer>> value) throws ADTException;
    Pair<Integer, List<Integer>> get(int key) throws ADTException;
    boolean containsKey(int key);
    int getFreeAddress();
    void setFreeAddress(int freeAddress);
    void update(int key, Pair<Integer, List<Integer>> value) throws ADTException;
    void setSemaphoreTable(HashMap<Integer, Pair<Integer, List<Integer>>> givenSemaphoreTable);
    HashMap<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable();
    boolean isEmpty();
}
