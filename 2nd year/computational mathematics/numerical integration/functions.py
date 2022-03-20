import math


def get_function(num):
    if (num == 1):
        return lambda x: x ** 3 + 2.28 * (x ** 2) - 1.934 * x - 3.907
    if (num == 2):
        return lambda x: x ** 2 - 3 * x - 2
    if (num == 3):
        return lambda x: math.sin(x) - math.cos(x) + 0.2*x


def get_function_name(num):
    if (num == 1):
        return "x^3 + 2.28x^2 - 1.934x - 3.907"
    if (num == 2):
        return "x^2 - 3x - 2"
    if (num == 3):
        return "sin(x) - cos(x) + 0.2x"


def get_break_function(num):
    if (num == 1):
        return lambda x: 1/x, 0
    if (num == 2):
        return lambda x: x**2 if x < 2 else 3*x, 2
    if (num == 3):
        return lambda x: x/abs(x), 0


def get_break_function_name(num):
    if (num == 1):
        return "1/x"
    if (num == 2):
        return "x^2 при x < 2, 3x при х >= 2"
    if (num == 3):
        return "sign(x)"
