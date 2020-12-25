package flashcards;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        Optional<String> optionalImportFileName = getValueOfArg(args, "-import");
        Optional<String> optionalExportFileName = getValueOfArg(args, "-export");

        Flashcards flashcards = new Flashcards();
        optionalImportFileName.ifPresent(s -> flashcards.importFrom(Path.of(s)));

        mainLoop(flashcards);

        optionalExportFileName.ifPresent(s -> flashcards.exportTo(Path.of(s)));
        LoggedIO.out.println("Bye bye!");
    }

    private static Optional<String> getValueOfArg(String[] args, String arg) {
        int index = Arrays.asList(args).indexOf(arg);
        return index == -1 ? Optional.empty() : Optional.of(args[index + 1]);
    }

    private static void mainLoop(Flashcards flashcards) {
        while (true) {
            LoggedIO.out.println("Input the action (" + joinedActionNames() + "):");
            String input = LoggedIO.in.nextLine();
            Action action = Action.fromStringCommand(input);

            if (action == Action.EXIT) {
                break;
            } else {
                runCommand(action, flashcards);
            }
        }
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
