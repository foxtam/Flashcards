package flashcards;

import java.util.Objects;

public class Card {
    public static String separator = "\n\n";
    private final String term;
    private final String definition;

    public Card(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(term, card.term) && Objects.equals(definition, card.definition);
    }

    @Override
    public String toString() {
        return term + separator + definition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(term, definition);
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }
}
