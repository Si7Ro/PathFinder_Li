package javadev;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PathFinder {
    static Config conf = new Config();
    static Elements e = new Elements();
    static JPanel p = new JPanel();
    static GUI g = new GUI();
    Component[][] buttonField;

    public static void main(String[] args) {
	//Options frameO = new Options();
	//frameO.optionFrame();
	g.show();
    }

    // Отрисовка результата выполнения алгоритма в GUI
    public void displayFindWayGUI() {
	p.setPreferredSize(new Dimension(390, 410));
	p.setLayout(new FlowLayout());
	buttonField = new Component[conf.fieldW][conf.fieldH];
	ActionListener handler = new Handler();

	// Выводим батоны
	for (int i = 0; i < conf.fieldH; i++) {
	    for (int j = 0; j < conf.fieldW; j++) {
		buttonField[i][j] = new JButton();
		((AbstractButton) buttonField[i][j]).addActionListener(handler);
	    }
	}

	for (int i = 0; i < conf.fieldH; i++) {
	    for (int j = 0; j < conf.fieldW; j++) {
		// не редактируемые батоны вокруг поля
		buttonField[i][0].setEnabled(false);
		buttonField[0][i].setEnabled(false);
		buttonField[i][conf.fieldH - 1].setEnabled(false);
		buttonField[conf.fieldH - 1][i].setEnabled(false);

		// Окрашиваем батоны в зависимости от типа елементов
		if (conf.field[i][j] == e.BLOCK) {
		    buttonField[i][j].setBackground(Color.GRAY);
		    buttonField[i][j].setPreferredSize(new Dimension(10, 10));
		    p.add(buttonField[i][j]);
		}

		if (conf.field[i][j] == e.EMPTY) {
		    buttonField[i][j].setPreferredSize(new Dimension(10, 10));
		    p.add(buttonField[i][j]);
		}

		if (conf.field[i][j] == e.A) {
		    buttonField[i][j].setBackground(Color.GREEN);
		    buttonField[i][j].setPreferredSize(new Dimension(10, 10));
		    p.add(buttonField[i][j]);
		}

		if (conf.field[i][j] == e.B) {
		    buttonField[i][j].setBackground(Color.BLUE);
		    buttonField[i][j].setPreferredSize(new Dimension(10, 10));
		    p.add(buttonField[i][j]);
		}

		if (conf.field[i][j] == -1) {
		    buttonField[i][j].setBackground(Color.RED);
		    buttonField[i][j].setPreferredSize(new Dimension(10, 10));
		    p.add(buttonField[i][j]);

		}

	    }

	}
	p.revalidate();

    }

    // Взаимодействие пользователя с полем
    public class Handler implements ActionListener {
	public void actionPerformed(ActionEvent ev) {
	    for (int i = 0; i < conf.fieldH; i++) {
		for (int j = 0; j < conf.fieldW; j++) {
		    if (!conf.randomize) {
			if (g.blockPointInt) {
			    if (ev.getSource() == buttonField[i][j]) {
				if (conf.field[i][j] == e.BLOCK) {
				    conf.field[i][j] = e.EMPTY;
				    p.removeAll();
				    displayFindWayGUI();
				    p.revalidate();

				} else {
				    buttonField[i][j].setBackground(Color.GRAY);
				    conf.field[i][j] = e.BLOCK;
				}
			    }
			}
			if (g.startPointInt) {
			    if (ev.getSource() == buttonField[i][j]) {
				for (int s = 0; s < conf.fieldH; s++) {
				    for (int t = 0; t < conf.fieldW; t++) {
					if (conf.field[s][t] == e.A) {
					    conf.field[s][t] = e.EMPTY;
					    p.removeAll();
					    displayFindWayGUI();
					    p.revalidate();
					}
				    }
				}
				buttonField[i][j].setBackground(Color.GREEN);
				conf.field[i][j] = e.A;
				conf.startX = i;
				conf.startY = j;
			    }

			}

			if (g.targetPointInt) {
			    if (ev.getSource() == buttonField[i][j]) {
				for (int s = 0; s < conf.fieldH; s++) {
				    for (int t = 0; t < conf.fieldW; t++) {
					if (conf.field[s][t] == e.B) {
					    conf.field[s][t] = e.EMPTY;
					    p.removeAll();
					    displayFindWayGUI();
					    p.revalidate();
					}
				    }
				}
				buttonField[i][j].setBackground(Color.BLUE);
				conf.field[i][j] = e.B;
				conf.targetX = i;
				conf.targetY = j;
			    }
			}
		    }
		}
	    }

	}

    }

    /*
     * // Отрисовка результата выполнения алгоритма в консоли public void
     * displayFindWay() { for (int i = 0; i < conf.fieldH; i++) { for (int j =
     * 0; j < conf.fieldW; j++) { if (conf.field[i][j] == e.BLOCK) {
     * System.out.print(" # "); }
     * 
     * if (conf.field[i][j] == e.EMPTY) { System.out.print("   "); }
     * 
     * if (conf.field[i][j] == e.A) { System.out.print(" A "); }
     * 
     * if (conf.field[i][j] == e.B) { System.out.print(" B "); }
     * 
     * if (conf.field[i][j] == -1) { System.out.print(" * ");
     * 
     * }
     * 
     * } System.out.print("  "); System.out.println();
     * 
     * } }
     */

    // Собственно сам алгоритм поиска пути
    public void algLi() {
	int iter = 0;
	int iterk = conf.config();
	int posX = conf.startX;
	int posY = conf.startY;
	int X1 = 0;
	int Y1 = 0;
	int min = e.BLOCK;

	while (iter < iterk) {
	    // Запускаем волны
	    for (int i = 0; i < conf.fieldH; i++) {

		for (int j = 0; j < conf.fieldW; j++) {

		    if (conf.history[i][j] == iter) {

			try {
			    if (conf.history[i + 1][j - 1] == e.EMPTY) {
				conf.history[i + 1][j - 1] = iter + 1;
			    }
			    if (conf.history[i - 1][j + 1] == e.EMPTY) {
				conf.history[i - 1][j + 1] = iter + 1;
			    }
			    if (conf.history[i + 1][j] == e.EMPTY) {
				conf.history[i + 1][j] = iter + 1;
			    }
			    if (conf.history[i + 1][j + 1] == e.EMPTY) {
				conf.history[i + 1][j + 1] = iter + 1;
			    }

			    if (conf.history[i - 1][j] == e.EMPTY) {
				conf.history[i - 1][j] = iter + 1;
			    }
			    if (conf.history[i - 1][j - 1] == e.EMPTY) {
				conf.history[i - 1][j - 1] = iter + 1;
			    }
			    if (conf.history[i][j + 1] == e.EMPTY) {
				conf.history[i][j + 1] = iter + 1;
			    }
			    if (conf.history[i][j - 1] == e.EMPTY) {
				conf.history[i][j - 1] = iter + 1;
			    }
			    if (conf.history[i + 1][j] == e.A) {
				break;
			    }
			    if (conf.history[i - 1][j] == e.A) {
				break;
			    }
			    if (conf.history[i][j + 1] == e.A) {
				break;
			    }
			    if (conf.history[i][j - 1] == e.A) {
				break;
			    }
			    if (conf.history[i + 1][j - 1] == e.A) {
				break;
			    }
			    if (conf.history[i - 1][j + 1] == e.A) {
				break;
			    }
			    if (conf.history[i + 1][j + 1] == e.A) {
				break;
			    }
			    if (conf.history[i - 1][j - 1] == e.A) {
				break;
			    }

			} catch (Exception e) {

			}

		    }

		}

	    }
	    iter++;
	}
	int count = 0;
	// Ищем путь от конечной точки до стартовой
	while (true) {
	    if (conf.history[posX + 1][posY] < min) {
		min = conf.history[posX + 1][posY];
		X1 = posX + 1;
		Y1 = posY;
	    }
	    if (conf.history[posX - 1][posY] < min) {
		min = conf.history[posX - 1][posY];
		X1 = posX - 1;
		Y1 = posY;
	    }
	    if (conf.history[posX][posY + 1] < min) {
		min = conf.history[posX][posY + 1];
		X1 = posX;
		Y1 = posY + 1;
	    }
	    if (conf.history[posX][posY - 1] < min) {
		min = conf.history[posX][posY - 1];
		X1 = posX;
		Y1 = posY - 1;
	    }
	    if (conf.history[posX + 1][posY - 1] < min) {
		min = conf.history[posX + 1][posY - 1];
		X1 = posX + 1;
		Y1 = posY - 1;
	    }
	    if (conf.history[posX - 1][posY - 1] < min) {
		min = conf.history[posX - 1][posY - 1];
		X1 = posX - 1;
		Y1 = posY - 1;
	    }
	    if (conf.history[posX - 1][posY + 1] < min) {
		min = conf.history[posX - 1][posY + 1];
		X1 = posX - 1;
		Y1 = posY + 1;
	    }
	    if (conf.history[posX + 1][posY + 1] < min) {
		min = conf.history[posX + 1][posY + 1];
		X1 = posX + 1;
		Y1 = posY + 1;
	    }
	    posX = X1;
	    posY = Y1;
	    if (conf.history[X1][Y1] == e.B) {
		break;
	    }
	    conf.field[X1][Y1] = -1;
	    if (count == conf.config()) {
		// System.out.println("Путь не найден!!!");
		JOptionPane.showMessageDialog(null, "Путь не найден!");
		break;
	    }
	    count++;
	}
	displayFindWayGUI();
    }
}
