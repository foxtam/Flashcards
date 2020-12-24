package flashcards;

public enum Action {
    ADD("add"),
    REMOVE("remove"),
    IMPORT("import"),
    EXPORT("export"),
    ASK("ask"),
    EXIT("exit"),
    LOG("log"),
    HARDEST_CARD("hardest card"),
    RESET_STATS("reset stats");

    public final String stringCommand;

    Action(String stringCommand) {
        this.stringCommand = stringCommand;
    }

    public static Action fromStringCommand(String stringCommand) {
        for (Action action : values()) {
            if (action.stringCommand.equals(stringCommand))
                return action;
        }
        throw new IllegalArgumentException(stringCommand);
    }
}
