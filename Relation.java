package Gr20;

public class Relation {
    private Subject prerequisite;
    private Subject subject;

    public Relation() {
        prerequisite = new Subject();
        subject = new Subject();
    }

    // from a subject prerequisite to itself
    public Relation(Subject prerequisite, Subject subject) {
        this.prerequisite = prerequisite;
        this.subject = subject;
    }
}
