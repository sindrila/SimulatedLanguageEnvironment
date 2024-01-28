package View.CLI;

public abstract class Command {
    private final String key, description;
    public Command(String givenKey, String givenDescription) {
        if (givenKey == null)
            throw new NullPointerException("Command: givenKey is null");
        if (givenDescription == null)
            throw new NullPointerException("Command: givenDescription is null");
        this.key = givenKey;
        this.description = givenDescription;
    }
    public abstract void execute();
    public String getKey() {
        return key;
    }
    public String getDescription() {
        return description;
    }
}
