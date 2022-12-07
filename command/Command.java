package command;

/**
 * The Command Interface has an execute() method which is overriden by the classes that implement
 * the Command Interface.
 */
public interface Command {
    /**
     * Abstract method to be overriden by classes which implement the Command Interface
     */
    void execute();
}
