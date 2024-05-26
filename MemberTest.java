package GymApp;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import GymApp.Member;

class MemberTest {
    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member("John", "Doe",
                "john.doe@gmail.com", "123 Elm St", "Premium");
    }

    @Test
    void testUniqueId() {
        Member secondMember = new Member("Jane", "Doe", "jane.doe@gmail.com", "124 Elm St", "Premium");
        assertNotEquals(member.getId(), secondMember.getId(), "Each member should have a unique ID");
    }

    @Test
    void testMembershipFeeCalculation() {
        assertEquals(150.0, member.getMembershipFee(),
                "Membership fee should be calculated based on the 'Premium' grade");
    }

    @Test
    void testCorrectRegistrationDate() {
        assertEquals(LocalDate.now(), member.getRegistrationDate(),
                "Registration date should be set to the current date");
    }

    @Test
    void testSerialization() {
        Member.saveNextId(); // Assume static access is allowed for simplicity
        assertEquals(Member.loadNextId(), member.getId() + 1,
                "Next ID should be incremented and saved correctly");
    }

    @Test
    void testGetters() {
        assertAll("Test all getters",
                () -> assertEquals("John", member.getName()),
                () -> assertEquals("Doe", member.getLastName()),
                () -> assertEquals("john.doe@gmail.com", member.getEmail()),
                () -> assertEquals("123 Elm St", member.getAddress()),
                () -> assertEquals("Premium", member.getMembershipGrade()),
                () -> assertEquals(LocalDate.now(), member.getRegistrationDate()),
                () -> assertEquals(158.0, member.getTotalFee(),
                        "Total fee includes membership and journal fee")
        );
    }
}

