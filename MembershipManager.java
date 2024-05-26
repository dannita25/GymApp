package GymApp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import GymApp.Member;

//To manage adding members, calculating fees, and keeping track of total collected fees.
public class MembershipManager {
    // Static variable that holds the single instance of MembershipManager
    private static MembershipManager instance;
    // List to hold members
    private List<Member> members = new ArrayList<>();
    private double totalFeesCollected = 0;

    // Private constructor to prevent external instantiation
    private MembershipManager() {
    }

    // Public static method to get the single instance of MembershipManager
    public static MembershipManager getInstance() {
        if (instance == null) {
            instance = new MembershipManager();
        }
        return instance;
    }

    // Adds a member to the list and updates the total fees collected
    public void addMember(Member member) {
        members.add(member);
        totalFeesCollected += member.getMembershipFee();
    }

    // Sets the list of members and recalculates the total fees collected
    public void setMembers(List<Member> newMembers) {
        this.members = newMembers;
        recalculateTotalFees();
    }

    // Recalculates the total fees based on the current list of members
    private void recalculateTotalFees() {
        totalFeesCollected = 0; // Reset the total before recalculating
        for (Member member : members) {
            totalFeesCollected += member.getMembershipFee();
        }
    }

    // Returns a list of all members
    public List<Member> getMembers() {
        return members;
    }

    public Member getMemberById(int id) {
        for (Member member : members) {
            if (member.getId() == id) {
                return member;
            }
        }
        return null; // Return null if no member with the specified ID is found
    }

    // Method to remove a member by ID
    public boolean removeMemberById(int id) {
        Iterator<Member> iterator = members.iterator();
        while (iterator.hasNext()) {
            Member member = iterator.next();
            if (member.getId() == id) {
                iterator.remove();  // Remove the member
                return true;  // Return true if the member was found and removed
            }
        }
        return false;  // Return false if no member found with the given ID
    }

}
