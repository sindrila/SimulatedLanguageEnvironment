package View.CLI;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private final Map<String, Command> commands;
    public TextMenu() {
        commands = new LinkedHashMap<>();
    }

    public void addCommand(Command c) {
        commands.put(c.getKey(), c);
    }

    private void printMenu() {
        for (Command command : commands.values()) {
            String line = String.format("%4s : %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            this.printMenu();
            System.out.println("Input the option: ");
            System.out.print("> ");
            String key = scanner.nextLine();
            Command command = commands.get(key);
            if (command == null){
                System.out.println("Invalid Option");
                continue;
            }
            command.execute();
        }

    }
}
