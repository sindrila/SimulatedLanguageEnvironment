package Domain;

import Controller.ControllerException;
import Domain.ADT.*;
import Domain.Statements.Statement;
import Domain.Statements.StatementException;
import Domain.Utilities.GeneralException;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.*;


public class ProgramState {
    private StackWrapper<Statement> executionStack;
    private DictionaryWrapper<String, Value> symbolsTable;
    private ListWrapper<Value> outputList;
    private HeapInterface<Integer, Value> heap;
    private final FileTableInterface fileTable;
    private Statement originalProgram;
    private final SemaphoreTableInterface  semaphoreTable;
    private final int id;
    private static int availableId = 0;

    private static int getAvailableId() {
        return ++availableId;
    }



    public ProgramState(StackWrapper<Statement> givenExecutionStack, DictionaryWrapper<String, Value> givenSymbolsTable, ListWrapper<Value> givenOutputList, FileTableInterface givenFileTable, HeapInterface<Integer, Value> givenHeap, SemaphoreTableInterface givenSemaphore) {
        if (givenExecutionStack == null)
            throw new NullPointerException("ProgramState: givenExecutionStack is null");
        if (givenSymbolsTable == null)
            throw new NullPointerException("ProgramState: givenSymbolsTable is null");
        if (givenOutputList == null)
            throw new NullPointerException("ProgramState: givenOutputList is null");
        if (givenFileTable == null)
            throw new NullPointerException("ProgramState: givenFileTable is null");
        if (givenSemaphore == null)
            throw new NullPointerException("ProgramState: givenSemaphore is null");
        this.executionStack = givenExecutionStack;
        this.symbolsTable = givenSymbolsTable;
        this.outputList = givenOutputList;
        this.originalProgram = null;
        this.fileTable = givenFileTable;
        this.heap = givenHeap;
        this.semaphoreTable = givenSemaphore;
        this.id = getAvailableId();
    }

    public ProgramState(StackWrapper<Statement> givenExecutionStack, DictionaryWrapper<String, Value> givenSymbolsTable, ListWrapper<Value> givenOutputList, FileTableInterface givenFileTable, HeapInterface<Integer, Value> givenHeap, Statement givenOriginalProgram, SemaphoreTableInterface givenSemaphore) {
        if (givenExecutionStack == null)
            throw new NullPointerException("ProgramState: givenExecutionStack is null");
        if (givenSymbolsTable == null)
            throw new NullPointerException("ProgramState: givenSymbolsTable is null");
        if (givenOutputList == null)
            throw new NullPointerException("ProgramState: givenOutputList is null");
        if (givenFileTable == null)
            throw new NullPointerException("ProgramState: givenFileTable is null");
        if (givenOriginalProgram == null)
            throw new NullPointerException("ProgramState: givenOriginalProgram is null");
        if (givenSemaphore == null)
            throw new NullPointerException("ProgramState: givenSemaphore is null");

        this.executionStack = givenExecutionStack;
        this.symbolsTable = givenSymbolsTable;
        this.outputList = givenOutputList;

        this.originalProgram = givenOriginalProgram.deepCopy();
        try {
            this.executionStack.push(givenOriginalProgram);
        } catch (ADTException e) {
            throw new RuntimeException(e);
        }
        this.fileTable = givenFileTable;
        this.heap = givenHeap;
        this.semaphoreTable = givenSemaphore;
        this.id = getAvailableId();
    }

    public int getId() {
        return this.id;
    }

    public Boolean isNotCompleted() {
        return !(this.executionStack.isEmpty());
    }

    public FileTableInterface getFileTable() {
        return fileTable;
    }


    public StackWrapper<Statement> getExecutionStack() {
        return this.executionStack;
    }

    public ListWrapper<Value> getOutputList() {
        return this.outputList;
    }

    public DictionaryWrapper<String, Value> getSymbolsTable() {
        return this.symbolsTable;
    }

    public HeapInterface<Integer, Value> getHeap() {
        return this.heap;
    }

    public SemaphoreTableInterface getSemaphoreTable() {
        return this.semaphoreTable;
    }

    public void setHeap(HeapInterface<Integer, Value> givenHeap) {
        this.heap = givenHeap;
    }

    public Statement getOriginalProgram() {
        if (this.originalProgram == null)
            throw new NullPointerException("ProgramState: originalProgram is null");
        return originalProgram;
    }

    public void setExecutionStack(CustomStack<Statement> executionStack) {
        this.executionStack = executionStack;
    }

    public void setSymbolsTable(CustomDictionary<String, Value> symbolsTable) {
        this.symbolsTable = symbolsTable;
    }

    public void setOutputList(CustomList<Value> outputList) {
        this.outputList = outputList;
    }

    public void setOriginalProgram(Statement originalProgram) {
        this.originalProgram = originalProgram;
    }


    public ProgramState oneStepExecution() throws GeneralException {
        try {
            Statement currentStatement = this.executionStack.pop();
            return currentStatement.execute(this);
        } catch (ADTException | StatementException e) {
            throw new GeneralException("ControllerException.oneStepExecution(): " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "ProgramState #" + this.id  + "\nHeap:\n" + heap + "ExecutionStack:\n" + executionStack + "SymbolsTable:\n" + symbolsTable + "OutputList:\n" + outputList + "FileTable:\n" + fileTable + "SemaphoreTable:\n" + semaphoreTable;
    }
}
