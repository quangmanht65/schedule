package Gr20;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class CourseScheduler {
    public static void main(String[] args) {
        GraphAdjacencyList graph = readFile("C:\\Users\\admin\\IdeaProjects\\des\\src\\Gr20\\Subjects.txt");
        assert graph != null;
//        System.out.println("Graph: ");
//        graph.printGraph();
//        System.out.println("\n--------------------------------------------------------");
//        System.out.println("Relations: ");
        graph.printRelations();

        System.out.println(graph.getSubjects());


//        System.out.println(graph.getRelations());
//        System.out.println(Arrays.toString(graph.getRelations().keySet().toArray()));
        System.out.println("---------------------------------------------------------------");

        for (Integer integer : graph.getSubjects().keySet()) {
            System.out.println(integer + " = " + graph.getSubjects().get(integer));
        }
        System.out.println("---------------------------------------------------------------");
        Map<Integer, List<Integer>> convertMap = convertMap(graph.getSubjects(), graph.getRelations());
        printMap(convertMap);

        System.out.println("---------------------------------------------------------------");
        for (Integer integer : graph.getSubjects().keySet()) {
            System.out.println(integer + " = " + graph.getSubjects().get(integer));
        }

        System.out.println("---------------------------------------------------------------");

        ArrayList<Integer[]> splitRelation = splitNumMap(convertMap);

        for (int i = 0; i < splitRelation.size(); i++) {
            System.out.println(Arrays.toString(splitRelation.get(i)));
        }

        System.out.println("---------------------------------------------------------------");
        System.out.println(Arrays.toString(numSchedule(graph)));

        // output course schedule
        int count = 0;
        System.out.println("Course: ");
        for (Subject subject : courseSchedule(graph)) {
            if ((count + 1) % 10 == 0) {
                System.out.println();
            } else if (count + 1 == courseSchedule(graph).size()) {
                System.out.println(subject.getId());
            } else {
                System.out.print(subject.getId() + " -> ");
            }
            count++;
        }

        /* Output:
            [0, 4, 15, 17, 18, 21, 23, 24, 28, 29, 33, 35, 37, 43, 3, 26, 1, 30, 12, 7, 10, 20, 41, 8, 36, 31, 34, 9, 39,
             38, 19, 11, 27, 13, 16, 5, 14, 22, 32, 2, 42, 25, 40, 6]
            FLF1107 -> MAT2506 -> PHI1006 -> MAT1060 -> HIS1001 -> MAT4083 -> PHY1103 -> MAT3557 -> GEO1050 ->
            POL1001 -> MAT2400 -> PHY1100 -> MAT2501 -> PEC1008 -> MAT2317 -> MAT2502 -> MAT2403 -> MAT3500 ->
            MAT3372 -> MAT3507 -> MAT2503 -> MAT2323 -> MAT2407 -> MAT2034 -> MAT3514 -> MAT3383 -> MAT3385 ->
            MAT3381 -> MAT3508 -> MAT3379 -> MAT3534 -> MAT3386 -> MAT3389 -> MAT3395 -> MAT2315 -> MAT3148 ->
            MAT3380 -> MAT3382 -> MAT3562 -> MAT3399
         */

        System.out.println("-------------------------------------------------------------------------");
        System.out.println(graph.getSubjects());
    }

    // return numSchedule, but in subject name
    public static List<Subject> courseSchedule(GraphAdjacencyList graph) {
        if (graph == null) return null;

        List<Subject> course = new ArrayList<>();
        Integer[] numSchedule = numSchedule(graph);

        if (numSchedule == null) return null;

        for (Integer index : numSchedule) {
            course.add(graph.getSubjects().get(index));
        }

        return course;
    }

    // browse input from device
    public static GraphAdjacencyList readFile(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            Subject prerequisite;
            GraphAdjacencyList graph = new GraphAdjacencyList();
            ArrayList<String> leftSubject = new ArrayList<>();

            try {
                while (line != null) {
                    // split by comma, as well as trimming all excessive spaces
                    String[] list = line.split(",|\s+");
                    Subject subject = new Subject(list[0], Integer.parseInt(list[1]));  // new Subject(id, credit)
                    graph.insertSubject(subject);

                    if (!leftSubject.isEmpty() && leftSubject.contains(subject.getId())) {
                        addAllLeft(graph, leftSubject, subject.getId());
                    }

                    // list.length by default > 0, since line != null
                    // input has been formatted to
                    //          subject, credits, prerequisites
                    // -> prerequisites will start from list[2] on
                    if (list.length >= 3) {
                        for (int i = 2; i < list.length; i++) {
                            prerequisite = graph.getSubject(list[i]);
                            // if the prerequisite is some subjects we haven't seen (yet), then we add them to a list
                            // and link them to the subject later on.
                            if (prerequisite == null) {
                                leftSubject.add(list[i]);
                            } else {
                                graph.insertRelation(prerequisite, subject);
                            }
                        }
                    }
                    line = reader.readLine();

                }
            } finally {
                reader.close();
            }

            return graph;
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            return null;
        }
    }

    // return number for a subject
    private static int idOf(Map<Integer, Subject> map, Subject subject) {
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i) == subject) return i;
        }

        return -1;
    }

    // topological sort (by number)
    private static Integer[] numSchedule(GraphAdjacencyList graph) {
        if (graph == null) return null;

        int numSubjects = graph.numSubjects();
        Integer[] schedule = new Integer[numSubjects];
        Map<Subject, List<Subject>> relationNum = graph.getRelations();
        Map<Integer, Subject> subjectNum = graph.getSubjects();

        Map<Integer, List<Integer>> map = new HashMap<>();
        // in-degree array -> môn có in-degree = 0
        int[] inDegree = new int[numSubjects];

        ArrayList<Integer[]> prerequisites = splitNumMap(convertMap(subjectNum, relationNum));

        assert prerequisites != null;
        for (Integer[] pair : prerequisites) {
            map.putIfAbsent(pair[1], new ArrayList<>());
            map.get(pair[1]).add(pair[0]);
        }

        // insert in-degree of each subject
        for (int i = 0; i < inDegree.length; i++) {
            inDegree[i] = graph.inDegree(graph.getSubjects().get(i));
        }

        Queue<Integer> queue = new LinkedList<>();

        // push the non prerequisite-required subjects to the queue
        for (int i = 0; i < numSubjects; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        int index = 0;

        while (!queue.isEmpty()) {
            // add the subject to the output
            int current = queue.poll();
            schedule[index++] = current;

            if(map.containsKey(current)) {
                for (int next : map.get(current)) {
                    inDegree[next]--;

                    if (inDegree[next] == 0) {
                        queue.offer(next);
                    }
                }
            }
        }

        return index == numSubjects ? schedule : null;
    }

    // convert to make our relationMap more accessible, by numbering every subject (i.e: FLF1107 = 0)
    private static Map<Integer, List<Integer>> convertMap(Map<Integer, Subject> map, Map<Subject, List<Subject>> relationMap) {
        Map<Integer, List<Integer>> relationNums = new HashMap<>();

        List<Integer> link;
        for (Subject prerequisite : relationMap.keySet()) {
            link = new ArrayList<>();
            for (Subject subject : relationMap.get(prerequisite)) {
                link.add(idOf(map, subject));
            }
            relationNums.put(idOf(map, prerequisite), link);
        }

        return relationNums;
    }

    // after converting to a numbered relation map, we split those relations to different pairs,
    // i.e: subject 1 -> [subject 2, subject 3] will turn to [subject 1, subject 2], [subject 1, subject 3]
    private static ArrayList<Integer[]> splitNumMap(Map<Integer, List<Integer>> convertMap) {
        if (convertMap.isEmpty()) return null;

        ArrayList<Integer[]> splitRelations = new ArrayList<>();
        Integer[] pair;

        for (Integer prerequisite : convertMap.keySet()) {
            for (Integer subject : convertMap.get(prerequisite)) {
                pair = new Integer[2];
                pair[0] = subject;
                pair[1] = prerequisite;
                splitRelations.add(pair);
            }
        }

        return splitRelations;
    }

    // add subjects waiting in queue
    private static void addAllLeft(GraphAdjacencyList graph, ArrayList<String> leftSubjects, String id) {
        int index = leftSubjects.indexOf(id);
        Subject prerequisite = graph.getSubject(leftSubjects.remove(index));

        for (Subject subject : graph.subjectSet()) {
            List<Subject> list = graph.get(subject);

            if (list.contains(prerequisite)) graph.insertRelation(prerequisite, subject);
        }
    }

    public static void printMap(Map<Integer, List<Integer>> map) {
        for (Integer index : map.keySet()) {
            System.out.println(index + " = " + map.get(index));
        }
    }
}
