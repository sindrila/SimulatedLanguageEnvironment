package Repository;

import Domain.ADT.CustomList;
import Domain.ProgramState;

import java.util.List;

public interface Repository {
    void addProgramState(ProgramState givenProgramState);

    void logProgramState(ProgramState programState) throws RepositoryException;

    void clearLogFile() throws RepositoryException;

    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> givenProgramList);
}
