import functions
import functions as fn
import matplotlib.pyplot as plt
import numpy as np
import sympy as sp


#Отрисовка графика
def plot_function(func, min_x, max_x, min_y, max_y, step):
    x = np.linspace(min_x, max_x, 10000)

    fig = plt.figure()
    ax = fig.add_subplot(1, 1, 1)
    ax.spines['left'].set_position('center')
    ax.spines['bottom'].set_position('center')
    ax.spines['right'].set_color('none')
    ax.spines['top'].set_color('none')
    ax.xaxis.set_ticks_position('bottom')
    ax.yaxis.set_ticks_position('left')

    ax.plot(x, func(x), "g", linewidth=2.0)

    ax.set(xlim=(min_x, max_x), xticks=np.arange(min_x, max_x, step),
           ylim=(min_y, max_y), yticks=np.arange(min_y, max_y, step))

    plt.show()


def get_function_num():
    function = -1
    while function != 1 and function != 2 and function != 3:
        for i in range(1, 4):
            print(str(i) + " - " + fn.get_function_name(i))
        try:
            function = int(input("Введите номер желаемой функции: "))
        except ValueError:
            print("Номер функции должен быть числом")
    return function


def get_interval_input_type():
    type = 0
    while type != 1 and type != 2:
        try:
            type = int(input("Введите 1, чтобы ввести интервал, 2, чтобы запустить автоматический поиск интервала: "))
        except ValueError:
            print("Введите 1 или 2")
    return type

#Ввод интервала и проверка его корректности
def get_interval(function):
    a, b = 0, 0
    while True:
        try:
            a, b = map(float, input("Введите границы интервала через пробел: ").split())
            if a > b:
                a, b = b, a
            elif a == b:
                raise ArithmeticError
            elif function(a) * function(b) >= 0:
                raise AttributeError
            break
        except ValueError:
            print("Границы интервала должны быть числами, введёнными через пробел.")
        except ArithmeticError:
            print("Границы интервала не могут быть равны.")
        except AttributeError:
            print("Интервал содержит ноль или обе точки одного знака.")
    return a, b

def ask_continue():
    type = 0
    while type != 1 and type != 2:
        try:
            type = int(input("Введите 1, чтобы найти следующий интервал, 2, чтобы выбрать этот интервал: "))
        except ValueError:
            print("Введите 1 или 2")
    return type

def ask_methods():
    type = 0
    while type != 1 and type != 2:
        try:
            type = int(input("Введите 1, чтобы выбрать метод секущих, 2 чтобы выбрать метод простой итерации: "))
        except ValueError:
            print("Введите 1 или 2")
    return type

def ask_error():
    number = 0
    while True:
        try:
            number = float(input("Введите точность: "))
            if (number > 0):
                return number
            print("Число должно быть положительным")
        except ValueError:
            print("Введите число")

def ask_task():
    type = 0
    while type != 1 and type != 2:
        try:
            type = int(input("Введите 1, чтобы выбрать одно уравнение, 2, чтобы выбрать систему уравнений: "))
        except ValueError:
            print("Введите 1 или 2")
    return type

#------Системы----

#Построение графика системы
def plot_system(num1, num2):
    x, y = sp.symbols("x y")
    sp.plot_implicit(sp.Or(sp.Eq(functions.get_system_function(num1)(x, y), 0), sp.Eq(
        functions.get_system_function(num2)(x, y), 0)))

#Выбор функций для системы
def get_system_function_num():
    function1, function2 = -1, -1
    while True:
        for i in range(1, 4):
            print(str(i) + " - " + fn.get_system_function_name(i))
        try:
            function1 = int(input("Введите номер первой функции: "))
            function2 = int(input("Введите второй первой функции: "))
            if (function1 == 1 or function1 == 2 or function1 == 3):
                if (function2 == 1 or function2 == 2 or function2 == 3):
                    if (function1 != function2):
                        return function1, function2
            print("Числа должны быть разными и от 1 до 3")

        except ValueError:
            print("Должен быть числом")

#Получение начального приближения
def get_start():
    a, b = 0, 0
    while True:
        try:
            a, b = map(float, input("Введите начальное приближение через пробел: ").split())
            break
        except ValueError:
            print("Начальное приближение должно быть числами, введенными через пробел.")
    return a, b