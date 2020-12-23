package flashcards;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;

public class Flashcards {

    static Scanner scanner = new Scanner(System.in);

    private final CardCollection cards = new CardCollection();

    public void commandAdd() {
        System.out.println("The card:");
        String term = scanner.nextLine();
        if (cards.hasCardWithTerm(term)) {
            System.out.printf("The card \"%s\" already exists.%n", term);
            return;
        }

        System.out.println("The definition of the card:");
        String definition = scanner.nextLine();
        if (cards.hasCardWithDefinition(definition)) {
            System.out.printf("The definition \"%s\" already exists.%n", definition);
            return;
        }

        cards.addCard(new Card(term, definition));
        System.out.printf("The pair (\"%s\":\"%s\") has been added.%n%n", term, definition);
    }

    public void commandRemove() {
        System.out.println("Which card?");
        String term = scanner.nextLine();
        if (cards.hasCardWithTerm(term)) {
            cards.removeCard(term);
            System.out.println("The card has been removed.\n");
        } else {
            System.out.printf("Can't remove \"%s\": there is no such card.%n%n", term);
        }
    }

    public void commandExport() {
        System.out.println("File name:");
        String fileName = scanner.nextLine();

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(cards.toString());
            System.out.println(cards.size() + " cards have been saved.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void commandImport() {
        System.out.println("File name:");
        String fileName = scanner.nextLine();

        try {
            tryImport(fileName);
        } catch (IOException e) {
            System.out.println("File not found.\n");
        }
    }

    private void tryImport(String fileName) throws IOException {
        String text = Files.readString(Path.of(fileName));
        String[] allCardsText = text.split(CardCollection.separator);
        for (String cardText : allCardsText) {
            addOrReplaceCard(cardText);
        }
        System.out.println(allCardsText.length + " cards have been loaded.\n");
    }

    private void addOrReplaceCard(String cardText) {
        String[] termDefinition = cardText.split(Card.separator);
        String term = termDefinition[0];
        String definition = termDefinition[1];
        if (cards.hasCardWithTerm(term)) cards.removeCard(term);
        cards.addCard(new Card(term, definition));
    }

    public void commandAsk() {
        System.out.println("How many times to ask?");
        int asksCount = Integer.parseInt(scanner.nextLine());
        askRandomQuestions(asksCount);
    }

    private void askRandomQuestions(int asksCount) {
        for (int i = 0; i < asksCount; i++) {
            Card card = cards.getRandomCard();
            System.out.printf("Print the definition of \"%s\":%n", card.getTerm());
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
                System.out.printf("Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".%n",
                        card.getDefinition(), optionalCard.get().getTerm());
            }
        } else {
            System.out.printf("Wrong. The right answer is \"%s\".%n", card.getDefinition());
        }
    }
}
