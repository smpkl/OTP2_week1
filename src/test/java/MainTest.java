import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    // Helper to run the app with simulated input and capture output
    private String runWithInput(String input) {
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        Main.run(scanner, out);
        return baos.toString();
    }

    @Test
    void testEnglishLanguageSelection() {
        // 1 = English, 1 item, name=Apple, quantity=2, price=1.50
        String output = runWithInput("1\n1\nApple\n2\n1,50\n");
        assertTrue(output.contains("3,00"));
    }

    @Test
    void testTotalCalculationSingleItem() {
        List<Main.Item> items = new ArrayList<>();
        Main.Item item = new Main.Item("Apple");
        item.setQuantity(2);
        item.setPrice(1.50);
        items.add(item);

        double total = Main.calculateTotal(items);
        assertEquals(3.0, total, 0.001);
    }

    @Test
    void testTotalCalculationMultipleItems() {
        List<Main.Item> items = new ArrayList<>();

        Main.Item a = new Main.Item("Apple");
        a.setPrice(1.50);
        a.setQuantity(1);
        items.add(a);

        Main.Item b = new Main.Item("Banana");
        b.setPrice(2.00);
        b.setQuantity(1);
        items.add(b);

        assertEquals(3.50, Main.calculateTotal(items), 0.001);
    }

    @Test
    void testTotalCalculationEmptyList() {
        assertEquals(0.0, Main.calculateTotal(new ArrayList<>()), 0.001);
    }

    @Test
    void testInvalidLanguageDefaultsToEnglish() {
        String output = runWithInput("99\n1\nApple\n1\n1,00\n");
        assertTrue(output.contains("Invalid choice."));
    }
}