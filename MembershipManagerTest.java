package GymApp;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import GymApp.Member;
import GymApp.MembershipManager;
import java.util.ArrayList;
import java.util.List;

class MembershipManagerTest {
    private MembershipManager manager;

    @BeforeEach
    void setUp() {
        manager = MembershipManager.getInstance();
        manager.setMembers(new ArrayList<>()); // Reset the list of members before each test
    }

    @Test
    void testSingletonInstance() {
        MembershipManager anotherManager = MembershipManager.getInstance();
        assertSame(manager, anotherManager, "getInstance should return the same instance");
    }

    @Test
    void testAddMember() {
        Member member = new Member("John", "Doe", "john.doe@example.com",
                "123 Street", "Premium");
        manager.addMember(member);
        assertEquals(1, manager.getMembers().size(),
                "Member list should have one member after addition");
        assertEquals(member, manager.getMembers().get(0),
                "The added member should be in the members list");
    }

    @Test
    void testRemoveMemberById() {
        Member member = new Member("John", "Doe", "john.doe@example.com", "123 Street", "Premium");
        manager.addMember(member);
        boolean removed = manager.removeMemberById(member.getId());
        assertTrue(removed, "Member should be removed successfully");
        assertTrue(manager.getMembers().isEmpty(), "Member list should be empty after removal");
    }

}
