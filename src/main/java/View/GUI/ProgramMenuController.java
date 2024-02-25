package View.GUI;

import Controller.Controller;
import Domain.ADT.*;
import Domain.Expressions.*;
import Domain.ProgramState;
import Domain.Statements.*;
import Domain.Utilities.Types.BoolType;
import Domain.Utilities.Types.IntType;
import Domain.Utilities.Types.ReferenceType;
import Domain.Utilities.Values.*;
import Repository.Repository;
import View.CLI.RunExampleCommand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import Repository.InMemoryRepository;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ProgramMenuController {
    private ProgramExecutorController programExecutorController;
    private Stage secondaryStage;
    @FXML
    private ListView<Statement> programListView;

    @FXML
    private Button displayButton;

    @FXML
    public void initialize() {
        programListView.setItems(getAllStatements());
        programListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void setSecondaryStage(Stage secondaryStage) {
        this.secondaryStage = secondaryStage;
    }

    public void setProgramExecutorController(ProgramExecutorController givenProgramExecutorController) {
        this.programExecutorController = givenProgramExecutorController;
    }

    @FXML
    private void onDisplayButtonDisplayProgram(ActionEvent actionEvent) {
        Statement selectedStatement = programListView.getSelectionModel().getSelectedItem();
        if (selectedStatement == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No program selected");
            alert.setContentText("Please select a program from the list");
            alert.showAndWait();
        } else {
            int id = programListView.getSelectionModel().getSelectedIndex();
            if (this.typeCheckStatement(selectedStatement)) {
                ProgramState programState = this.createProgramState(selectedStatement);
                Repository repository = new InMemoryRepository(programState, "log" + (id + 1) + ".txt");
                Controller controller = new Controller(repository);
                programExecutorController.setController(controller);
                this.programExecutorController.populate();
                this.secondaryStage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Type check error");
                alert.setContentText("The selected program does not type check");
                alert.showAndWait();
            }
        }
    }

    private void exceptionAlert(Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(exception.getMessage());
        alert.showAndWait();
    }

    private Statement badTypeCheckStatement() {
        return new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new BoolValue(Boolean.TRUE))), new PrintStatement(new VariableExpression("v"))));
    }

    private Boolean typeCheckStatement(Statement givenStatement) {
        try {
            givenStatement.typeCheck(new CustomDictionary<>());
            return true;
        } catch (StatementException e) {
            exceptionAlert(e);
            return false;
        }
    }

    @FXML
    public ObservableList<Statement> getAllStatements() {
        List<Statement> allStatements = new ArrayList<>();
        Statement example1 = this.createExample1Statement();
        allStatements.add(example1);
        Statement example2 = this.createExample2Statement();
        allStatements.add(example2);
        Statement example3 = this.createExample3Statement();
        allStatements.add(example3);
        Statement example4 = this.createExample4Statement();
        allStatements.add(example4);
        Statement example5 = this.createExample5Statement();
        allStatements.add(example5);
        Statement example6 = this.createExample6Statement();
        allStatements.add(example6);
        Statement example7 = this.createExample7Statement();
        allStatements.add(example7);
        Statement example8 = this.createExample8Statement();
        allStatements.add(example8);
        Statement example9 = this.createExample9Statement();
        allStatements.add(example9);
        Statement example10 = this.createExample10Statement();
        allStatements.add(example10);
        Statement example11 = this.createExample11Statement();
        allStatements.add(example11);
        Statement example12 = this.createExample12Statement();
        allStatements.add(example12);
        Statement example13 = this.createExampleCountSemaphore();
        allStatements.add(example13);
        return FXCollections.observableArrayList(allStatements);
    }

    private Statement createExample1Statement() {
        return new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))), new PrintStatement(new VariableExpression("v"))));
    }


    private Statement createExample2Statement() {
        VariableDeclarationStatement a_IntegerDeclaration = new VariableDeclarationStatement("a", new IntType());
        VariableDeclarationStatement b_IntegerDeclaration = new VariableDeclarationStatement("b", new IntType());
        ArithmeticExpression threeTimesFive = new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), ArithmeticOperation.MULTIPLICATION);
        ArithmeticExpression twoPlusThreeTimesFive = new ArithmeticExpression(new ValueExpression(new IntValue(2)), threeTimesFive, ArithmeticOperation.ADDITION);
        AssignmentStatement a_AssignmentStatement_TwoPlusThreeTimesFive = new AssignmentStatement("a", twoPlusThreeTimesFive);
        ArithmeticExpression a_PlusOne = new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(1)), ArithmeticOperation.ADDITION);
        AssignmentStatement b_AssignmentStatement_a_PlusOne = new AssignmentStatement("b", a_PlusOne);
        PrintStatement printStatement_b = new PrintStatement(new VariableExpression("b"));

        return new CompoundStatement(a_IntegerDeclaration, new CompoundStatement(b_IntegerDeclaration, new CompoundStatement(a_AssignmentStatement_TwoPlusThreeTimesFive, new CompoundStatement(b_AssignmentStatement_a_PlusOne, printStatement_b))));
    }

    private Statement createExample3Statement() {
        VariableDeclarationStatement a_BooleanDeclaration = new VariableDeclarationStatement("a", new BoolType());
        VariableDeclarationStatement v_IntegerDeclaration = new VariableDeclarationStatement("v", new IntType());
        AssignmentStatement a_AssignmentStatement_True = new AssignmentStatement("a", new ValueExpression(new BoolValue(Boolean.TRUE)));
        AssignmentStatement v_AssignmentStatement_2 = new AssignmentStatement("v", new ValueExpression(new IntValue(2)));
        AssignmentStatement v_AssignmentStatement_3 = new AssignmentStatement("v", new ValueExpression(new IntValue(3)));
        IfStatement if_a_vIs2_else_vIs3 = new IfStatement(new VariableExpression("a"), v_AssignmentStatement_2, v_AssignmentStatement_3);
        PrintStatement printStatement_v = new PrintStatement(new VariableExpression("v"));
        return new CompoundStatement(a_BooleanDeclaration, new CompoundStatement(v_IntegerDeclaration, new CompoundStatement(a_AssignmentStatement_True, new CompoundStatement(if_a_vIs2_else_vIs3, printStatement_v))));
    }

    private Statement createExample4Statement() {
        VariableDeclarationStatement x_IntegerDeclaration = new VariableDeclarationStatement("x", new IntType());
        AssignmentStatement x_AssignmentStatement_10 = new AssignmentStatement("x", new ValueExpression(new IntValue(10)));
        VariableDeclarationStatement y_IntegerDeclaration = new VariableDeclarationStatement("y", new IntType());
        AssignmentStatement y_AssignmentStatement_20 = new AssignmentStatement("y", new ValueExpression(new IntValue(20)));
        RelationalExpression x_less_than_y = new RelationalExpression(new VariableExpression("x"), new VariableExpression("y"), RelationalOperation.LESS_THAN);
        PrintStatement printStatement_x_less_than_y = new PrintStatement(x_less_than_y);

        return new CompoundStatement(x_IntegerDeclaration, new CompoundStatement(x_AssignmentStatement_10, new CompoundStatement(y_IntegerDeclaration, new CompoundStatement(y_AssignmentStatement_20, printStatement_x_less_than_y))));
    }

    private Statement createExample5Statement() {
        StringValue filePath = new StringValue("src/main/java/LogFiles/twoValidValuesInputFile.in");
        ValueExpression filePathExpression = new ValueExpression(filePath);
        OpenReadFileStatement openReadFileStatement = new OpenReadFileStatement(filePathExpression);
        StringValue intVariableName = new StringValue("testIntVariable");
        VariableDeclarationStatement intVariableDeclarationStatement = new VariableDeclarationStatement(intVariableName.toString(), new IntType());
        ReadFileStatement readFileStatement = new ReadFileStatement(filePathExpression, intVariableName);
        PrintStatement printStatement1 = new PrintStatement(new VariableExpression(intVariableName.toString()));
        ReadFileStatement readFileStatement2 = new ReadFileStatement(filePathExpression, intVariableName);
        PrintStatement printStatement2 = new PrintStatement(new VariableExpression(intVariableName.toString()));
        CloseReadFileStatement closeReadFileStatement = new CloseReadFileStatement(filePathExpression);

        return new CompoundStatement(openReadFileStatement, new CompoundStatement(intVariableDeclarationStatement, new CompoundStatement(readFileStatement, new CompoundStatement(printStatement1, new CompoundStatement(readFileStatement2, new CompoundStatement(printStatement2, closeReadFileStatement))))));
    }

    private ProgramState createProgramState(Statement givenStatement) {
        return new ProgramState(new CustomStack<>(), new CustomDictionary<>(), new CustomList<>(), new FileTable(), new Heap<>(), givenStatement, new SemaphoreTable());
    }

    private Statement createExample6Statement() {
        VariableDeclarationStatement v_IntegerReferenceStatement = new VariableDeclarationStatement("v", new ReferenceType(new IntType()));
        HeapAllocationStatement v_HeapAllocation = new HeapAllocationStatement(v_IntegerReferenceStatement.getName(), new ValueExpression(new IntValue(20)));
        VariableDeclarationStatement a_IntegerReferenceReferenceStatement = new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType())));
        HeapAllocationStatement a_HeapAllocation = new HeapAllocationStatement(a_IntegerReferenceReferenceStatement.getName(), new VariableExpression(v_IntegerReferenceStatement.getName()));
        PrintStatement v_PrintStatement = new PrintStatement(new VariableExpression("v"));
        PrintStatement a_PrintStatement = new PrintStatement(new VariableExpression("a"));

        return new CompoundStatement(v_IntegerReferenceStatement, new CompoundStatement(v_HeapAllocation, new CompoundStatement(a_IntegerReferenceReferenceStatement, new CompoundStatement(a_HeapAllocation, new CompoundStatement(v_PrintStatement, a_PrintStatement)))));
    }

    private Statement createExample7Statement() {
        VariableDeclarationStatement v_IntegerReferenceDeclarationStatement = new VariableDeclarationStatement("v", new ReferenceType(new IntType()));
        HeapAllocationStatement v_HeapAllocationStatement = new HeapAllocationStatement(v_IntegerReferenceDeclarationStatement.getName(), new ValueExpression(new IntValue(20)));
        VariableDeclarationStatement a_IntegerReferenceReferenceDeclarationStatement = new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType())));
        HeapAllocationStatement a_HeapAllocationStatement = new HeapAllocationStatement(a_IntegerReferenceReferenceDeclarationStatement.getName(), new VariableExpression(v_IntegerReferenceDeclarationStatement.getName()));
        ReadHeapExpression v_HeapValue = new ReadHeapExpression(new VariableExpression("v"));
        PrintStatement v_HeapValuePrintStatement = new PrintStatement(v_HeapValue);
        ReadHeapExpression a_HeapValue = new ReadHeapExpression(new VariableExpression("a"));
        ReadHeapExpression a_ReferenceHeapValue = new ReadHeapExpression(a_HeapValue);
        ArithmeticExpression a_ReferenceHeapValuePlusFive = new ArithmeticExpression(a_ReferenceHeapValue, new ValueExpression(new IntValue(5)), ArithmeticOperation.ADDITION);
        PrintStatement a_ReferenceHeapValuePrintStatement = new PrintStatement(a_ReferenceHeapValuePlusFive);
        return new CompoundStatement(v_IntegerReferenceDeclarationStatement, new CompoundStatement(v_HeapAllocationStatement, new CompoundStatement(a_IntegerReferenceReferenceDeclarationStatement, new CompoundStatement(a_HeapAllocationStatement, new CompoundStatement(v_HeapValuePrintStatement, a_ReferenceHeapValuePrintStatement)))));
    }

    private Statement createExample8Statement() {
        VariableDeclarationStatement v_IntegerReferenceDeclarationStatement = new VariableDeclarationStatement("v", new ReferenceType(new IntType()));
        HeapAllocationStatement v_HeapAllocationStatement = new HeapAllocationStatement(v_IntegerReferenceDeclarationStatement.getName(), new ValueExpression(new IntValue(20)));
        ReadHeapExpression v_HeapValueExpression = new ReadHeapExpression(new VariableExpression("v"));
        PrintStatement v_HeapValuePrintStatement = new PrintStatement(v_HeapValueExpression);
        HeapWritingStatement v_Gets30 = new HeapWritingStatement(v_IntegerReferenceDeclarationStatement.getName(), new ValueExpression(new IntValue(30)));
        ReadHeapExpression v_NewHeapValueExpression = new ReadHeapExpression(new VariableExpression("v"));
        ArithmeticExpression v_HeapValuePlus5Expression = new ArithmeticExpression(v_NewHeapValueExpression, new ValueExpression(new IntValue(5)), ArithmeticOperation.ADDITION);
        PrintStatement v_HeapValuePlus5PrintStatement = new PrintStatement(v_HeapValuePlus5Expression);

        return new CompoundStatement(v_IntegerReferenceDeclarationStatement, new CompoundStatement(v_HeapAllocationStatement, new CompoundStatement(v_HeapValuePrintStatement, new CompoundStatement(v_Gets30, v_HeapValuePlus5PrintStatement))));

    }

    private Statement createExample9Statement() {
        VariableDeclarationStatement v_IntegerReferenceStatement = new VariableDeclarationStatement("v", new ReferenceType(new IntType()));
        HeapAllocationStatement v_HeapAllocation = new HeapAllocationStatement(v_IntegerReferenceStatement.getName(), new ValueExpression(new IntValue(20)));
        VariableDeclarationStatement a_IntegerReferenceReferenceStatement = new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType())));
        HeapAllocationStatement a_HeapAllocation = new HeapAllocationStatement(a_IntegerReferenceReferenceStatement.getName(), new VariableExpression(v_IntegerReferenceStatement.getName()));
        HeapAllocationStatement v_NewHeapAllocation30Statement = new HeapAllocationStatement(v_IntegerReferenceStatement.getName(), new ValueExpression(new IntValue(30)));

        VariableDeclarationStatement b_IntegerReferenceReferenceStatement = new VariableDeclarationStatement("b", new ReferenceType(new ReferenceType(new ReferenceType(new IntType()))));
        HeapAllocationStatement b_HeapAllocation = new HeapAllocationStatement(b_IntegerReferenceReferenceStatement.getName(), new VariableExpression(a_IntegerReferenceReferenceStatement.getName()));
        ReadHeapExpression b_HeapValueExpression = new ReadHeapExpression(new VariableExpression("b"));
        ReadHeapExpression b_ReferenceHeapValueExpression = new ReadHeapExpression(b_HeapValueExpression);
        ReadHeapExpression b_ReferenceReferenceHeapValueExpression = new ReadHeapExpression(b_ReferenceHeapValueExpression);

        PrintStatement b_ReferenceHeapValuePrintStatement = new PrintStatement(b_ReferenceReferenceHeapValueExpression);
        return new CompoundStatement(v_IntegerReferenceStatement, new CompoundStatement(v_HeapAllocation, new CompoundStatement(a_IntegerReferenceReferenceStatement, new CompoundStatement(a_HeapAllocation, new CompoundStatement(v_NewHeapAllocation30Statement, new CompoundStatement(b_IntegerReferenceReferenceStatement, new CompoundStatement(b_HeapAllocation, b_ReferenceHeapValuePrintStatement)))))));
    }

    private Statement createExample10Statement() {
        VariableDeclarationStatement v_IntegerDeclaration = new VariableDeclarationStatement("v", new IntType());
        AssignmentStatement v_Gets4 = new AssignmentStatement("v", new ValueExpression(new IntValue(4)));
        RelationalExpression v_GreaterThan0 = new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), RelationalOperation.GREATER_THAN);
        PrintStatement print_v = new PrintStatement(new VariableExpression("v"));
        ArithmeticExpression v_Minus1 = new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), ArithmeticOperation.SUBTRACTION);
        AssignmentStatement v_GetsDecremented = new AssignmentStatement("v", v_Minus1);
        CompoundStatement while_body = new CompoundStatement(print_v, v_GetsDecremented);
        WhileStatement while_vGreaterThan0 = new WhileStatement(v_GreaterThan0, while_body);
        PrintStatement print_v2 = new PrintStatement(new VariableExpression("v"));
        return new CompoundStatement(v_IntegerDeclaration, new CompoundStatement(v_Gets4, new CompoundStatement(while_vGreaterThan0, print_v2)));
    }

    private Statement createExample11Statement() {
        VariableDeclarationStatement v_IntegerDeclaration = new VariableDeclarationStatement("v", new IntType());
        VariableDeclarationStatement a_IntegerReferenceStatement = new VariableDeclarationStatement("a", new ReferenceType(new IntType()));
        AssignmentStatement v_Gets10 = new AssignmentStatement("v", new ValueExpression(new IntValue(10)));
        HeapAllocationStatement a_HeapAllocation = new HeapAllocationStatement("a", new ValueExpression(new IntValue(22)));

        HeapWritingStatement a_Gets30 = new HeapWritingStatement("a", new ValueExpression(new IntValue(30)));
        AssignmentStatement v_Gets32 = new AssignmentStatement("v", new ValueExpression(new IntValue(32)));
        PrintStatement print_v = new PrintStatement(new VariableExpression("v"));
        PrintStatement print_readHeap_a = new PrintStatement(new ReadHeapExpression(new VariableExpression("a")));
        CompoundStatement forkCompoundStatement = new CompoundStatement(a_Gets30, new CompoundStatement(v_Gets32, new CompoundStatement(print_v, print_readHeap_a)));

        ForkStatement forkStatement = new ForkStatement(forkCompoundStatement);
        PrintStatement print_v2 = new PrintStatement(new VariableExpression("v"));
        PrintStatement print_readHeap_a2 = new PrintStatement(new ReadHeapExpression(new VariableExpression("a")));
        return new CompoundStatement(v_IntegerDeclaration, new CompoundStatement(a_IntegerReferenceStatement, new CompoundStatement(v_Gets10, new CompoundStatement(a_HeapAllocation, new CompoundStatement(forkStatement, new CompoundStatement(print_v2, print_readHeap_a2))))));
    }

    private Statement createExample12Statement() {
        VariableDeclarationStatement a_IntegerDeclaration = new VariableDeclarationStatement("a", new IntType());
        VariableDeclarationStatement b_IntegerDeclaration = new VariableDeclarationStatement("b", new IntType());
        VariableDeclarationStatement c_IntVariableDeclaration = new VariableDeclarationStatement("c", new IntType());

        AssignmentStatement a_gets1 = new AssignmentStatement("a", new ValueExpression(new IntValue(1)));
        AssignmentStatement b_gets2 = new AssignmentStatement("b", new ValueExpression(new IntValue(2)));
        AssignmentStatement c_gets5 = new AssignmentStatement("c", new ValueExpression(new IntValue(5)));

        ArithmeticExpression a_times10 = new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(10)), ArithmeticOperation.MULTIPLICATION);
        ArithmeticExpression b_times_c = new ArithmeticExpression(new VariableExpression("b"), new VariableExpression("c"), ArithmeticOperation.MULTIPLICATION);
        ValueExpression intValue_10 = new ValueExpression(new IntValue(10));

        Statement statement1 = new CompoundStatement(new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b")));
        Statement statement2 = new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))), new PrintStatement(new ValueExpression(new IntValue(200))));
        Statement statement3 = new PrintStatement(new ValueExpression(new IntValue(300)));

        SwitchStatement switch_aTimes10 = new SwitchStatement(a_times10, b_times_c, intValue_10, statement1, statement2, statement3);

        PrintStatement print_300 = new PrintStatement(new ValueExpression(new IntValue(300)));

        return new CompoundStatement(a_IntegerDeclaration, new CompoundStatement(b_IntegerDeclaration, new CompoundStatement(c_IntVariableDeclaration,
                new CompoundStatement(a_gets1, new CompoundStatement(b_gets2, new CompoundStatement(c_gets5,
                        new CompoundStatement(switch_aTimes10, print_300)))))));
    }

    private Statement createExampleCountSemaphore() {
        VariableDeclarationStatement v1_IntegerReferenceDeclaration = new VariableDeclarationStatement("v1", new ReferenceType(new IntType()));
        VariableDeclarationStatement cnt_IntegerDeclaration = new VariableDeclarationStatement("cnt", new IntType());
        HeapAllocationStatement v1_HeapGets1 = new HeapAllocationStatement("v1", new ValueExpression(new IntValue(1)));
        CreateSemaphoreStatement semaphore_cnt_v1Value = new CreateSemaphoreStatement("cnt", new ReadHeapExpression(new VariableExpression("v1")));

        AcquireStatement acquire_cnt1 = new AcquireStatement("cnt");
        ReadHeapExpression v1_heapValue = new ReadHeapExpression(new VariableExpression("v1"));
        ArithmeticExpression v1times10 = new ArithmeticExpression(v1_heapValue, new ValueExpression(new IntValue(10)), ArithmeticOperation.MULTIPLICATION);
        HeapWritingStatement v1_gets_v1times10 = new HeapWritingStatement("v1", v1times10);
        ReadHeapExpression v1_newHeapValue = new ReadHeapExpression(new VariableExpression("v1"));
        PrintStatement print_v1NewHeapValue = new PrintStatement(v1_newHeapValue);
        ReleaseStatement release_cnt1 = new ReleaseStatement("cnt");

        ForkStatement fork_cnt1 = new ForkStatement(new CompoundStatement(acquire_cnt1, new CompoundStatement(v1_gets_v1times10, new CompoundStatement(print_v1NewHeapValue, release_cnt1))));

        AcquireStatement acquire_cnt2 = new AcquireStatement("cnt");
        ReadHeapExpression v1_heapValue2 = new ReadHeapExpression(new VariableExpression("v1"));
        ArithmeticExpression v1times10_2 = new ArithmeticExpression(v1_heapValue2, new ValueExpression(new IntValue(10)), ArithmeticOperation.MULTIPLICATION);
        HeapWritingStatement v1_gets_v1times10_2 = new HeapWritingStatement("v1", v1times10_2);
        ReadHeapExpression v1_newHeapValue2 = new ReadHeapExpression(new VariableExpression("v1"));
        ArithmeticExpression v1times2 = new ArithmeticExpression(v1_newHeapValue2, new ValueExpression(new IntValue(2)), ArithmeticOperation.MULTIPLICATION);
        HeapWritingStatement v1_gets_v1times2 = new HeapWritingStatement("v1", v1times2);
        ReadHeapExpression v1_newHeapValue3 = new ReadHeapExpression(new VariableExpression("v1"));
        PrintStatement print_v1NewHeapValue3 = new PrintStatement(v1_newHeapValue3);
        ReleaseStatement release_cnt2 = new ReleaseStatement("cnt");

        ForkStatement fork_cnt2 = new ForkStatement(new CompoundStatement(acquire_cnt2, new CompoundStatement(v1_gets_v1times10_2, new CompoundStatement(v1_gets_v1times2, new CompoundStatement(print_v1NewHeapValue3, release_cnt2)))));


        AcquireStatement acquire_cnt3 = new AcquireStatement("cnt");
        ArithmeticExpression v1_minus1 = new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(1)), ArithmeticOperation.SUBTRACTION);
        PrintStatement print_v1_minus1 = new PrintStatement(v1_minus1);
        ReleaseStatement release_cnt3 = new ReleaseStatement("cnt");

        return new CompoundStatement(v1_IntegerReferenceDeclaration, new CompoundStatement(cnt_IntegerDeclaration,
                new CompoundStatement(v1_HeapGets1, new CompoundStatement(semaphore_cnt_v1Value, new CompoundStatement(fork_cnt1, new CompoundStatement(
                        fork_cnt2, new CompoundStatement(acquire_cnt3, new CompoundStatement(print_v1_minus1, release_cnt3))))))));

    }

}
