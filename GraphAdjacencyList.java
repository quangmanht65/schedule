package Gr20;

import java.util.*;

public class GraphAdjacencyList implements GraphInterface {
    private int numRelations;
    private Map<Subject, List<Subject>> graph;

    public GraphAdjacencyList() {
        numRelations = 0;
        graph = new HashMap<>();
    }

    @Override
    public int numSubjects() {
        return graph.keySet().size();
    }

    @Override
    public int numRelations() {
        return numRelations;
    }

    @Override
    public int degree() {
        int degree = -1;
        Set<Subject> subjectSet = graph.keySet();
        for (Subject subject : subjectSet) {
            degree = Math.max(degree, inDegree(subject) + outDegree(subject));
        }

        return degree;
    }

    @Override
    public Subject getSubject(String id) {
        for (Subject subject : graph.keySet()) {
            if (subject.getId().equals(id)) return subject;
        }

        return null;
    }

    public Map<Integer, Subject> getSubjects() {
        Map<Integer, Subject> map = new HashMap<>();
        int i = 0;

        for (Subject subject : subjectSet()) {
            map.put(i++, subject);
        }

        return map;
    }

    @Override
    public Relation getRelation(Subject prerequisite, Subject subject) {
        // some first subjects that require no prerequisites
        if (!graph.containsKey(prerequisite)) return null;

        if (graph.get(prerequisite).contains(subject)) return new Relation(prerequisite, subject);

        // no prerequisite as well as itself, or only prerequisite exists.
        return null;
    }

    public Map<Subject, List<Subject>> getRelations() {
        Map<Subject, List<Subject>> relations = new HashMap<>();

        for (Subject subject : graph.keySet()) {
            if (get(subject).size() > 0) relations.put(subject, get(subject));
        }

//        System.out.println("\n" + numRelations + " relations");
        return relations;
    }

    @Override
    public int inDegree(Subject subject) {
        Set<Subject> subjectSet = graph.keySet();

        if (!subjectSet.contains(subject)) return -1;

        int inDegree = 0;
        for (Subject subject1 : subjectSet) {
            if (graph.get(subject1).contains(subject)) inDegree += 1;
        }
        return inDegree;
    }

    @Override
    public int outDegree(Subject subject) {
        if (!graph.containsKey(subject)) return -1;

        return graph.get(subject).size();
    }

    @Override
    public Subject insertSubject(Subject subject) {
        // in case there are repeated subjects in the input
        if (getSubject(subject.getId()) != null) return null;
        graph.put(subject, new ArrayList<>());

        return subject;
    }

    @Override
    public Relation insertRelation(Subject prerequisite, Subject subject) {
        // directed edge turn to undirected edge -> violate DAG rule
        if (getRelation(subject, prerequisite) != null) return null;
        // repeated edges
        for (Subject subject1 : graph.get(prerequisite)) {
            if (subject1.getId().equals(subject.getId())) return null;
        }

        graph.get(prerequisite).add(subject);
        numRelations++;

        return new Relation(prerequisite, subject);
    }

    public List<Subject> get(Subject subject) {
        for (Subject subject1 : graph.keySet()) {
            if (subject.getId().equals(subject1.getId())) return graph.get(subject1);
        }
        return null;
    }

    public Set<Subject> subjectSet() {
        return new HashSet<>(graph.keySet());
    }

    public void printGraph() {
        if (graph == null) return;

        int i = 0;
        for (Subject subject : graph.keySet()) {
            System.out.println(++i + ". " + subject + " -> " + (graph.get(subject) == null ? "null" : graph.get(subject)));
        }
    }

    public void printRelations() {
        Map<Subject, List<Subject>> relations = getRelations();
        if (relations == null) return;

        int i = 0;

        for (Subject subject : relations.keySet()) {
            System.out.println(++i + ". " + subject + " -> " + graph.get(subject));
        }
    }
}