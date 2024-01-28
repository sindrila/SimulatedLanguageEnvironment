package View.GUI;

import Domain.ProgramState;
import Domain.Statements.Statement;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;
import Domain.Utilities.Wrappers.HeapInterface;
import Domain.Utilities.Wrappers.SemaphoreTableInterface;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import Controller.Controller;
import Controller.ControllerException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProgramExecutorController {
    private Controller controller;

    @FXML
    private TextField numberOfProgramStatesTextField;

    @FXML
    private TableView<Pair<Integer, Value>> heapTableView;

    @FXML
    private TableColumn<Pair<Integer, Value>, Integer> heapAddressTableColumn;

    @FXML
    private TableColumn<Pair<Integer, Value>, String> heapValueTableColumn;

    @FXML
    private ListView<Integer> programStateIdentifiersListView;

    @FXML
    private ListView<String> outputListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private TableView<Pair<String, Value>> symbolTableView;
    @FXML
    private TableColumn<Pair<String, Value>, String> symbolTableVariableNameTableColumn;
    @FXML
    private TableColumn<Pair<String, Value>, String> symbolTableValueTableColumn;
    @FXML
    private ListView<String> executionStackListView;

    @FXML
    private TableView<Map.Entry<Integer, javafx.util.Pair<Integer, List<Integer>>>> semaphoreTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, javafx.util.Pair<Integer, List<Integer>>>, Integer> indexSemaphoreTableColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer> valueSemaphoreTableColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, javafx.util.Pair<Integer, List<Integer>>>, List<Integer>> listSemaphoreTableColumn;


    @FXML
    private Button runOneStepButton;

    public void setController(Controller givenController) {
        this.controller = givenController;
    }

    @FXML
    public void initialize() {
        programStateIdentifiersListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        heapAddressTableColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapValueTableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        symbolTableVariableNameTableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        symbolTableValueTableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));

        indexSemaphoreTableColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        valueSemaphoreTableColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue().getKey()).asObject());
        listSemaphoreTableColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue().getValue()));

    }

    @FXML
    private void changeProgramState(MouseEvent event) {
        populateExecutionStackListView();
        this.populateSymbolsTableView();

    }

    public ProgramState getCurrentProgramState() {
        if (this.controller.getProgramStates().isEmpty())
            return null;
        int currentId = programStateIdentifiersListView.getSelectionModel().getSelectedIndex();
        if (currentId == -1)
            return controller.getProgramStates().getFirst();
        return controller.getProgramStates().get(currentId);
    }
    public void populate() {
        this.populateHeapTableView();
        this.populateProgramStateIdentifiersListView();
        this.populateOutputListView();
        this.populateFileTableListView();
        this.populateSymbolsTableView();
        this.populateExecutionStackListView();
        populateSemaphoreTableView();
    }

    private void populateSemaphoreTableView() {
        ProgramState programState = getCurrentProgramState();
        SemaphoreTableInterface semaphoreTable = Objects.requireNonNull(programState).getSemaphoreTable();
        List<Map.Entry<Integer, Pair<Integer, List<Integer>>>> semaphoreList = new ArrayList<>(semaphoreTable.getSemaphoreTable().entrySet());
        semaphoreTableView.setItems(FXCollections.observableArrayList(semaphoreList));
        semaphoreTableView.refresh();
    }

    private void populateExecutionStackListView() {
        ProgramState programState = this.getCurrentProgramState();
        List<String> executionStackToString = new ArrayList<>();
        if (programState != null) {
            for (Statement statement : programState.getExecutionStack().getStack()) {
                executionStackToString.add(statement.toString());
            }
            executionStackListView.setItems(FXCollections.observableArrayList(executionStackToString));
        }
    }
    private void populateSymbolsTableView() {
        ProgramState programState = this.getCurrentProgramState();
        DictionaryWrapper<String, Value> symbolsTable = Objects.requireNonNull(programState).getSymbolsTable();
        ArrayList<Pair<String, Value>> symbolsTableEntries = new ArrayList<>();
        for (Map.Entry<String, Value> entry : symbolsTable.getDictionary().entrySet().stream().toList()) {
            symbolsTableEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        symbolTableView.setItems(FXCollections.observableArrayList(symbolsTableEntries));
    }
    private void populateFileTableListView() {
        ProgramState programState = getCurrentProgramState();
        List<String> files = new ArrayList<>(Objects.requireNonNull(programState).getFileTable().getDictionary().keySet());
        fileTableListView.setItems(FXCollections.observableArrayList(files));
    }
    private void populateOutputListView() {
        ProgramState programState = this.getCurrentProgramState();
        List<String> output = new ArrayList<>();
        List<Value> outputList = Objects.requireNonNull(programState).getOutputList().toList();
        for (Value value : outputList) {
            output.add(value.toString());
        }
        outputListView.setItems(FXCollections.observableArrayList(output));
    }


    public void populateHeapTableView() {
        ProgramState programState = this.getCurrentProgramState();
        HeapInterface<Integer, Value> heap = Objects.requireNonNull(programState).getHeap();
        ArrayList<Pair<Integer, Value>> heapEntries = new ArrayList<>();
        for (Map.Entry<Integer, Value> entry : heap.getAllPairs().entrySet()) {
            heapEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        heapTableView.setItems(FXCollections.observableArrayList(heapEntries));
    }

    private void populateNumberOfProgramStatesTextField() {
        int numberOfProgramStates = this.controller.getProgramStates().size();
        this.numberOfProgramStatesTextField.setText(String.valueOf(numberOfProgramStates));
    }

    private void populateProgramStateIdentifiersListView() {
        List<ProgramState> programStates = this.controller.getProgramStates();
        List<Integer> idList = programStates.stream().map(ProgramState::getId).toList();
        programStateIdentifiersListView.setItems(FXCollections.observableList(idList));
        populateNumberOfProgramStatesTextField();
    }


    @FXML
    private void runOneStep(MouseEvent event) {
        if (this.controller != null) {
            try {
                List<ProgramState> programStates = Objects.requireNonNull(controller.getProgramStates());
                if (!programStates.isEmpty()) {
                    this.controller.oneStepExecution();
                    this.populate();
                    programStates = controller.removeCompletedPrograms(this.controller.getProgramStates());
                    this.controller.setProgramStates(programStates);
                    this.populateProgramStateIdentifiersListView();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Execution information");
                    alert.setHeaderText("Execution has ended");
                    alert.setContentText("There are no more programs to execute");
                    alert.showAndWait();
                }
            } catch (ControllerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Execution error");
                alert.setHeaderText("An error occurred while executing the program");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }
}
