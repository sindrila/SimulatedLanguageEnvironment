package Repository;

import Domain.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class InMemoryRepository implements Repository {
    private List<ProgramState> programs;
    private ProgramState currentProgramState;
    private String logFilePath;

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public InMemoryRepository(String logFilePath) {
        this.programs = new ArrayList<>();
        this.logFilePath = logFilePath;
        currentProgramState = null;
    }public InMemoryRepository(ProgramState givenProgramState, String logFilePath) {
        this.programs = new ArrayList<>();
        this.logFilePath = logFilePath;
        this.addProgramState(givenProgramState);
    }


    public void addProgramState(ProgramState givenProgramState) {
        if (givenProgramState == null)
            throw new NullPointerException("InMemoryRepository.addProgramState(): givenProgramState is null.");
        if (currentProgramState == null)
            this.currentProgramState = givenProgramState;
        this.programs.add(givenProgramState);
    }
    public void clearLogFile() throws RepositoryException {
        try {
            new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, false))).close();
        } catch (IOException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public List<ProgramState> getProgramList() {
        return this.programs;
    }

    @Override
    public void setProgramList(List<ProgramState> givenProgramList) {
        this.programs = givenProgramList;
    }

    @Override
    public void logProgramState(ProgramState programState) throws RepositoryException {

        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)))) {
            logFile.println(programState);
            logFile.println();
        } catch (IOException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
