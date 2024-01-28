package Domain.Utilities.Wrappers;

import java.util.Collection;
import java.util.HashMap;

public interface HeapInterface<T, V> extends DictionaryWrapper<T, V> {
    int getNewFreeLocation();
    void setContent(HashMap<T, V> newContent);
    HashMap<T, V> getAllPairs();

    Collection<V> getAllValues();


}
