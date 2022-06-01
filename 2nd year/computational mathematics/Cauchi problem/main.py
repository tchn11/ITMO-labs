import math

from functions import *
from calculations import *
from io_functions import *

func = ask_function()

x_0 = ask_float("Введите x0: ")
y_0 = ask_float("Введите y0: ")
a = 0
b = ask_float("Введите левую границу (b): ")
h = -1
while h <= 0:
    h = ask_float("Введите h: ")

e = -1
while e <= 0:
    e = ask_float("Введите e: ")


if x_0 <= b:
    ansR = rungeKuttMethod(func, a, b, x_0, y_0, h, e)
    ansA = adamsMethod(func, a, b, x_0, y_0, h, e)
    print("x      y_r     y_a     y_correct")
    if (len(ansR) < len(ansA)):
        for i in range(len(ansR)):
            for j in range(i, len(ansA)):
                if (ansA[j][0] >= ansR[i][0]):
                    y_correct = 0.5*(math.exp(ansA[i][0]) - math.cos(ansA[i][0]) - math.sin(ansA[i][0]))
                    print(f"{round(ansR[i][0], 3)}  {round(ansR[i][1], 3)}  {round(ansA[j][1], 3)}  {round(y_correct, 3)}")
                    break
    else:
        for i in range(len(ansA)):
            for j in range(i, len(ansR)):
                if (ansR[j][0] >= ansA[i][0]):
                    y_correct = 0.5*(math.exp(ansR[i][0]) - math.cos(ansR[i][0]) - math.sin(ansR[i][0]))
                    print(f"{round(ansA[i][0], 3)}   {round(ansR[j][1], 3)}   {round(ansA[i][1], 3)}  {round(y_correct, 3)}")
                    break
    print("Метод Рунге-Кута: \nx     y_r")
    for i in range(len(ansR)):
        print(f"{round(ansR[i][0], 3)}   {round(ansR[i][1], 3)}")

    print("Метод Адамса: \nx     y_a")
    for i in range(len(ansA)):
        print(f"{round(ansA[i][0], 3)}   {round(ansA[i][1], 3)}")

    plot(ansR, ansA, x_0, y_0)
else:
    print("Введите a < x0 < b")

