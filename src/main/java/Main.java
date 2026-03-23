import java.text.MessageFormat;
import java.util.*;

public class Main {

    private static class Item {
        public String name;
        public int quantity;
        public double price;
        public Item(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }
    }

    public static void main(String[] args) {
        List<Item> list = new ArrayList<>();


        System.out.println("Select a language: ");
        System.out.println("1. English");
        System.out.println("2. Suomi");
        System.out.println("3. 日本語");
        System.out.println("4. Svenska");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        // Set the locale based on user's choice
        Locale locale = switch (choice) {
            case 1 -> new Locale("en", "UK");
            case 2 -> new Locale("fi", "FI");
            case 3 -> new Locale("ja", "JP");
            case 4 -> new Locale("sv", "SE");
            default -> {
                System.out.println("Invalid choice. Defaulting to English.");
                yield new Locale("en", "UK");
            }
        };

        // Load the resource bundle for the selected locale
        ResourceBundle rb = ResourceBundle.getBundle("MessagesBundle", locale);

        // STRING: Input the number of items you want to purchase
        System.out.println(rb.getString("prompt.item.count"));
        int numberOfItems = scanner.nextInt();

        for (int i = 1; i <= numberOfItems; i++) {
            // STRING: Input the name of the {0}. item
            System.out.println(MessageFormat.format(rb.getString("prompt.item.name"), i));
            String nameOfItem = scanner.next();

            Item item = new Item(nameOfItem);

            // STRING: Input the quantity of item ''{0}''
            System.out.println(MessageFormat.format(rb.getString("prompt.item.quantity"), nameOfItem));
            item.quantity = scanner.nextInt();

            // STRING: Input the price of item ''{0}''
            System.out.println(MessageFormat.format(rb.getString("prompt.item.price"), nameOfItem));
            item.price = scanner.nextDouble();

            list.add(item);
        }

        // Calculate total cost of items
        double sum = list.stream()
                .reduce(0.0, (total, item) -> total + item.getPrice(), Double::sum);

        // STRING: Total cost of all items
        System.out.println(rb.getString("prompt.item.total") + String.format("\n%.2f", sum));

        scanner.close();
    }
}
