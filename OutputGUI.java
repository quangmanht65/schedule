package Gr20;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class OutputGUI extends JFrame {
    public OutputGUI(GraphAdjacencyList graph) {
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Output");
        setLocationRelativeTo(null);
        createOutputUI(this, graph);
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    public static void createOutputUI(JFrame frame, GraphAdjacencyList graph) {

        final JDialog dialog = new JDialog();
        Color MIX_BLUE_GREEN = new Color(0, 204, 160);

        // Option pane to enter subjects per sem
        JPanel enterPanel = new JPanel();
        JLabel numSubLabel = new JLabel("Enter number of subjects per semester: ");
        JTextField subjectNum = new JTextField(5);
        subjectNum.setText("0");
        enterPanel.add(numSubLabel);
        enterPanel.add(subjectNum);

        int result = JOptionPane.showConfirmDialog(null, enterPanel, "Enter: ", JOptionPane.OK_CANCEL_OPTION);
        int subjectPerSemester = Integer.parseInt(subjectNum.getText());
        if (result == JOptionPane.OK_OPTION) {
            // System.out.println(subjectPerSemester);
        }

        // JLabel
        Font font = new Font("Arial", Font.BOLD, 15);
        JPanel subjectScheduleContainer = new JPanel();
        JLabel subjectSchedule = new JLabel("Subject Schedule: ");
        subjectScheduleContainer.setBackground(Color.BLACK);
        subjectScheduleContainer.add(subjectSchedule);
        subjectSchedule.setFont(font);
        subjectSchedule.setForeground(Color.WHITE);

        // End Button

        JButton endButton = new JButton("END");
        JPanel endButtonContainer = new JPanel();
        endButton.setHorizontalTextPosition(JButton.CENTER);
        endButton.setVerticalTextPosition(JButton.CENTER);
        endButton.setBackground(MIX_BLUE_GREEN);
        endButton.setFont(font);
        endButton.setForeground(Color.WHITE);
        endButton.setBackground(Color.BLACK);
        endButtonContainer.add(endButton);
        endButtonContainer.setBackground(Color.BLACK);
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.setVisible(false);
                System.exit(0);
            }

        });

        // Restart Button
        JButton restartButton = new JButton("RESTART");
        restartButton.setHorizontalTextPosition(JButton.CENTER);
        restartButton.setVerticalTextPosition(JButton.CENTER);
        restartButton.setBackground(MIX_BLUE_GREEN);
        restartButton.setFont(font);
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(Color.BLACK);
        endButtonContainer.add(restartButton);

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                dialog.setVisible(false);
                new StartGUI();
            }

        });

        // panel contain JList
        JPanel panel = new JPanel();
        JList listSubject;
        java.util.List<Subject> course = CourseScheduler.courseSchedule(graph);

        if (course == null) {
            frame.setVisible(false);
            dialog.setSize(400, 300);
            dialog.setVisible(true);
            dialog.setTitle("Sorry, something went wrong :( ");

            JLabel optionLabel = new JLabel("Tiên quyết không hợp lệ !!! ");
            optionLabel.setFont(font);
            optionLabel.setForeground(Color.WHITE);

            JPanel p = new JPanel();

            LayoutManager gridBagLayout = new GridBagLayout();

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            p.setBackground(Color.BLACK);
            p.setLayout(gridBagLayout);

            gbc.gridx = 0;
            gbc.gridy = 0;
            p.add(optionLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            p.add(endButton, gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;

            p.add(restartButton, gbc);
            dialog.add(p);
        }

        // add JList into the panel
        java.util.List<Subject> courseString;
        int numSemester = (int) Math.ceil((double) graph.numSubjects() / subjectPerSemester);

        for (int i = 0; i < numSemester; i++) {
            courseString = new ArrayList<>();
            for (int j = 0; j < subjectPerSemester; j++) {
                // Array index out of bound
                if (i * subjectPerSemester + j == graph.numSubjects())
                    break;
                courseString.add((course.get(i * subjectPerSemester + j)));
            }
            listSubject = new JList(courseString.toArray());
            panel.add(listSubject);
        }
        panel.setBackground(Color.BLACK);
        frame.getContentPane().setBackground(Color.BLACK);

        // add component into frame
        LayoutManager borderLayout = new BorderLayout();
        frame.setLayout(borderLayout);
        frame.add(BorderLayout.NORTH, subjectScheduleContainer);
        frame.add(BorderLayout.CENTER, panel);
        frame.add(BorderLayout.SOUTH, endButtonContainer);
    }
}