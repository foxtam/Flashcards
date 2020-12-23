package flashcards;

import java.util.Optional;
import java.util.Scanner;

public class Flashcards {
    static Scanner scanner = new Scanner(System.in);

    private CardCollection cards = new CardCollection();

    public void readCards() {
        int cardCount = readCardNumber();
        CardCollection cards = new CardCollection();
        for (int i = 1; i <= cardCount; i++) {
            String term = readTerm(i);
            String definition = readDefinition(i);
            cards.addCard(new Card(term, definition));
        }
        this.cards = cards;
    }

    private int readCardNumber() {
        System.out.println("Input the number of cards:");
        return Integer.parseInt(scanner.nextLine());
    }

    private String readTerm(int cardNumber) {
        System.out.println("Card #" + cardNumber + ":");
        String term;
        while (true) {
            term = scanner.nextLine();
            if (cards.hasCardWithTerm(term)) {
                System.out.println("The term \"" + term + "\" already exists. Try again:");
            } else break;
        }
        return term;
    }

    private String readDefinition(int cardNumber) {
        System.out.println("The definition for card #" + cardNumber + ":");
        String definition;
        while (true) {
            definition = scanner.nextLine();
            if (cards.hasCardWithDefinition(definition)) {
                System.out.println("The definition \"" + definition + "\" already exists. Try again:");
            } else break;
        }
        return definition;
    }

    public void askQuestions() {
        for (Card card : cards) {
            System.out.println("Print the definition of \"" + card.getTerm() + "\":");
            String userInput = scanner.nextLine();
            checkAnswer(card, userInput);
        }
    }

    private void checkAnswer(Card card, String userDefinition) {
        Optional<Card> optionalCard = cards.getCardWithDefinition(userDefinition);
        if (optionalCard.isPresent()) {
            if (optionalCard.get().equals(card)) {
                System.out.println("Correct!");
            } else {
                System.out.println("Wrong. The right answer is \"" + card.getDefinition() +
                        "\", but your definition is correct for \"" + optionalCard.get().getTerm() + "\".");
            }
        } else {
            System.out.println("Wrong. The right answer is \"" + card.getDefinition() + "\".");
        }
    }
}
