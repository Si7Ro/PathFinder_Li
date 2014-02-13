package javadev;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GUI extends PathFinder {
    JFrame f = new JFrame("Path Finder");
    JPanel mainPanel = new JPanel();
    JPanel authorPanel = new JPanel();
    Font font = new Font("Arial", Font.BOLD, 8);
    JCheckBox randomizeCheck = new JCheckBox("Авто");
    JButton b = new JButton("Начать");
    JButton startPoint = new JButton();
    JButton targetPoint = new JButton();
    JButton blockPoint = new JButton();
    JButton clearField = new JButton("Очистить");
    JLabel startPointLabel = new JLabel("НАЧАЛЬНАЯ ТОЧКА");
    JLabel targetPointLabel = new JLabel("ЦЕЛЕВАЯ ТОЧКА");
    JLabel blockPointLabel = new JLabel("БЛОК ПРЕГРАДА");
    JLabel blockCountLabel = new JLabel("КОЛ-ВО ПРЕГРАД");
    JLabel author = new JLabel("Si7Ro - JavaDev - 2014");
    SpinnerNumberModel blockCountModel = new SpinnerNumberModel(conf.blockCount, 0, 300, 25);
    JSpinner blockCount = new JSpinner(blockCountModel);
    boolean startPointInt = true;
    boolean targetPointInt = false;
    boolean blockPointInt = false;

    public void show() {
	f.setResizable(false);
	f.setSize(490, 430);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setLocationRelativeTo(null);
	
	startPointLabel.setFont(font);
	startPoint.setBackground(Color.GREEN);
	startPoint.setPreferredSize(new Dimension(25, 25));

	targetPointLabel.setFont(font);
	targetPoint.setBackground(Color.BLUE);
	targetPoint.setPreferredSize(new Dimension(25, 25));

	blockPointLabel.setFont(font);
	blockPoint.setBackground(Color.GRAY);
	blockPoint.setPreferredSize(new Dimension(25, 25));
	
	blockCountLabel.setFont(font);
	blockCount.setPreferredSize(new Dimension(50, 20));
	blockCount.setEnabled(false);
	
	mainPanel.setPreferredSize(new Dimension(100, 50));
	mainPanel.add(b);
	mainPanel.add(randomizeCheck);
	mainPanel.add(startPointLabel);
	mainPanel.add(startPoint);
	mainPanel.add(targetPointLabel);
	mainPanel.add(targetPoint);
	mainPanel.add(blockPointLabel);
	mainPanel.add(blockPoint);
	mainPanel.add(blockCountLabel);
	mainPanel.add(blockCount);
	mainPanel.add(clearField);
	
	author.setFont(font);
	authorPanel.add(author, BorderLayout.CENTER);

	f.add(p, BorderLayout.CENTER);
	f.add(mainPanel, BorderLayout.WEST);
	f.add(authorPanel, BorderLayout.SOUTH);

	f.setVisible(true);
	conf.createField();
	
	
	blockCount.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent arg0) {
		conf.blockCount = (int)blockCount.getValue();
		
	    }
	});
	clearField.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		p.removeAll();
		conf.clearField();
		displayFindWayGUI();
		p.revalidate();
	    }
	});
	randomizeCheck.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (randomizeCheck.isSelected()) {
		    conf.randomize = true;
		    startPoint.setEnabled(false);
		    targetPoint.setEnabled(false);
		    blockPoint.setEnabled(false);
		    clearField.setEnabled(false);
		    blockCount.setEnabled(true);
		} else {
		    conf.randomize = false;
		    startPoint.setEnabled(true);
		    targetPoint.setEnabled(true);
		    blockPoint.setEnabled(true);
		    clearField.setEnabled(true);
		    blockCount.setEnabled(false);
		}
	    }
	});
	startPoint.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		startPointInt = true;
		targetPointInt = false;
		blockPointInt = false;
	    }
	});
	targetPoint.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		startPointInt = false;
		targetPointInt = true;
		blockPointInt = false;

	    }
	});
	blockPoint.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		startPointInt = false;
		targetPointInt = false;
		blockPointInt = true;
	    }
	});
	b.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		p.removeAll();
		conf.createField();

	    }
	});
    }
}
