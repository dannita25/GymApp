package GymApp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class MemberFormPanelTest {
    private MemberFormPanel panel;

    @BeforeEach
    void setUp() {
        panel = new MemberFormPanel(new GymMembershipApp());
    }

    @Test
    void componentsShouldNotBeNull() throws NoSuchFieldException, IllegalAccessException {
        assertAll(
                () -> assertNotNull(getField(panel, "nameField"), "Name field should not be null"),
                () -> assertNotNull(getField(panel, "lastNameField"), "Last name field should not be null"),
                () -> assertNotNull(getField(panel, "emailField"), "Email field should not be null"),
                () -> assertNotNull(getField(panel, "addressField"), "Address field should not be null"),
                () -> assertNotNull(getField(panel, "gradeBox"), "Grade box should not be null"),
                () -> assertNotNull(getField(panel, "addButton"), "Add button should not be null")
        );
    }

    private Object getField(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }
}


