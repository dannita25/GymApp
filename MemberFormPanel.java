package GymApp;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemberFormPanel extends JPanel {
    private JTextField nameField, lastNameField, emailField, addressField;
    private JComboBox<String> gradeBox;
    private JButton addButton;
    private GymMembershipApp mainApp;

    public MemberFormPanel(GymMembershipApp mainApp) {
        this.mainApp = mainApp;
        setLayout(new GridLayout(0, 2));  // Use a grid layout
        // Add fields
        add(new JLabel("First Name:"));
        nameField = new JTextField(20);
        add(nameField);

        add(new JLabel("Last Name:"));
        lastNameField = new JTextField(20);
        add(lastNameField);

        add(new JLabel("Email:"));
        emailField = new JTextField(20);
        add(emailField);

        add(new JLabel("Address:"));
        addressField = new JTextField(20);
        add(addressField);

        add(new JLabel("Membership Grade:"));
        gradeBox = new JComboBox<>(new String[] {"Standard", "Premium", "VIP"});
        add(gradeBox);

        addButton = new JButton("Add Member");
        add(addButton);
        addButton.addActionListener(e -> addMember());

    }

    private void addMember() {
        String name = nameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();
        String grade = (String) gradeBox.getSelectedItem();

        // Debugging output to console
        System.out.println("Adding member with details: " + name + ", " + lastName + ", " + email + ", " + address + ", " + grade);

        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || address.isEmpty() || grade == null) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all fields and select a membership grade.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Member newMember = new Member(name, lastName, email, address, grade);
        MembershipManager.getInstance().addMember(newMember);
        JOptionPane.showMessageDialog(this, "Member added!");

        if (mainApp != null) {
            mainApp.showMemberListPanel();  // Refresh the member list panel to show the new member
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return ((Matcher) matcher).matches();
    }

}