package GymApp;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import javax.swing.*;

public class MemberListPanel extends JPanel {
    private JTable table;
    private GymMembershipApp mainApp;
    public MemberListPanel(GymMembershipApp mainApp) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID", "First Name", "Last Name", "Email", "Address", "Grade",
                "Fee", "Journal Fee",
                "Registration Day", "Total"});

        table = new JTable(model);
        fillTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton deleteButton = new JButton("Delete Member");
        add(deleteButton, BorderLayout.SOUTH);
        deleteButton.addActionListener(e -> deleteSelectedMember());
    }

    private void deleteSelectedMember() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int memberId = (int) table.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this member?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                if (MembershipManager.getInstance().removeMemberById(memberId)) {
                    ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Member deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error deleting member.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No member selected.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void fillTable(DefaultTableModel model) {
        for (Member m : MembershipManager.getInstance().getMembers()) {
            model.addRow(new Object[]{
                    m.getId(),
                    m.getName(),
                    m.getLastName(),
                    m.getEmail(),
                    m.getAddress(),
                    m.getMembershipGrade(),
                    m.getMembershipFee(),
                    m.getJournalFee(),
                    m.getRegistrationDate(),
                    m.getTotalFee()});
        }
    }

    public void refreshMemberList() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing data
        fillTable(model); // Refill table with latest data
    }
    //For testing
    public JTable getTable() {
        return table;
    }

}
