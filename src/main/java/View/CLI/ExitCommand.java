package View.CLI;



public class ExitCommand extends Command {
    public ExitCommand(String givenKey, String givenDescription) {
        super(givenKey, givenDescription);
    }
    @Override
    public void execute() {
        System.exit(0);
    }
}
