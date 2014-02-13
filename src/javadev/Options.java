package javadev;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.plaf.ColorChooserUI;
import javax.swing.plaf.ColorUIResource;

public class Options {
    
    JFrame frameOptions;
    JButton okButton;
    JButton cancelButton;
    JLabel title = new JLabel("Настройки");
    JLabel startPointSpinnerLabel = new JLabel("Цвет начальной точки");

   
    
    public void optionFrame(){
	frameOptions = new JFrame("Настройки");
	frameOptions.setSize(new Dimension(300, 400));
	frameOptions.setLocationRelativeTo(null);
	frameOptions.setLayout(null);
	
	
	title.setBounds(110, 10, 100, 30);
	frameOptions.add(title);
	okButton = new JButton("Сохранить");
	okButton.setBounds(30, 320, 100, 30);
	frameOptions.add(okButton);
	cancelButton = new JButton("Отмена");
	cancelButton.setBounds(150, 320, 100, 30);
	frameOptions.add(cancelButton);
	startPointSpinnerLabel.setBounds(75, 50, frameOptions.getWidth(), 30);
	frameOptions.add(startPointSpinnerLabel);

	frameOptions.setVisible(true);
    }
}
