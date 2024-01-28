package Domain.ADT;

import Domain.Utilities.Wrappers.FileTableInterface;

import java.io.BufferedReader;
import java.util.HashMap;

public class FileTable extends CustomDictionary<String, BufferedReader> implements FileTableInterface {
    public FileTable() {
        super();
    }

    @Override
    public String toString() {
        HashMap<String, BufferedReader> dictionary = this.getDictionary();
        if (this.isEmpty())
            return "empty\n";
        StringBuilder output = new StringBuilder();
        for (String key : dictionary.keySet())
            output.insert(0, key + '\n');
        return output.toString();
    }

}


