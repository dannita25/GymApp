package GymApp;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import GymApp.DataStorage;
import GymApp.Member;

public class FileStorage implements DataStorage {
    private String filename;

    public FileStorage(String filename) {
        this.filename = filename;
    }

    @Override
    public void saveMembers(List<Member> members) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(members);
        }
    }

    @Override
    public List<Member> loadMembers() throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Member>) in.readObject();  // Directly return the list of members
        }
    }

}
