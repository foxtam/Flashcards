package flashcards;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Flashcards flashcards = new Flashcards();
        while (true) {
            LoggedIO.out.println("Input the action (" + joinedActionNames() + "):");
            String input = LoggedIO.in.nextLine();
            Action action = Action.fromStringCommand(input);

            if (action == Action.EXIT)
                break;
            else
                runCommand(action, flashcards);
        }
        LoggedIO.out.println("Bye bye!");
    }

    private static String joinedActionNames() {
        ArrayList<String> commands = new ArrayList<>();
        for (Action action : Action.values()) {
            commands.add(action.stringCommand);
        }
        return String.join(", ", commands);
    }

    private static void runCommand(Action action, Flashcards flashcards) {
        switch (action) {
            case ADD:
                flashcards.commandAdd();
                break;
            case REMOVE:
                flashcards.commandRemove();
                break;
            case IMPORT:
                flashcards.commandImport();
                break;
            case EXPORT:
                flashcards.commandExport();
                break;
            case ASK:
                flashcards.commandAsk();
                break;
            case LOG:
                flashcards.commandLog();
                break;
            case HARDEST_CARD:
                flashcards.commandHardestCard();
                break;
            case RESET_STATS:
                flashcards.commandResetStats();
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
