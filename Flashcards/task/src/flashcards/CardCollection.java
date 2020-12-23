package flashcards;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class CardCollection implements Iterable<Card> {

    public static final String separator = "\n\n\n";

    private final ArrayList<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    public boolean hasCardWithTerm(String term) {
        for (Card card : cards) {
            if (card.getTerm().equals(term)) return true;
        }
        return false;
    }

    public boolean hasCardWithDefinition(String definition) {
        return getCardWithDefinition(definition).isPresent();
    }

    public Optional<Card> getCardWithDefinition(String definition) {
        for (Card card : cards) {
            if (card.getDefinition().equals(definition)) return Optional.of(card);
        }
        return Optional.empty();
    }

    @NotNull
    @Override
    public Iterator<Card> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < cards.size();
            }

            @Override
            public Card next() {
                return cards.get(index++);
            }
        };
    }

    public void removeCard(String term) {
        cards.removeIf(c -> c.getTerm().equals(term));
    }

    @Override
    public String toString() {
        ArrayList<String> cardsAsStrings = new ArrayList<>();
        for (Card card : cards) {
            cardsAsStrings.add(card.toString());
        }
        return String.join(separator, cardsAsStrings);
    }

    public int size() {
        return cards.size();
    }

    public Card getRandomCard() {
        int randomIndex = (int) (Math.random() * cards.size());
        return cards.get(randomIndex);
    }
}
