package flashcards;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int cardNumber = readCardNumber();
        CardCollection cards = readCards(cardNumber);
        askQuestions(cards);
    }

    private static int readCardNumber() {
        System.out.println("Input the number of cards:");
        return Integer.parseInt(scanner.nextLine());
    }

    private static CardCollection readCards(int cardNumber) {
        CardCollection cards = new CardCollection();
        for (int i = 1; i <= cardNumber; i++) {
            String term = readTerm(i, cards);
            String definition = readDefinition(i, cards);
            cards.addCard(new Card(term, definition));
        }
        return cards;
    }

    private static void askQuestions(CardCollection cards) {
        for (Card card : cards) {
            System.out.println("Print the definition of \"" + card.getTerm() + "\":");
            String userInput = scanner.nextLine();
            checkAnswer(card, userInput, cards);
        }
    }

    private static String readTerm(int cardNumber, CardCollection cards) {
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

    private static String readDefinition(int cardNumber, CardCollection cards) {
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

    private static void checkAnswer(Card card, String userDefinition, CardCollection cards) {
        Optional<Card> maybeCard = cards.getCardWithDefinition(userDefinition);
        if (maybeCard.isPresent()) {
            if (maybeCard.get().equals(card)) {
                System.out.println("Correct!");
            } else {
                System.out.println("Wrong. The right answer is \"" + card.getDefinition() +
                        "\", but your definition is correct for \"" + maybeCard.get().getTerm() + "\".");
            }
        } else {
            System.out.println("Wrong. The right answer is \"" + card.getDefinition() + "\".");
        }
    }
}
