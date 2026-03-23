import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.*;

public class Main {

    public static class Item {
        private String name;
        private int quantity;
        private double price;

        public Item(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getQuantity() {
            return this.quantity;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }

        public double getTotalPrice() {
            return this.quantity * this.price;
        }
    }

    public static void main(String[] args) {
        run(new Scanner(System.in), System.out);
    }

    public static void run(Scanner scanner, PrintStream out) {
        List<Item> list = new ArrayList<>();


        out.println("Select a language: ");
        out.println("1. English");
        out.println("2. Suomi");
        out.println("3. 日本語");
        out.println("4. Svenska");

        int choice = scanner.nextInt();

        // Set the locale based on user's choice
        Locale locale = switch (choice) {
            case 1 -> new Locale("en", "UK");
            case 2 -> new Locale("fi", "FI");
            case 3 -> new Locale("ja", "JP");
            case 4 -> new Locale("sv", "SE");
            default -> {
                out.println("Invalid choice. Defaulting to English.");
                yield new Locale("en", "UK");
            }
        };

        // Load the resource bundle for the selected locale
        ResourceBundle rb = ResourceBundle.getBundle("MessagesBundle", locale);
        scanner.useLocale(locale);

        // STRING: Input the number of items you want to purchase
        out.println(rb.getString("prompt.item.count"));
        int numberOfItems = scanner.nextInt();

        for (int i = 1; i <= numberOfItems; i++) {
            // STRING: Input the name of the {0}. item
            out.println(MessageFormat.format(rb.getString("prompt.item.name"), i));
            String nameOfItem = scanner.next();

            Item item = new Item(nameOfItem);

            // STRING: Input the quantity of item ''{0}''
            out.println(MessageFormat.format(rb.getString("prompt.item.quantity"), nameOfItem));
            item.setQuantity(scanner.nextInt());

            // STRING: Input the price of item ''{0}''
            out.println(MessageFormat.format(rb.getString("prompt.item.price"), nameOfItem));
            item.setPrice(scanner.nextDouble());

            list.add(item);
        }

        // Calculate total cost of items
        double total = calculateTotal(list);

        // STRING: Total cost of all items
        out.println(rb.getString("prompt.item.total") + String.format("\n%.2f", total));

        scanner.close();
    }

    public static double calculateTotal(List<Item> items) {
        return items.stream()
                .reduce(0.0, (total, item) -> total + item.getTotalPrice(), Double::sum);
    }
}
