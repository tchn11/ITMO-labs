import data_io
import numpy as np

#Поиск производной
def find_div(function, h = 0.0000001):
    return lambda x: (function(x + h) - function(x - h)) / (2 * h)

#Табличный способ поиска интервала
def find_interval(function, step):
    prev = -100
    for i in np.arange(-100, 100, step):
        if (function(i) * function(prev) < 0):
            #Если значения разного знака, то спрашиваем пользователя, оставить этот интервал или искать следующий
            print("Найден интервал: [" + str(prev) + ", " + str(i)+ "]")
            if (data_io.ask_continue() == 2):
                return prev, i
        prev = i
    print("Интервал не найден")
    return data_io.get_interval(function)

#Метод секущих
def secant_method(function, a, b, error):
    x_prev_prev = 0
    x_prev = 0
    #Поиск начального приближения
    if (function(a) * find_div(find_div(function))(a) > 0):
        print("В качестве начального приближения выбрано число: " + str(a))
        x_prev_prev = a
        x_prev = a + 0.001
    else:
        print("В качестве начального приближения выбрано число: " + str(b))
        x_prev_prev = b
        x_prev = b - 0.001
    x_current = x_prev * 1000 + 10
    iterations = 0
    #Поиск корней, отлавливаем условие остановки
    while (abs(x_current - x_prev_prev) > error):
        x_current = x_prev - ((x_prev - x_prev_prev) / (function(x_prev) - function(x_prev_prev))) * function(x_prev)
        x_prev_prev = x_prev
        x_prev = x_current
        iterations += 1
    return x_current, iterations

#Метод простой итерации
def iterrations_method(function, a, b, error):
    dev_a = find_div(function)(a)
    dev_b = find_div(function)(b)
    if (dev_a > dev_b):
        lyambd = -1 / dev_a
    else:
        lyambd = -1 / dev_b

    x_current = a
    x_prev = a * 1000 + 10

    iterations = 0

    #Поиск корней
    while (abs(x_prev - x_current) > error):
        x_prev = x_current
        x_current = x_prev + lyambd * function(x_prev)
        iterations += 1
        if (iterations > 100):
            print("Алгоритм расходится")
            exit()

    return x_current, iterations


#-------СИСТЕМЫ УРАВНЕНИЙ-------

#Поиск частных производных

#Частная производная по х
def calc_dx(function, x, y, h = 0.00000001):
    return (function(x + h, y) - function(x - h, y)) / (2 * h)

#Частная производная по у
def calc_dy(function, x, y, h = 0.00000001):
    return (function(x, y + h) - function(x, y - h)) / (2 * h)

#Поиск определителя матрицы Якоби
def calc_j(function1, function2, x, y):
    return calc_dx(function1, x, y) * calc_dy(function2, x, y) - calc_dx(function2, x, y) * calc_dy(function1, x, y)

#Поиск решений системы
def calc_newton_system(function1, function2, start_x, start_y, error):
    x_current = start_x
    y_current = start_y
    y_prev = y_current * 1000 + 10
    x_prev = x_current * 1000 + 10
    iterations = 0

    while max(abs(x_current - x_prev), abs(y_current - y_prev)) > error:
        x_prev = x_current
        y_prev = y_current
        J = calc_j(function1, function2, x_prev, y_prev)
        A = function1(x_prev, y_prev) / J
        B = function2(x_prev, y_prev) / J
        x_current = x_prev - A * calc_dy(function2, x_prev, y_prev) + B * calc_dy(function1, x_prev, y_prev)
        y_current = y_prev + A * calc_dx(function2, x_prev, y_prev) - B * calc_dx(function1, x_prev, y_prev)
        iterations += 1
        if (iterations == 100):
            print("Расходится")
            exit()
    return x_current, y_current, abs(x_current - x_prev), abs(y_current - y_prev), iterations