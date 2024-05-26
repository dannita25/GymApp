package GymApp;
import java.io.*;
import java.util.List;

public interface DataStorage {
    void saveMembers(List<Member> members) throws IOException;
    List<Member> loadMembers() throws IOException, ClassNotFoundException;
}
