package GymApp;
import javax.swing.*;
import java.awt.*;

public class ReceiptsPanel extends JPanel {

    private JTextArea receiptArea;
    JTextField memberIdField;
    private JButton viewButton;

    // Assuming getters are added like this, :
    public JTextArea getReceiptArea() {
        return receiptArea;
    }

    public JTextField getMemberIdField() {
        return memberIdField;
    }

    public JButton getViewButton() {
        return viewButton;
    }

    public ReceiptsPanel(GymMembershipApp mainApp) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        memberIdField = new JTextField(10);
        viewButton = new JButton("Display Receipt");

        inputPanel.add(new JLabel("Enter Member ID:"));
        inputPanel.add(memberIdField);
        inputPanel.add(viewButton);

        // Text area for displaying the receipt
        receiptArea = new JTextArea(10, 40);
        receiptArea.setEditable(false);

        // Add components to the main panel
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(receiptArea), BorderLayout.CENTER);

        // Action listener for the button
        viewButton.addActionListener(e -> displayReceiptById(mainApp));
    }

    private void displayReceiptById(GymMembershipApp mainApp) {
        try {
            int memberId = Integer.parseInt(memberIdField.getText().trim());
            Member member = MembershipManager.getInstance().getMemberById(memberId);
            if (member != null) {
                displayReceipt(member);
            } else {
                JOptionPane.showMessageDialog(this, "No member found with ID: " + memberId, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for Member ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void displayReceipt(Member member) { //where should I need to call this one?
        StringBuilder receipt = new StringBuilder();
        receipt.append(String.format("Statement for %s for %s %s - NEW %s Membership\n",
                member.getRegistrationDate().getMonth(),
                member.getName(), member.getLastName(),
                member.getMembershipGrade()));
        receipt.append("+++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        receipt.append(String.format("Standard Member Enrollment\nDate: %s\n", member.getRegistrationDate()));
        receipt.append("Purchases:\n");
        receipt.append(String.format("  1: Annual Membership: £%.2f\n", member.getMembershipFee()));
        receipt.append(String.format("  2: Journal Fee: £%.2f\n", member.getJournalFee()));
        receipt.append(String.format("Total Price: £%.2f\n", member.getTotalFee()));
        receipt.append("+++++++++++++++++++++++++++++++++++++++++++++++++++\n");

        receiptArea.setText(receipt.toString());
    }

}
