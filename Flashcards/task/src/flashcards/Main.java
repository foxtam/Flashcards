package flashcards;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Flashcards flashcards = new Flashcards();
        while (true) {
            System.out.println("Input the action (" + joinedActionNames() + "):");
            String input = scanner.nextLine().trim();
            Action action = Action.valueOf(input.toUpperCase(Locale.ROOT));

            if (action == Action.EXIT) break;
            else runCommand(action, flashcards);
        }
        System.out.println("Bye bye!");
    }

    private static String joinedActionNames() {
        StringBuilder builder = new StringBuilder();
        for (Action action : Action.values()) {
            builder.append(action.lowerName()).append(", ");
        }
        builder.delete(builder.length() - 2, builder.length());
        return builder.toString();
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
            default:
                throw new IllegalArgumentException();
        }
    }
}
