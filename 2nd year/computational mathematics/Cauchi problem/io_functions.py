import matplotlib.pyplot as plt
from functions import *

def plot(runge, adams, x_0, y_0):
    x = [a[0] for a in runge]
    y = [a[1] for a in runge]
    plt.plot(x, y, label="Runge Kutt Method")
    x = [a[0] for a in adams]
    y = [a[1] for a in adams]
    plt.plot(x, y, label="Adams Method")
    plt.plot(x_0, y_0, marker="o", linewidth=0, label="Solution")
    plt.legend()
    plt.show()

def ask_float(str):
    num = 0
    while True:
        try:
            num = float(input(str))
            return num
        except Exception:
            print("Введите число")

def ask_function():
    for i in range(1, 5):
        print(i, ": y' = ", get_str_func(i))
    num  = 0
    while num < 1 or num > 4:
        try:
            num = int(input("Выберите функцию: "))
        except Exception:
            print("Введите число")
    return get_func(num)