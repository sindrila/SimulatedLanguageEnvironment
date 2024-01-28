package Controller;

import Domain.ADT.*;
import Domain.ProgramState;
import Domain.Utilities.GeneralException;
import Domain.Utilities.Types.ReferenceType;
import Domain.Utilities.Values.ReferenceValue;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.HeapInterface;
import Repository.Repository;
import Repository.RepositoryException;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Pair {
    final ProgramState first;
    final GeneralException second;

    public Pair(ProgramState first, GeneralException second) {
        this.first = first;
        this.second = second;
    }
}

public class Controller {
    private final Repository repository;
    ExecutorService executor;

    public Controller(Repository givenRepository) {
        if (givenRepository == null)
            throw new NullPointerException("Controller: givenRepository is null.");
        this.repository = givenRepository;

        try {
            this.repository.clearLogFile();
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }


    public List<ProgramState> removeCompletedPrograms(List<ProgramState> givenProgramList) {
        return givenProgramList.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
    }


    private Integer getNestedAddress(ReferenceValue currentValue, HeapInterface<Integer, Value> heap) throws ControllerException {
        if (currentValue.getLocationType() instanceof ReferenceType) {
            int address = currentValue.getAddress();
            ReferenceValue newReference;
            try {
                newReference = (ReferenceValue) heap.get(address);
            } catch (ADTException e) {
                throw new ControllerException("CRITICAL!: Failed to get value from heap with address: " + address);
            }
            return getNestedAddress(newReference, heap);
        }
        return currentValue.getAddress();
    }

    private HashMap<Integer, Value> getGarbageCollectedHeap(List<Integer> symbolsTableAddresses, HeapInterface<Integer, Value> heap) {
        List<Integer> referencedHeapAddresses = heap.getAllValues().stream()
                .filter(value -> value instanceof ReferenceValue)
                .map(value -> (ReferenceValue) value)
                .map(referenceValue -> {
                    try {
                        return getNestedAddress(referenceValue, heap);
                    } catch (ControllerException e) {
                        throw new RuntimeException(e); // critical exception which should not happen in any case
                    }
                })
                .toList();
        return (HashMap<Integer, Value>)heap.getAllPairs().entrySet().stream()
                .filter(element -> symbolsTableAddresses.contains(element.getKey()) || referencedHeapAddresses.contains(element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    private void conservativeGarbageCollector(List<ProgramState> programStateList) {
        List<Integer> symbolsTableAddresses = Objects.requireNonNull(programStateList.stream()
                        .map(program -> getAddressesFromSymbolsTable(program.getSymbolsTable().getAllElements()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .toList();
        programStateList.forEach(p -> p.getHeap().setContent(getGarbageCollectedHeap(symbolsTableAddresses, p.getHeap())));
    }


    List<Integer> getAddressesFromSymbolsTable(Collection<Value> symbolsTableValues) {
        return symbolsTableValues.stream()
                .filter(value -> value instanceof ReferenceValue)
                .map(referenceValue -> {
                    ReferenceValue value = (ReferenceValue) referenceValue;
                    return value.getAddress();
                })
                .toList();
    }

    public void oneStepExecutionForAllPrograms(List<ProgramState> programStateList) throws ControllerException{
        try {
            programStateList.forEach(programState -> {
                try {
                    this.repository.logProgramState(programState);
                } catch (RepositoryException e) {
                    System.out.println("ControllerException.allStepExecution(): " + e.getMessage());
                }
            });
            List<Callable<ProgramState>> callList = programStateList.stream()
                    .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStepExecution))
                    .collect(Collectors.toList());

            List<Pair> newProgramList;
            newProgramList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
//                            System.out.println();
                            return new Pair(future.get(), null);
                        } catch (ExecutionException | InterruptedException e) {
                            if (e.getCause() instanceof GeneralException)
                                return new Pair(null, (GeneralException) e.getCause());
                            throw new RuntimeException(e);
//                            System.out.println("controller 126" + e.getMessage());
//                            return null;
                        }
                    }).filter(Objects::nonNull)
                    .filter(pair -> pair.first != null || pair.second != null)
                    .toList();

            for (Pair error: newProgramList)
                if (error.second != null)
                    throw new ControllerException("Controller.oneStepExecutionForAllPrograms(): " + error.second.getMessage());
            programStateList.addAll(newProgramList.stream().map(pair -> pair.first).toList());
//            programStateList.forEach(program -> {
//                try {
//                    this.repository.logProgramState(program);
//                } catch (RepositoryException e) {
//                    System.out.println("ControllerException.allStepExecution(): " + e.getMessage());
//                }
//            });
            repository.setProgramList(programStateList);
        }
        catch (InterruptedException e)
        {
            throw new ControllerException("ControllerException.allStepExecution(): " + e.getMessage());
        }
    }
    public void setProgramStates(List<ProgramState> givenProgramStates) {
        this.repository.setProgramList(givenProgramStates);
    }
    public void allStepExecution() throws ControllerException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList = this.removeCompletedPrograms(this.repository.getProgramList());
        try {
            this.repository.clearLogFile();
        } catch (RepositoryException e) {
            throw new ControllerException("ControllerException.allStepExecution(): " + e.getMessage());
        }
        while (!(programStateList.isEmpty()))
        {
            oneStepExecutionForAllPrograms(programStateList);
            this.conservativeGarbageCollector(programStateList);
            programStateList = removeCompletedPrograms(repository.getProgramList());
        }
        executor.shutdownNow();
        this.repository.setProgramList(programStateList);
    }
    public List<ProgramState> getProgramStates() {
        return this.repository.getProgramList();
    }

    public void oneStepExecution() throws ControllerException {
        this.executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStates = this.removeCompletedPrograms(this.repository.getProgramList());
        oneStepExecutionForAllPrograms(programStates);
        this.conservativeGarbageCollector(programStates);
        executor.shutdownNow();
    }
}
