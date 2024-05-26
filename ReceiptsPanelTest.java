package GymApp;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import GymApp.MemberFormPanel;
import GymApp.MemberListPanel;
import GymApp.ReceiptsPanel;
import GymApp.DataStorage;
import GymApp.Member;
import GymApp.GymMembershipApp;

import javax.swing.*;


public class ReceiptsPanelTest {
    private ReceiptsPanel panel;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        // Setup necessary mock components or replace with real components
        panel = new ReceiptsPanel(null); // Assuming that the real app context is not needed for these tests
        System.setOut(new PrintStream(outContent)); // Capture system output
    }

    @Test
    void testDisplayReceiptWithValidMember() {
        // Set up a member with known details
        Member member = new Member("John", "Doe", "john.doe@example.com", "123 Elm St", "Premium");
        MembershipManager.getInstance().addMember(member); // Assuming addMember handles ID assignment

        // Simulate entering the member ID and clicking the view button
        panel.getMemberIdField().setText(String.valueOf(member.getId()));
        panel.getViewButton().doClick();

        // Check if the JTextArea contains the expected output
        String receiptText = panel.getReceiptArea().getText();
        assertTrue(receiptText.contains("John"));
        assertTrue(receiptText.contains("Doe"));
        assertTrue(receiptText.contains("Premium"));
    }

    @Test
    void testDisplayReceiptWithInvalidMember() {
        // Set an invalid member ID and simulate button click
        panel.getMemberIdField().setText("999"); // Assuming 999 is not a valid ID
        panel.getViewButton().doClick();

        // Wait for any GUI updates to complete, if necessary
        SwingUtilities.invokeLater(() -> {
            // Check if the JTextArea shows the error message
            String receiptText = panel.getReceiptArea().getText();
            System.out.println("Received text: " + receiptText);  // Debug output
            assertTrue(receiptText.contains("No member found with ID: 999"));
        });
    }
}
