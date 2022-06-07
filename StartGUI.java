package Gr20;

import javax.swing.*;

import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartGUI extends JFrame {
	public static void main(String[] args) {
		new StartGUI();
	}

	public StartGUI() {
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Welcome!!!");
		setLocationRelativeTo(null);		// centering frame
		createUI(this);
		setVisible(true);
	}

	public static void createUI(JFrame frame) {
		// color
		Color MIX_BLUE_GREEN = new Color(0,204,160);
		Font font = new Font("Arial", Font.BOLD, 22);

		// panel
		JPanel panel = new JPanel();
		panel.setBackground(MIX_BLUE_GREEN);
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// create start button
		JButton startButton = new JButton("START");
		panel.add(startButton);
		startButton.setHorizontalTextPosition(JButton.CENTER);
		startButton.setVerticalTextPosition(JButton.CENTER);
		startButton.setBackground(Color.BLACK);
		startButton.setFont(font);
		startButton.setForeground(MIX_BLUE_GREEN);
		startButton.setBackground(Color.BLACK);


		// set layout and background color for frame
		LayoutManager gridBagLayout = new GridBagLayout();
		frame.setLayout(gridBagLayout);
		frame.add(panel, new GridBagConstraints());
		frame.getContentPane().setBackground(Color.BLACK);

		// set size for button
		Dimension d = new Dimension(100, 50);
		startButton.setPreferredSize(d);

		// create empty border
		Border emptyBorder = BorderFactory.createEmptyBorder();
		startButton.setBorder(emptyBorder);
		panel.setBorder(emptyBorder);


		// add action for start button
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new InputGUI();
				frame.setVisible(false);
			}

		});

	}
}
