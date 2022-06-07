package Gr20;

public interface GraphInterface {
    int numSubjects();
    int numRelations();
    int degree();
    Subject getSubject(String id);
    Relation getRelation(Subject prerequisite, Subject subject);
    int inDegree(Subject subject);
    int outDegree(Subject subject);
    Subject insertSubject(Subject subject);
    Relation insertRelation(Subject prerequisite, Subject subject);
}
