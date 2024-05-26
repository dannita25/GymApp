package GymApp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;


public class GymMembershipApp extends JFrame {
    private DataStorage storage;

    public GymMembershipApp() {

        super("Gym Club Membership Fee Calculator");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });

        // Load the gym-themed icon image (e.g., a dumbbell icon)
        ImageIcon icon = new ImageIcon("C:/Users/Rana/gymFeeCalculator/img/1.png");
        setIconImage(icon.getImage()); // Set the icon image

        storage = new FileStorage("members.dat"); // Initialize the FileStorage with the filename where members are stored

        // Load members from storage and set them in the MembershipManager
        try {
            List<Member> loadedMembers = storage.loadMembers();  // This should return the loaded members
            MembershipManager.getInstance().setMembers(loadedMembers);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading member data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        setupFonts();
        setupMenu();
        setVisible(true);
    }

    private void setupFonts() {
        Font globalFont = new Font("Segoe UI", Font.PLAIN, 18);
        UIManager.put("Menu.font", globalFont);
        UIManager.put("MenuItem.font", globalFont);
        UIManager.put("Button.font", globalFont);
        UIManager.put("Label.font", globalFont);
        UIManager.put("TextField.font", globalFont);
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem addMember = new JMenuItem("Add New Member");
        JMenuItem displayMembers = new JMenuItem("Display List of Members");
        JMenuItem displayReceipts = new JMenuItem("Display Receipt"); // Ensure this is named appropriately
        JMenuItem exit = new JMenuItem("Save and Exit");

        addMember.addActionListener(e -> setMemberPanel(new MemberFormPanel(this)));
        displayMembers.addActionListener(e -> setMemberPanel(new MemberListPanel(this)));
        displayReceipts.addActionListener(e -> setMemberPanel(new ReceiptsPanel(this))); // Pass the main app reference if needed
        exit.addActionListener(e -> saveAndExit());

        menu.add(addMember);
        menu.add(displayMembers);
        menu.add(displayReceipts);
        menu.add(exit);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    public void setMemberPanel(JPanel panel) {
        setContentPane(panel);
        revalidate();  // Recalculate the layout of the container
        repaint();  // Repaint the container
    }

    public void showMemberListPanel() {
        MemberListPanel memberListPanel = new MemberListPanel(this);
        setContentPane(memberListPanel);
        memberListPanel.refreshMemberList();
        validate();
        repaint();
    }

    private void saveAndExit() {
        try {
            storage.saveMembers(MembershipManager.getInstance().getMembers());
            JOptionPane.showMessageDialog(this, "Changes saved successfully.",
                    "Exit Confirmation", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving member data: "
                    + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exitApplication() {
        Object[] options = {"Yes", "No", "Cancel"};
        int result = JOptionPane.showOptionDialog(this,
                "Would you like to save your changes before exiting?", "Exit Confirmation",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

        if (result == JOptionPane.YES_OPTION) {
            try {
                storage.saveMembers(MembershipManager.getInstance().getMembers());
                System.exit(0);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving member data: "
                        + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (result == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
        // Cancel or dialog close does not exit the application
        else if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Prevents the window from closing
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GymMembershipApp());
    }
}
