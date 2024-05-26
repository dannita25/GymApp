package GymApp;
import java.io.Serializable; // If you are planning to serialize objects for file I/O.
import java.time.LocalDate;
import java.io.*;

// To store information about each member, including their name, membership grade, and fees.
public class Member implements Serializable{
    private static int nextId;  // Class variable to generate unique IDs for each member
    static {
        nextId = loadNextId(); // Load this value when the class is loaded
    }
    private int id;
    private String name;
    private String lastName;
    private String email;
    private String address;
    private String membershipGrade;
    private double membershipFee;
    private LocalDate registrationDate;  // Store the registration date
    private final double journalFee = 8.0;  // Constant fee for all members
    public Member(String name, String lastName, String email, String address, String membershipGrade) {
        this.id = nextId++;  // Assign and increment the unique ID
        saveNextId(); // Save the new nextId value immediately after incrementation
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.membershipGrade = membershipGrade;
        this.registrationDate = LocalDate.now();
        calculateMembershipFee();
    }

    private void calculateMembershipFee() {
        switch (membershipGrade) {
            case "Standard":
                membershipFee = 100 + journalFee;
                break;
            case "Premium":
                membershipFee = 150 + journalFee;
                break;
            case "VIP":
                membershipFee = 200 + journalFee;
                break;
            default:
                membershipFee = journalFee; // Default to only journal fee if no valid grade selected
        }
    }

    // Getters and setters for all fields
    public int getId() {
        return id;
    }

    public String getName() {return name;}

    public String getLastName() {return lastName;}

    public String getEmail() {return email;}

    public String getAddress() {return address;}

    public String getMembershipGrade() {return membershipGrade;}

    public double getMembershipFee() {return membershipFee - journalFee;}

    public double getJournalFee() {return journalFee;}

    public LocalDate getRegistrationDate() { return registrationDate; }

    public double getTotalFee() {return membershipFee;}


    // Utility method to save nextId to a file
    public static void saveNextId() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("nextId.dat"))) {
            dos.writeInt(nextId);
        } catch (IOException e) {
            System.err.println("Unable to save nextId: " + e.getMessage());
        }
    }

    // Utility method to load nextId from a file
    public static int loadNextId() {
        File file = new File("nextId.dat");
        if (file.exists()) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
                return dis.readInt();
            } catch (IOException e) {
                System.err.println("Unable to load nextId: " + e.getMessage());
            }
        }
        return 1;  // Default to 1 if file not found or an error occurs
    }

}

