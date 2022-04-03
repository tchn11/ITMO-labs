import numpy as np
import matplotlib.pyplot as plt

INPUT = "./input.txt"

def get_points_file():
    # Получение точек из файла
    with open(INPUT, 'rt') as file:
        try:
            points = []
            for line in file:
                new_row = list(map(float, line.strip().split()))
                if len(new_row) != 2:
                    raise ValueError
                points.append(new_row)
        except ValueError:
            print("Неверный формат файла")
            exit()
    return points

def get_points_console():
    n = 0
    while n <= 0:
        try:
            n = int(input("Ведите количество точек, которые хотите ввести: "))
        except Exception:
            print("Введите число")

    points = []
    print("Введите точки через пробел: ")
    while len(points) != n:
        try:
            for i in range(n):
                points.append(list(map(float, input().strip().split())))
                if len(points[i]) != 2:
                    raise ValueError
        except ValueError:
            print("Неправильный формат ввода")
            exit()
    return points

def ask_input_data():
    mode = 0
    while mode != 1 and mode != 2 and mode != 3:
        try:
            mode = int(input("Ведите источник точек. Для файла: 1, для консоли: 2, готовая функция: 3: "))
        except Exception:
            print("Введите число")
    return mode

def ploting(points, lin_f, sqd_f, qub_f, exp_f, log_f, deg_f):
    minimum_x = 0
    maximum_x = 0

    minimum_y = 0
    maximum_y = 0

    points_x = []
    points_y = []

    for i in points:
        maximum_x = max(i[0], maximum_x)
        minimum_x = min(i[0], minimum_x)
        maximum_y = max(i[1], maximum_y)
        minimum_y = min(i[1], minimum_y)
        points_x.append(i[0])
        points_y.append(i[1])

    x = np.linspace(minimum_x - 0.5, maximum_x + 0.5, 10000)

    fig = plt.figure()
    ax = fig.add_subplot(1, 1, 1)
    ax.spines['left'].set_position('center')
    ax.spines['bottom'].set_position('center')
    ax.spines['right'].set_color('none')
    ax.spines['top'].set_color('none')
    ax.xaxis.set_ticks_position('bottom')
    ax.yaxis.set_ticks_position('left')

    ax.plot(x, lin_f(x), "r", linewidth=2.0, label="linear")
    ax.plot(x, sqd_f(x), "g", linewidth=2.0, label="squad")
    ax.plot(x, qub_f(x), "b", linewidth=2.0, label="cube")
    if exp_f is not None:
        ax.plot(x, exp_f(x), "pink", linewidth=2.0, label="exp")
    x = np.linspace(0.000001, maximum_x + 0.5, 10000)
    if log_f is not None:
        ax.plot(x, log_f(x), "darkred", linewidth=2.0, label="log")
    if deg_f is not None:
        ax.plot(x, deg_f(x), "purple", linewidth=2.0, label="deg")
    ax.legend()
    ax.plot(points_x, points_y, linewidth=0 ,marker="*", markersize=10, markeredgecolor="black", markerfacecolor="green")

    ax.set(xlim=(minimum_x - 0.5, maximum_x + 0.5), xticks=np.arange(minimum_x, maximum_x, 0.5),
           ylim=(minimum_y - 0.5, maximum_y + 0.5), yticks=np.arange(minimum_y, maximum_y, 0.5))

    plt.show()