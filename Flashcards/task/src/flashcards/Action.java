package flashcards;

import java.util.Locale;

public enum Action {
    ADD,
    REMOVE,
    IMPORT,
    EXPORT,
    ASK,
    EXIT;

    public String lowerName() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
