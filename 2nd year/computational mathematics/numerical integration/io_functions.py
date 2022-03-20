from functions import *

def ask_mode():
    mode = 0
    while mode != 1 and mode != 2:
        try:
            mode = int(input("Введите 1 для функций без разрыва, 2 для функций с разрывом: "))
        except Exception:
            print("Введите число")
    return mode


def ask_a_b():
    a = 0
    b = 0
    while True:
        try:
            a = float(input("Введите точку а: "))
            b = float(input("Введите точку b: "))
            if a == b:
                print("введите разные числа")
                continue
            break
        except Exception:
            print("Введите число")
    return a, b

def ask_error():
    err = 0
    while True:
        try:
            err = float(input("Введите точность: "))
            if err <= 0:
                print("число должно быть больше нуля")
                continue
            break
        except Exception:
            print("Введите число")
    return err


def ask_function():
    for i in range(3):
        print(f"{i+1}: {get_function_name(i+1)}")
    num = 0
    while True:
        try:
            num = int(input("Выберите функцию: "))
            if num <= 0:
                print("число должно быть больше нуля")
                continue
            if num > 3:
                print("Число должно быть больше трёх")
                continue
            break
        except Exception:
            print("Введите число")
    return num

def ask_break_function():
    for i in range(3):
        print(f"{i+1}: {get_break_function_name(i+1)}")
    num = 0
    while True:
        try:
            num = int(input("Выберите функцию: "))
            if num <= 0:
                print("число должно быть больше нуля")
                continue
            if num > 3:
                print("Число должно быть больше трёх")
                continue
            break
        except Exception:
            print("Введите число")
    return num



def ask_method():
    method = 0
    while method != 1 and method != 2 and method != 3 and method != 4:
        try:
            method = int(input("Введите 1 для метода прямоугольника левого, 2 для метода прямоугольника правого, 3 для метода прямоугольника среднего, 4 для метода трапеций: "))
        except Exception:
            print("Введите число")
    return method


def ask_method_with_simpson():
    method = 0
    while method != 1 and method != 2 and method != 3 and method != 4 and method != 5:
        try:
            method = int(input("Введите 1 для метода прямоугольника левого, 2 для метода прямоугольника правого, 3 для метода прямоугольника среднего, 4 для метода трапеций, 5 для метода Симпсона: "))
        except Exception:
            print("Введите число")
    return method

