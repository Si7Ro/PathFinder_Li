package javadev;

public class Config extends PathFinder {
    // ---------------НАСТРОЙКИ---------------//

    // Генерация поля ручная/автоматическая (true/false);
    boolean randomize = false;
    int blockCount = 100;

    // Координаты стартовой точки
    int startX = 1;
    int startY = 1;

    // Координаты конечной точки
    int targetX = 20;
    int targetY = 20;

    // --------------------------------------//
    int fieldH = 25;
    int fieldW = 25;
    int[][] history = new int[fieldH][fieldW];
    int[][] field = new int[fieldH][fieldW];

    // Ограничения рандома
    public int randomize() {
	int randomInt = fieldH;
	int random = (int) (Math.random() * randomInt);
	if (random < 2) {
	    random += 3;
	}
	if (random > fieldH - 2) {
	    random = fieldH - 2;
	}
	if (random > fieldW - 2) {
	    random = fieldW - 2;
	}
	return random;
    }

    // Конфигурирование некоторых элементов (BLOCK, EMPTY, A, B и т.д.)
    public int config() {
	int field = fieldH * fieldW;
	return field;
    }

    // Очистка поля
    public void clearField() {
	for (int i = 0; i < fieldH; i++) {
	    for (int j = 0; j < fieldW; j++) {
		field[i][j] = e.EMPTY;
	    }
	}
	for (int i = 0; i < fieldH; i++) {
	    field[i][0] = e.BLOCK;
	    field[0][i] = e.BLOCK;
	    field[i][fieldH - 1] = e.BLOCK;
	    field[fieldH - 1][i] = e.BLOCK;
	}
    }

    // Отрисовуем рабочее поле
    public void createField() {
	if (randomize) {
	    startX = randomize();
	    startY = randomize();
	    targetX = randomize();
	    targetY = randomize();
	}
	// Инициализируем рабочий массив
	for (int i = 0; i < fieldH; i++) {
	    for (int j = 0; j < fieldW; j++) {
		if (randomize) {
		    field[i][j] = e.EMPTY;
		} else {
		    if (field[i][j] != e.BLOCK) {
			field[i][j] = e.EMPTY;
		    }
		}
	    }
	}
	// Random true
	if (randomize) {
	    int add = 0;
	    while (add != blockCount) {
		int random1 = randomize();
		int random2 = randomize();
		if (field[random1][random2] == e.EMPTY) {
		    field[random1][random2] = e.BLOCK;
		    add++;
		}
	    }
	}

	// Ограничиваем поле блоками
	for (int i = 0; i < fieldH; i++) {
	    field[i][0] = e.BLOCK;
	    field[0][i] = e.BLOCK;
	    field[i][fieldH - 1] = e.BLOCK;
	    field[fieldH - 1][i] = e.BLOCK;
	}
	// Задаем стартовую точку и конечную
	field[startX][startY] = e.A;
	field[targetX][targetY] = e.B;

	// Инициализируем титульный массив для отрисовки результата
	for (int i = 0; i < fieldH; i++) {
	    for (int j = 0; j < fieldW; j++) {
		if (field[i][j] == e.BLOCK) {
		    history[i][j] = e.BLOCK;
		}
		if (field[i][j] == e.EMPTY) {
		    history[i][j] = e.EMPTY;
		}
		if (field[i][j] == e.B) {
		    history[i][j] = e.B;
		}
		if (field[i][j] == e.A) {
		    history[i][j] = e.A;
		}
	    }
	}
	algLi();
    }
}
