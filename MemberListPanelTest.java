package GymApp;
import GymApp.MemberListPanel;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.table.DefaultTableModel;

class MemberListPanelTest {
    private MemberListPanel panel;
    private GymMembershipApp mainApp; // Assuming this is already implemented correctly.

    @BeforeEach
    void setUp() {
        // Assuming mainApp is correctly instantiated somewhere globally or mocked
        panel = new MemberListPanel(mainApp);
    }

    @Test
    void panelShouldNotBeNull() {
        assertNotNull(panel, "Panel should be instantiated.");
    }

    @Test
    void tableShouldHaveCorrectColumns() {
        String[] expectedColumns = {"ID", "First Name", "Last Name", "Email", "Address", "Grade",
                "Fee", "Journal Fee", "Registration Day", "Total"};
        DefaultTableModel model = (DefaultTableModel) panel.getTable().getModel();
        for (int i = 0; i < model.getColumnCount(); i++) {
            assertEquals(expectedColumns[i], model.getColumnName(i), "Column names should match expected names.");
        }
    }

    @Test
    void addingAndDeletingMembers() {
        Member member1 = new Member("John", "Doe", "john@example.com", "123 Street", "Premium");
        Member member2 = new Member("Jane", "Doe", "jane@example.com", "124 Street", "Standard");
        MembershipManager.getInstance().addMember(member1);
        MembershipManager.getInstance().addMember(member2);
        panel.refreshMemberList();

        DefaultTableModel model = (DefaultTableModel) panel.getTable().getModel();
        assertEquals(2, model.getRowCount(), "Two members should be in the table.");

        MembershipManager.getInstance().removeMemberById(member1.getId());
        panel.refreshMemberList();
        assertEquals(1, model.getRowCount(), "One member should remain after deletion.");
    }
}
