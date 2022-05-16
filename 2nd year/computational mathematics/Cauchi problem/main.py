from functions import *
from calculations import *
from io_functions import *

func = ask_function()

x_0 = ask_float("Введите x0: ")
y_0 = ask_float("Введите y0: ")
a = ask_float("Введите правую границу (a): ")
b = ask_float("Введите левую границу (b): ")
h = -1
while h <= 0:
    h = ask_float("Введите h: ")

e = -1
while e <= 0:
    e = ask_float("Введите e: ")


if a <= x_0 <= b:
    ansR = rungeKuttMethod(func, a, b, x_0, y_0, h, e)
    ansA = adamsMethod(func, a, b, x_0, y_0, h, e)

    plot(ansR, ansA, x_0, y_0)
else:
    print("Введите a < x0 < b")

