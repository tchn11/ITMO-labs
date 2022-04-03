import math
import numpy as np

def get_points_func(func, a, b, step):
    points = []
    iteration = a
    while iteration <= b:
        try:
            points.append([iteration, func(iteration)])
        except Exception:
            print(f"Нельзя найти точку от {round(iteration, 3)}")
        iteration += step
        iteration = round(iteration, 3)
    return points

#решение СЛАУ методом Гаусса-Зейделя
def calc_system(array, n):
    max_iterations = 100000
    epsilon = 0.00001
    vector_old_ans = [0] * n
    vector_ans = [0] * n
    difference = epsilon + 1
    num = 0
    found_answer = True
    errors = [0] * n

    while difference > epsilon:
        for i in range(n):
            sum = 0
            for j in range(n):
                if i != j:
                    sum += array[i][j] / array[i][i] * vector_ans[j]
            vector_ans[i] = array[i][n] / array[i][i] - sum
        for i in vector_ans:
            if i == None or i == math.inf or i == -math.inf:
                print("Значения расходятся, невозможно найти ответ")
                found_answer = False
                exit(0)

        max_difference = 0.0
        for i in range(n):
            if abs(vector_old_ans[i] - vector_ans[i]) > max_difference:
                max_difference = abs(vector_old_ans[i] - vector_ans[i])
        difference = max_difference
        for i in range(n):
            vector_old_ans[i] = vector_ans[i]
        num += 1
        if (num >= max_iterations):
            print("Не удалось получить ответ за максимальное количество итераций")
            found_answer = False
            break
    return vector_ans

#Линейная аппроксимация
def linear_approximate(points):

    n = len(points)

    summ_x = 0
    for i in range(n):
        summ_x += points[i][0]

    summ_x_sqd = 0
    for i in range(n):
        summ_x_sqd += points[i][0]**2

    summ_y = 0
    for i in range(n):
        summ_y += points[i][1]

    summ_x_y = 0
    for i in range(n):
        summ_x_y += points[i][0] * points[i][1]

    #коэффициент корреляции Пирсона
    mid_x = summ_x / n
    mid_y = summ_y / n
    #числитель
    summ_1 = 0
    for i in range(n):
        summ_1 += (points[i][0] - mid_x) * (points[i][1] - mid_y)
    #знаменатель (суммы 2 и 3)
    summ_2 = 0
    for i in range(n):
        summ_2 += (points[i][0] - mid_x) ** 2
    summ_3 = 0
    for i in range(n):
        summ_3 += (points[i][1] - mid_y) ** 2
        
    try:
        r = (summ_1)/(math.sqrt(summ_2*summ_3))
        print(f"Коэффициент корреляции Пирсона равен: {round(r, 3)}")
    except Exception:
        print("Не получилось посчитать коэффициент корреляции Пирсона")
    ans = calc_system([[summ_x_sqd, summ_x, summ_x_y],[summ_x, n, summ_y]], 2)

    result_func = lambda x: ans[0]*x + ans[1]

    str_result_func = f"{round(ans[0], 3)}x + {round(ans[1], 3)}"

    #среднеквадратичное отклонение
    errors = [(points[i][1] - result_func(points[i][0]))**2 for i in range(n)]
    mid_sqd_err = math.sqrt(sum(errors)/n)

    return result_func, str_result_func, errors, mid_sqd_err

#Квадратичная аппроксимация
def squad_approximate(points):
    n = len(points)
    summ_x = 0
    for i in range(n):
        summ_x += points[i][0]

    summ_x_sqd = 0
    for i in range(n):
        summ_x_sqd += points[i][0]**2

    summ_x_qub = 0
    for i in range(n):
        summ_x_qub += points[i][0]**3

    summ_x_forth = 0
    for i in range(n):
        summ_x_forth += points[i][0]**4

    summ_y = 0
    for i in range(n):
        summ_y += points[i][1]

    summ_x_y = 0
    for i in range(n):
        summ_x_y += points[i][0] * points[i][1]

    summ_x_sqd_y = 0
    for i in range(n):
        summ_x_sqd_y += (points[i][0]**2) * points[i][1]

    system = [
        [n, summ_x, summ_x_sqd, summ_y],
        [summ_x, summ_x_sqd, summ_x_qub, summ_x_y],
        [summ_x_sqd, summ_x_qub, summ_x_forth, summ_x_sqd_y]
    ]

    ans = calc_system(system, 3)

    result_func = lambda x: ans[2]*(x**2) + ans[1]*x + ans[0]

    str_result_func = f"{round(ans[2], 3)}x^2 + {round(ans[1], 3)}x + {round(ans[0], 3)}"

    #СКО
    errors = [(points[i][1] - result_func(points[i][0]))**2 for i in range(n)]
    mid_sqd_err = math.sqrt(sum(errors)/n)

    return result_func, str_result_func, errors, mid_sqd_err

#Кубическая аппроксимация
def qub_approximate(points):
    n = len(points)

    summ_x = 0
    for i in range(n):
        summ_x += points[i][0]

    summ_x_sqd = 0
    for i in range(n):
        summ_x_sqd += points[i][0]**2

    summ_x_qub = 0
    for i in range(n):
        summ_x_qub += points[i][0]**3

    summ_x_forth = 0
    for i in range(n):
        summ_x_forth += points[i][0]**4

    summ_x_fifth = 0
    for i in range(n):
        summ_x_fifth += points[i][0]**5

    summ_x_six = 0
    for i in range(n):
        summ_x_six += points[i][0] ** 6

    summ_y = 0
    for i in range(n):
        summ_y += points[i][1]

    summ_x_y = 0
    for i in range(n):
        summ_x_y += points[i][0] * points[i][1]

    summ_x_sqd_y = 0
    for i in range(n):
        summ_x_sqd_y += (points[i][0]**2) * points[i][1]

    summ_x_cub_y = 0
    for i in range(n):
        summ_x_cub_y += (points[i][0] ** 3) * points[i][1]

    system = [
        [n, summ_x, summ_x_sqd, summ_x_qub, summ_y],
        [summ_x, summ_x_sqd, summ_x_qub, summ_x_forth, summ_x_y],
        [summ_x_sqd, summ_x_qub, summ_x_forth, summ_x_fifth, summ_x_sqd_y],
        [summ_x_qub, summ_x_forth, summ_x_fifth, summ_x_six,summ_x_cub_y]
    ]

    ans = calc_system(system, 4)

    result_func = lambda x: ans[3]*(x**3) + ans[2]*(x**2) + ans[1]*x + ans[0]

    str_result_func = f"{round(ans[3], 3)}x^3 + {round(ans[2], 3)}x^2 + {round(ans[1], 3)}x + {round(ans[0], 3)}"

    #СКО
    errors = [(points[i][1] - result_func(points[i][0]))**2 for i in range(n)]
    mid_sqd_err = math.sqrt(sum(errors)/n)

    return result_func, str_result_func, errors, mid_sqd_err

#Степенная аппроксимация
def degree_approximate(input_points):
    points = []

    #добавляем в массив только те точки, которые подходят по ОДЗ логарифма
    for i in input_points:
        if i[1] > 0 and i[0] > 0:
            points.append(i)

    #if len(points) < 2:, но это будет неидеальная аппроксимация
    if len(points) != len(input_points):
        return None, None, None, None

    n = len(points)
    summ_x = 0
    for i in range(n):
        summ_x += math.log(points[i][0])

    summ_x_sqd = 0
    for i in range(n):
        summ_x_sqd += math.log(points[i][0]) ** 2

    summ_y = 0
    for i in range(n):
        summ_y += math.log(points[i][1])

    summ_x_y = 0
    for i in range(n):
        summ_x_y += math.log(points[i][0]) * math.log(points[i][1])

    try:
        ans = calc_system([[summ_x_sqd, summ_x, summ_x_y], [summ_x, n, summ_y]], 2)
    except Exception:
        return None, None, None, None
    result_func = lambda x: np.exp(ans[1])*(x ** ans[0])

    str_result_func = f"{round(math.exp(ans[1]), 3)}x^{round(ans[0], 3)}"

    #СКО
    errors = [(points[i][1] - result_func(points[i][0])) ** 2 for i in range(n)]
    mid_sqd_err = math.sqrt(sum(errors) / n)

    return result_func, str_result_func, errors, mid_sqd_err

 #экспоненциальная аппроксимация
def exp_approximate(input_points):
    points = []

    for i in input_points:
        if i[1] > 0:
            points.append(i)

    #if len(points) < 2:, но это будет неидеальная аппроксимация
    if len(points) != len(input_points):
        return None, None, None, None

    n = len(points)
    summ_x = 0
    for i in range(n):
        summ_x += points[i][0]

    summ_x_sqd = 0
    for i in range(n):
        summ_x_sqd += points[i][0] ** 2

    summ_y = 0
    for i in range(n):
        summ_y += math.log(points[i][1])

    summ_x_y = 0
    for i in range(n):
        summ_x_y += points[i][0] * math.log(points[i][1])
    try:
        ans = calc_system([[summ_x_sqd, summ_x, summ_x_y], [summ_x, n, summ_y]], 2)
    except Exception:
        return None, None, None, None
    result_func = lambda x: np.exp(ans[1])* np.exp(ans[0]*x)

    str_result_func = f"{round(math.exp(ans[1]), 3)}e^{round(ans[0], 3)}*x"
    #СКО
    errors = [(points[i][1] - result_func(points[i][0])) ** 2 for i in range(n)]
    mid_sqd_err = math.sqrt(sum(errors) / n)

    return result_func, str_result_func, errors, mid_sqd_err

#Логарифмическая аппроксимация
def ln_approximate(input_points):
    points = []

    for i in input_points:
        if i[0] > 0:
            points.append(i)

    # if len(points) < 2:, но это будет неидеальная аппроксимация
    if len(points) != len(input_points):
        return None, None, None, None

    n = len(points)

    summ_x = 0
    for i in range(n):
        summ_x += math.log(points[i][0])

    summ_x_sqd = 0
    for i in range(n):
        summ_x_sqd += math.log(points[i][0]) ** 2

    summ_y = 0
    for i in range(n):
        summ_y += points[i][1]

    summ_x_y = 0
    for i in range(n):
        summ_x_y += math.log(points[i][0]) * points[i][1]

    try:
        ans = calc_system([[summ_x_sqd, summ_x, summ_x_y], [summ_x, n, summ_y]], 2)
    except Exception:
        return None, None, None, None
    result_func = lambda x: ans[0]* np.log(x) + ans[1]

    str_result_func = f"{round(ans[0], 3)} ln(x) + {round(ans[1], 3)}"

    #СКО
    errors = [(points[i][1] - result_func(points[i][0])) ** 2 for i in range(n)]
    mid_sqd_err = math.sqrt(sum(errors) / n)

    return result_func, str_result_func, errors, mid_sqd_err