package View.CLI;

import Controller.Controller;
import Controller.ControllerException;

public class RunExampleCommand extends Command {
    private final Controller controller;

    RunExampleCommand(String givenKey, String givenDescription, Controller givenController) {
        super(givenKey, givenDescription);
        if (givenController == null)
            throw new NullPointerException("RunExampleCommand: givenController is null.");
        this.controller = givenController;
    }
    @Override
    public void execute() {
        try
        {
            controller.allStepExecution();
        } catch (ControllerException e) {
            System.out.println("runexamplecommand 21" + e.getMessage());
        }
    }
}
