import math


def get_func(num):
    if num == 1:
        return lambda x, y: math.sin(x) + y
    if num == 2:
        return lambda x, y: math.cos(x + y)
    if num == 3:
        return lambda x, y: x**3 - 2 * y
    if num == 4:
        return lambda x, y: (x - y) ** 2


def get_str_func(num):
    if num == 1:
        return "sin(x) + y"
    if num == 2:
        return "cos(x + y)"
    if num == 3:
        return "x^3 - 2y"
    if num == 4:
        return "(x - y)^2"
