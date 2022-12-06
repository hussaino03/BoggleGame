package command;

/**
 * The Command Interface forces the classes which implement the interface to override the execute() method to ensure
 * all such classes can polymorphically executed via the execute() method
 */
public interface Command {
    /**
     * Abstract method to be implemented by classes which implement the Command Interface
     */
    void execute();
}
