package View.CLI;

import Controller.Controller;
import Domain.ADT.*;
import Domain.Expressions.*;
import Domain.ProgramState;
import Domain.Statements.*;
import Domain.Utilities.Types.BoolType;
import Domain.Utilities.Types.IntType;
import Domain.Utilities.Types.ReferenceType;
import Domain.Utilities.Values.*;
import Repository.InMemoryRepository;
import Repository.Repository;

public class Interpreter {

    private Statement createExample1Statement() {
        return new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
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

        return new CompoundStatement(a_IntegerDeclaration,
                new CompoundStatement(b_IntegerDeclaration,
                        new CompoundStatement(a_AssignmentStatement_TwoPlusThreeTimesFive,
                                new CompoundStatement(b_AssignmentStatement_a_PlusOne, printStatement_b))));
    }

    private Statement createExample3Statement() {
        VariableDeclarationStatement a_BooleanDeclaration = new VariableDeclarationStatement("a", new BoolType());
        VariableDeclarationStatement v_IntegerDeclaration = new VariableDeclarationStatement("v", new IntType());
        AssignmentStatement a_AssignmentStatement_True = new AssignmentStatement("a", new ValueExpression(new BoolValue(Boolean.TRUE)));
        AssignmentStatement v_AssignmentStatement_2 = new AssignmentStatement("v", new ValueExpression(new IntValue(2)));
        AssignmentStatement v_AssignmentStatement_3 = new AssignmentStatement("v", new ValueExpression(new IntValue(3)));
        IfStatement if_a_vIs2_else_vIs3 = new IfStatement(new VariableExpression("a"), v_AssignmentStatement_2, v_AssignmentStatement_3);
        PrintStatement printStatement_v = new PrintStatement(new VariableExpression("v"));
        return new CompoundStatement(a_BooleanDeclaration,
                new CompoundStatement(v_IntegerDeclaration,
                        new CompoundStatement(a_AssignmentStatement_True,
                                new CompoundStatement(if_a_vIs2_else_vIs3, printStatement_v))));
    }

    private Statement createExample4Statement() {
        VariableDeclarationStatement x_IntegerDeclaration = new VariableDeclarationStatement("x", new IntType());
        AssignmentStatement x_AssignmentStatement_10 = new AssignmentStatement("x", new ValueExpression(new IntValue(10)));
        VariableDeclarationStatement y_IntegerDeclaration = new VariableDeclarationStatement("y", new IntType());
        AssignmentStatement y_AssignmentStatement_20 = new AssignmentStatement("y", new ValueExpression(new IntValue(20)));
        RelationalExpression x_less_than_y = new RelationalExpression(new VariableExpression("x"), new VariableExpression("y"), RelationalOperation.LESS_THAN);
        PrintStatement printStatement_x_less_than_y = new PrintStatement(x_less_than_y);

        return new CompoundStatement(x_IntegerDeclaration,
                new CompoundStatement(x_AssignmentStatement_10,
                        new CompoundStatement(y_IntegerDeclaration,
                                new CompoundStatement(y_AssignmentStatement_20, printStatement_x_less_than_y))));
    }

    private Statement createExample5Statement() {
        StringValue filePath = new StringValue("/Users/Alex/Documents/Facultate/Semestrul III/Advanced Methods of Programming/ToyLanguageGUI/src/main/java/LogFiles/twoValidValuesInputFile.in");
        ValueExpression filePathExpression = new ValueExpression(filePath);
        OpenReadFileStatement openReadFileStatement = new OpenReadFileStatement(filePathExpression);
        StringValue intVariableName = new StringValue("testIntVariable");
        VariableDeclarationStatement intVariableDeclarationStatement = new VariableDeclarationStatement(intVariableName.toString(), new IntType());
        ReadFileStatement readFileStatement = new ReadFileStatement(filePathExpression, intVariableName);
        PrintStatement printStatement1 = new PrintStatement(new VariableExpression(intVariableName.toString()));
        ReadFileStatement readFileStatement2 = new ReadFileStatement(filePathExpression, intVariableName);
        PrintStatement printStatement2 = new PrintStatement(new VariableExpression(intVariableName.toString()));
        CloseReadFileStatement closeReadFileStatement = new CloseReadFileStatement(filePathExpression);

        return new CompoundStatement(openReadFileStatement,
                new CompoundStatement(intVariableDeclarationStatement,
                        new CompoundStatement(readFileStatement,
                                new CompoundStatement(printStatement1,
                                        new CompoundStatement(readFileStatement2,
                                                new CompoundStatement(printStatement2, closeReadFileStatement))))));
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

        return new CompoundStatement(v_IntegerReferenceStatement,
                new CompoundStatement(v_HeapAllocation,
                        new CompoundStatement(a_IntegerReferenceReferenceStatement,
                                new CompoundStatement(a_HeapAllocation,
                                        new CompoundStatement(v_PrintStatement, a_PrintStatement)))));
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
        return new CompoundStatement(v_IntegerReferenceDeclarationStatement,
                new CompoundStatement(v_HeapAllocationStatement,
                        new CompoundStatement(a_IntegerReferenceReferenceDeclarationStatement,
                                new CompoundStatement(a_HeapAllocationStatement,
                                        new CompoundStatement(v_HeapValuePrintStatement, a_ReferenceHeapValuePrintStatement)))));
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

        return new CompoundStatement(v_IntegerReferenceDeclarationStatement,
                new CompoundStatement(v_HeapAllocationStatement,
                        new CompoundStatement(v_HeapValuePrintStatement,
                                new CompoundStatement(v_Gets30, v_HeapValuePlus5PrintStatement))));

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
        return new CompoundStatement(v_IntegerReferenceStatement,
                new CompoundStatement(v_HeapAllocation,
                        new CompoundStatement(a_IntegerReferenceReferenceStatement,
                                new CompoundStatement(a_HeapAllocation,
                                        new CompoundStatement(v_NewHeapAllocation30Statement,
                                                new CompoundStatement(b_IntegerReferenceReferenceStatement,
                                                        new CompoundStatement(b_HeapAllocation, b_ReferenceHeapValuePrintStatement)))))));
    }

    Statement createExample10Statement() {
        VariableDeclarationStatement v_IntegerDeclaration = new VariableDeclarationStatement("v", new IntType());
        AssignmentStatement v_Gets4 = new AssignmentStatement("v", new ValueExpression(new IntValue(4)));
        RelationalExpression v_GreaterThan0 = new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), RelationalOperation.GREATER_THAN);
        PrintStatement print_v = new PrintStatement(new VariableExpression("v"));
        ArithmeticExpression v_Minus1 = new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), ArithmeticOperation.SUBTRACTION);
        AssignmentStatement v_GetsDecremented = new AssignmentStatement("v", v_Minus1);
        CompoundStatement while_body = new CompoundStatement(print_v, v_GetsDecremented);
        WhileStatement while_vGreaterThan0 = new WhileStatement(v_GreaterThan0, while_body);
        PrintStatement print_v2 = new PrintStatement(new VariableExpression("v"));
        return new CompoundStatement(v_IntegerDeclaration,
                new CompoundStatement(v_Gets4,
                        new CompoundStatement(while_vGreaterThan0, print_v2)));
    }

    Statement createExample11Statement() {
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
        return new CompoundStatement(v_IntegerDeclaration,
                new CompoundStatement(a_IntegerReferenceStatement,
                        new CompoundStatement(v_Gets10,
                                new CompoundStatement(a_HeapAllocation,
                                        new CompoundStatement(forkStatement,
                                                new CompoundStatement(print_v2, print_readHeap_a2))))));
    }

    public Statement badTypeCheckStatement() {
        return new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new BoolValue(Boolean.TRUE))),
                        new PrintStatement(new VariableExpression("v"))));
    }

    public Boolean typeCheckStatement(Statement givenStatement) {
        try {
            givenStatement.typeCheck(new CustomDictionary<>());
            return true;
        } catch (StatementException e) {
            System.out.println("Interpreter.typeCheckStatement(): " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter();
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        Statement example1 = interpreter.createExample1Statement();
        if (interpreter.typeCheckStatement(example1)) {
            ProgramState programState1 = interpreter.createProgramState(example1);
            Repository repository1 = new InMemoryRepository(programState1, "/Users/Alex/Documents/Facultate/Semestrul III/Advanced Methods of Programming/ToyLanguageGUI/src/main/java/LogFiles/log1.txt");
            Controller controller1 = new Controller(repository1);
            menu.addCommand(new RunExampleCommand("1", example1.toString(), controller1));
        }
        Statement example2 = interpreter.createExample2Statement();
        if (interpreter.typeCheckStatement(example2)) {
            ProgramState programState2 = interpreter.createProgramState(example2);
            Repository repository2 = new InMemoryRepository(programState2, "/Users/Alex/Documents/Facultate/Semestrul III/Advanced Methods of Programming/ToyLanguageGUI/src/main/java/LogFiles/log2.txt");
            Controller controller2 = new Controller(repository2);
            menu.addCommand(new RunExampleCommand("2", example2.toString(), controller2));
        }
        Statement example3 = interpreter.createExample3Statement();
        if (interpreter.typeCheckStatement(example3)) {
            ProgramState programState3 = interpreter.createProgramState(example3);
            Repository repository3 = new InMemoryRepository(programState3, "/Users/Alex/Documents/Facultate/Semestrul III/Advanced Methods of Programming/ToyLanguageGUI/src/main/java/LogFiles/log3.txt");
            Controller controller3 = new Controller(repository3);
            menu.addCommand(new RunExampleCommand("3", example3.toString(), controller3));
        }
        Statement example4 = interpreter.createExample4Statement();
        if (interpreter.typeCheckStatement(example4)) {
            ProgramState programState4 = interpreter.createProgramState(example4);
            Repository repository4 = new InMemoryRepository(programState4, "/Users/Alex/Documents/Facultate/Semestrul III/Advanced Methods of Programming/ToyLanguageGUI/src/main/java/LogFiles/log4.txt");
            Controller controller4 = new Controller(repository4);
            menu.addCommand(new RunExampleCommand("4", example4.toString(), controller4));
        }
        Statement example5 = interpreter.createExample5Statement();
        if (interpreter.typeCheckStatement(example5)) {
            ProgramState programState5 = interpreter.createProgramState(example5);
            Repository repository5 = new InMemoryRepository(programState5, "/Users/Alex/Documents/Facultate/Semestrul III/Advanced Methods of Programming/ToyLanguageGUI/src/main/java/LogFiles/log5.txt");
            Controller controller5 = new Controller(repository5);
            menu.addCommand(new RunExampleCommand("5", example5.toString(), controller5));
        }
        Statement example6 = interpreter.createExample6Statement();
        if (interpreter.typeCheckStatement(example6)) {
            ProgramState programState6 = interpreter.createProgramState(example6);
            Repository repository6 = new InMemoryRepository(programState6, "/Users/Alex/Documents/Facultate/Semestrul III/Advanced Methods of Programming/ToyLanguageGUI/src/main/java/LogFiles/log6.txt");
            Controller controller6 = new Controller(repository6);
            menu.addCommand(new RunExampleCommand("6", example6.toString(), controller6));
        }
        Statement example7 = interpreter.createExample7Statement();
        if (interpreter.typeCheckStatement(example7)) {
            ProgramState programState7 = interpreter.createProgramState(example7);
            Repository repository7 = new InMemoryRepository(programState7, "/Users/Alex/Documents/Facultate/Semestrul III/Advanced Methods of Programming/ToyLanguageGUI/src/main/java/LogFiles/log7.txt");
            Controller controller7 = new Controller(repository7);
            menu.addCommand(new RunExampleCommand("7", example7.toString(), controller7));
        }
        Statement example8 = interpreter.createExample8Statement();
        if (interpreter.typeCheckStatement(example8)) {
            ProgramState programState8 = interpreter.createProgramState(example8);
            Repository repository8 = new InMemoryRepository(programState8, "/Users/Alex/Documents/Facultate/Semestrul III/Advanced Methods of Programming/ToyLanguageGUI/src/main/java/LogFiles/log8.txt");
            Controller controller8 = new Controller(repository8);
            menu.addCommand(new RunExampleCommand("8", example8.toString(), controller8));
        }
        Statement example9 = interpreter.createExample9Statement();
        if (interpreter.typeCheckStatement(example9)) {
            ProgramState programState9 = interpreter.createProgramState(example9);
            Repository repository9 = new InMemoryRepository(programState9, "/Users/Alex/Documents/Facultate/Semestrul III/Advanced Methods of Programming/ToyLanguageGUI/src/main/java/LogFiles/log9.txt");
            Controller controller9 = new Controller(repository9);
            menu.addCommand(new RunExampleCommand("9", example9.toString(), controller9));
        }
        Statement example10 = interpreter.createExample10Statement();
        if (interpreter.typeCheckStatement(example10)) {
            ProgramState programState10 = interpreter.createProgramState(example10);
            Repository repository10 = new InMemoryRepository(programState10, "/Users/Alex/Documents/Facultate/Semestrul III/Advanced Methods of Programming/ToyLanguageGUI/src/main/java/LogFiles/log10.txt");
            Controller controller10 = new Controller(repository10);
            menu.addCommand(new RunExampleCommand("10", example10.toString(), controller10));
        }
        Statement example11 = interpreter.createExample11Statement();
        if (interpreter.typeCheckStatement(example11)) {
            ProgramState programState11 = interpreter.createProgramState(example11);
            Repository repository11 = new InMemoryRepository(programState11, "/Users/Alex/Documents/Facultate/Semestrul III/Advanced Methods of Programming/ToyLanguageGUI/src/main/java/LogFiles/log11.txt");
            Controller controller11 = new Controller(repository11);
            menu.addCommand(new RunExampleCommand("11", example11.toString(), controller11));
        }
        Statement badTypeCheckStatement = interpreter.badTypeCheckStatement();
        if (interpreter.typeCheckStatement(badTypeCheckStatement)) {
            ProgramState badTypeCheckProgramState = interpreter.createProgramState(badTypeCheckStatement);
            Repository badTypeCheckRepository = new InMemoryRepository(badTypeCheckProgramState, "/Users/Alex/Documents/Facultate/Semestrul III/Advanced Methods of Programming/ToyLanguageGUI/src/main/java/LogFiles/logBadTypeCheck.txt");
            Controller badTypeCheckController = new Controller(badTypeCheckRepository);
            menu.addCommand(new RunExampleCommand("12", badTypeCheckStatement.toString(), badTypeCheckController));
        }

        menu.show();
    }
}
