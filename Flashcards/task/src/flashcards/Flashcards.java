package flashcards;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class Flashcards {

    private final CardCollection cards = new CardCollection();

    public void commandAdd() {
        LoggedIO.out.println("The card:");
        String term = LoggedIO.in.nextLine();
        if (cards.hasCardWithTerm(term)) {
            LoggedIO.out.printf("The card \"%s\" already exists.%n", term);
            return;
        }

        LoggedIO.out.println("The definition of the card:");
        String definition = LoggedIO.in.nextLine();
        if (cards.hasCardWithDefinition(definition)) {
            LoggedIO.out.printf("The definition \"%s\" already exists.%n", definition);
            return;
        }

        cards.addCard(new Card(term, definition));
        LoggedIO.out.printf("The pair (\"%s\":\"%s\") has been added.%n%n", term, definition);
    }

    public void commandRemove() {
        LoggedIO.out.println("Which card?");
        String term = LoggedIO.in.nextLine();
        if (cards.hasCardWithTerm(term)) {
            cards.removeCard(term);
            LoggedIO.out.println("The card has been removed.\n");
        } else {
            LoggedIO.out.printf("Can't remove \"%s\": there is no such card.%n%n", term);
        }
    }

    public void commandExport() {
        LoggedIO.out.println("File name:");
        String fileName = LoggedIO.in.nextLine();
        exportTo(Path.of(fileName));
    }

    public void exportTo(Path fileName) {
        try (FileWriter writer = new FileWriter(fileName.toString())) {
            writer.write(cards.toString());
            LoggedIO.out.println(cards.size() + " cards have been saved.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void commandImport() {
        LoggedIO.out.println("File name:");
        String fileName = LoggedIO.in.nextLine();
        importFrom(Path.of(fileName));
    }

    public void importFrom(Path fileName) {
        try {
            tryImport(fileName);
        } catch (IOException e) {
            LoggedIO.out.println("File not found.\n");
        }
    }

    private void tryImport(Path fileName) throws IOException {
        String text = Files.readString(fileName);
        String[] allCardsText = text.split(CardCollection.separator);
        for (String cardText : allCardsText) {
            addOrReplaceCard(cardText);
        }
        LoggedIO.out.println(allCardsText.length + " cards have been loaded.\n");
    }

    private void addOrReplaceCard(String cardText) {
        String[] cardTextData = cardText.split(Card.separator);
        String term = cardTextData[0];
        String definition = cardTextData[1];
        int errorCount = Integer.parseInt(cardTextData[2]);
        if (cards.hasCardWithTerm(term)) {
            cards.removeCard(term);
        }
        cards.addCard(new Card(term, definition, errorCount));
    }

    public void commandAsk() {
        LoggedIO.out.println("How many times to ask?");
        int asksCount = Integer.parseInt(LoggedIO.in.nextLine());
        askRandomQuestions(asksCount);
    }

    private void askRandomQuestions(int asksCount) {
        for (int i = 0; i < asksCount; i++) {
            Card card = cards.getRandomCard();
            LoggedIO.out.printf("Print the definition of \"%s\":%n", card.getTerm());
            String userInput = LoggedIO.in.nextLine();
            checkAnswer(card, userInput);
        }
    }

    private void checkAnswer(Card card, String userDefinition) {
        Optional<Card> optionalCard = cards.getCardWithDefinition(userDefinition);
        if (optionalCard.isPresent()) {
            if (optionalCard.get().equals(card)) {
                LoggedIO.out.println("Correct!");
            } else {
                card.incrementErrorCounter();
                LoggedIO.out.printf("Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".%n",
                        card.getDefinition(), optionalCard.get().getTerm());
            }
        } else {
            card.incrementErrorCounter();
            LoggedIO.out.printf("Wrong. The right answer is \"%s\".%n", card.getDefinition());
        }
    }

    public void commandLog() {
        LoggedIO.out.println("File name:");
        String fileName = LoggedIO.in.nextLine();
        String text = LoggedIO.getLog();
        try {
            Files.writeString(Path.of(fileName), text);
            LoggedIO.out.println("The log has been saved.\n");
        } catch (IOException e) {
            LoggedIO.out.println("File not found.\n");
        }
    }

    public void commandHardestCard() {
        Collection<Card> errorCards = findCardsWithMaxErrors();
        if (errorCards.isEmpty()) {
            LoggedIO.out.println("There are no cards with errors.\n");
        } else if (errorCards.size() == 1) {
            Card card = errorCards.iterator().next();
            LoggedIO.out.printf("The hardest card is \"%s\". You have %d errors answering it%n%n",
                    card.getTerm(), card.getErrorCount());
        } else {
            ArrayList<String> terms = new ArrayList<>();
            for (Card card : errorCards) {
                terms.add(card.getTerm());
            }
            Card card = errorCards.iterator().next();
            LoggedIO.out.printf("The hardest cards are \"%s\". You have %d errors answering them%n%n",
                    String.join("\", \"", terms), card.getErrorCount());
        }
    }

    private ArrayList<Card> findCardsWithMaxErrors() {
        int errorLevel = 1;
        ArrayList<Card> errorCards = new ArrayList<>();
        for (Card card : cards) {
            if (card.getErrorCount() == errorLevel) {
                errorCards.add(card);
            } else if (card.getErrorCount() > errorLevel) {
                errorCards.clear();
                errorCards.add(card);
                errorLevel = card.getErrorCount();
            }
        }
        return errorCards;
    }

    public void commandResetStats() {
        for (Card card : cards) {
            card.resetErrorCount();
        }
        LoggedIO.out.println("Card statistics have been reset.\n");
    }
}
