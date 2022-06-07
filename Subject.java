package Gr20;

import java.util.List;

public class Subject {
    private String id;
    private int credits;
    private List<String> prerequisite;

    public Subject() {
        id = null;
        credits = 0;
        prerequisite = null;
    }

    public Subject(String id) {
        this.id = id;
        credits = 0;
        prerequisite = null;
    }

    public Subject(String id, int credits) {
        this.id = id;
        this.credits = credits;
        prerequisite = null;
    }

//    public Subject(String id, int credits, String... prerequisite) {
//        this.id = id;
//        this.credits = credits;
//        this.prerequisite = List.of(prerequisite);
//    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getCredits() {
        return credits;
    }

    public void setPrerequisite(List<String> prerequisite) {
        this.prerequisite = prerequisite;
    }

    public List<String> getPrerequisite() {
        return prerequisite;
    }

    @Override
    public String toString() {
        return "(" + id + ": " + getCredits() + ")";
    }
}
