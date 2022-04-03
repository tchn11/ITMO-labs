import math

from calculations import *
from input import *
from functions import *

mode = ask_input_data()

if mode == 1:
    points = get_points_file()
elif mode == 2:
    points = get_points_console()
else:
    points = get_points_func(input_func, -2, 0, 0.2)

print(f"Полученные точки: {points}")
print()
linear_func, linear_str_func, linear_err, linear_squad_err = linear_approximate(points)
print(f"Линейной аппроксимацией получена функция: {linear_str_func}, S = {round(sum(linear_err), 3)}, sigma = {round(linear_squad_err, 3)}")
print()
squad_func, squad_str_func, squad_err, squad_squad_err = squad_approximate(points)
print(f"Квадратичной аппроксимацией получена функция: {squad_str_func}, S = {round(sum(squad_err), 3)}, sigma = {round(squad_squad_err, 3)}")
print()
cub_func, cub_str_func, cub_err, cub_squad_err = qub_approximate(points)
print(f"Кубической аппроксимацией получена функция: {cub_str_func}, S = {round(sum(cub_err), 3)}, sigma = {round(cub_squad_err, 3)}")
print()
exp_func, exp_str_func, exp_err, exp_squad_err = exp_approximate(points)
if exp_func is None:
    print("Нет ни одной точки в области определения экспоненциальной функции")
    exp_squad_err = math.inf
else:
    print(f"Экспоненциальной аппроксимацией получена функция: {exp_str_func}, S = {round(sum(exp_err), 3)}, sigma = {round(exp_squad_err, 3)}")
print()
log_func, log_str_func, log_err, log_squad_err = ln_approximate(points)
if log_func is None:
    print("Нет ни одной точки в области определения логарифмический функции")
    log_squad_err = math.inf
else:
    print(f"Логарифмической аппроксимацией получена функция: {log_str_func}, S = {round(sum(log_err), 3)}, sigma = {round(log_squad_err, 3)}")
print()
deg_func, deg_str_func, deg_err, deg_squad_err = degree_approximate(points)
if deg_func is None:
    print("Нет ни одной точки в области опредения степенной функции")
    deg_squad_err = math.inf
else:
    print(f"Степенной апроксимацией получена функция: {deg_str_func}, S = {round(sum(deg_err), 3)}, sigma = {round(deg_squad_err, 3)}")
print()

min_r = min(linear_squad_err, squad_squad_err, cub_squad_err, exp_squad_err, log_squad_err, deg_squad_err)

print(f"Минимальное среднеквадратичное отклонение: {round(min_r, 3)}")
if min_r == linear_squad_err:
    print("Лучшая аппроксимация: линейная")
elif min_r == squad_squad_err:
    print("Лучшая аппроксимация: квадратичная")
elif min_r == cub_squad_err:
    print("Лучшая аппроксимация: кубическая")
elif min_r == exp_squad_err:
    print("Лучшая аппроксимация: экспоненциальная")
elif min_r == log_squad_err:
    print("Лучшая аппроксимация: логарфимическая")
elif min_r == deg_squad_err:
    print("Лучшая аппроксимация: степенная")

ploting(points, linear_func, squad_func, cub_func, exp_func, log_func, deg_func)
