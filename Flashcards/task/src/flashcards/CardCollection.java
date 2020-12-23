package flashcards;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class CardCollection implements Iterable<Card> {

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
}
