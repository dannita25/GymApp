package GymApp;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Path;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import GymApp.FileStorage;
import GymApp.Member;

class FileStorageTest {
    private FileStorage fileStorage;
    private Path testFile;

    @BeforeEach
    void setUp(@TempDir Path tempDir) {
        testFile = tempDir.resolve("testMembers.dat");
        fileStorage = new FileStorage(testFile.toString());
    }

    @Test
    void testSaveAndLoadMembers() throws Exception {
        // Create test members
        List<Member> originalMembers = new ArrayList<>();
        originalMembers.add(new Member("John", "Doe", "john.doe@example.com",
                "123 Street", "Premium"));
        originalMembers.add(new Member("Jane", "Doe", "jane.doe@example.com",
                "124 Street", "Standard"));

        fileStorage.saveMembers(originalMembers); // Save members

        List<Member> loadedMembers = fileStorage.loadMembers(); // Load members

        // Verify the contents
        assertNotNull(loadedMembers, "Loaded members list should not be null");
        assertEquals(originalMembers.size(), loadedMembers.size(),
                "Loaded members list size should match saved list size");

        // Verification of the contents if necessary (based on equals/hashCode implementation in Member)
        assertEquals(originalMembers.get(0).getEmail(), loadedMembers.get(0).getEmail(),
                "Member details should match");
        assertEquals(originalMembers.get(1).getEmail(), loadedMembers.get(1).getEmail(),
                "Member details should match");
    }

    @Test
    void testLoadFromEmptyFile() {
        // Expecting an IOException or specific handling because the file is supposed to be empty/non-existent
        assertThrows(IOException.class, () -> {
            fileStorage.loadMembers(); // Attempt to load from a non-existent file
        }, "Should throw IOException when loading from an empty or non-existent file");
    }
}
