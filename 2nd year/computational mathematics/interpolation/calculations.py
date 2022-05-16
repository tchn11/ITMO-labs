import math


def lagrange_method(x_arr, y_arr, x):
    l = 0
    for j in range(len(y_arr)):
        numerator = 1
        denominator = 1
        for i in range(len(x_arr)):
            if i != j:
                numerator = numerator * (x - x_arr[i])
                denominator = denominator * (x_arr[j] - x_arr[i])
        l = l + y_arr[j] * numerator / denominator
    return l


def get_points_from_func(f, a, b, step):
    x = []
    y = []
    x_now = a
    while x_now <= b:
        x.append(x_now)
        y.append(f(x_now))
        x_now += step
    return x, y

def newton_method(x_arr, y_arr, x):
    diff = [y_arr]
    for i in range(len(x_arr)):
        tmp_dif = []
        for j in range(len(x_arr) - i - 1):
            tmp_dif.append((diff[-1][j + 1] - diff[-1][j])/(x_arr[j + i + 1] - x_arr[j]))
        diff.append(tmp_dif)
    mul = 1
    answer = y_arr[0]
    for i in range(len(x_arr)-1):
        mul *= (x - x_arr[i])
        answer += diff[i + 1][0] * mul
    return answer

#конечные разности
def final_differences(y_values):
    final_diff = [y_values]
    for i in range(len(y_values)-1):
        temp_fin_dif = []
        for j in range(len(final_diff[i]) - 1):
            diff = final_diff[i][j + 1] - final_diff[i][j]
            temp_fin_dif.append(diff)
        final_diff.append(temp_fin_dif)
    return final_diff


def create_factorial(n):
    result = []
    for i in range(n+1):
        fact = 1
        for j in range(1, i+1):
            fact *= j
        result.append(fact)
    return result


def stirling_method(x_arr, y_arr, x):
    if len(y_arr) % 2 == 0:
        return
    h = x_arr[1] - x_arr[0]
    #проверка, что все остальные разности одинаковые
    for i in range(len(y_arr) - 1):
        if round(x_arr[i + 1] - x_arr[i], 8) != round(h, 8):
            return
    fin_diff = final_differences(y_arr)
    fact = create_factorial(len(y_arr))
    mid = len(y_arr)//2
    t = (x - x_arr[mid]) / h
    result = y_arr[mid]

    for n in range(1, mid + 1):
        mul = 1
        for j in range(1, n):
            mul *= (t * t - j * j)
        result += 1 / fact[2 * n - 1] * t * mul * (fin_diff[2 * n - 1][-(n - 1) + mid] + fin_diff[2 * n - 1][-n + mid]) / 2
        result += 1 / fact[2 * n] * (t ** 2) * mul * (fin_diff[2 * n][-n + mid])
    return result

def bessel_method(x_arr, y_arr, x):
    if len(y_arr) % 2 == 1:
        return
    h = x_arr[1] - x_arr[0]
    for i in range(len(y_arr)-1):
        if round(x_arr[i+1] - x_arr[i], 8) != round(h, 8):
            return
    fin_diff = final_differences(y_arr)
    fact = create_factorial(len(y_arr))
    mid = (len(y_arr) - 2) // 2
    t = (x - x_arr[mid]) / h
    result = 0

    for n in range(0, mid + 1):
        mul = 1
        for j in range(1, n + 1):
            mul *= (t - j)*(t + j - 1)
        result += (1 / fact[2 * n]) * mul * (fin_diff[2 * n][-n + mid] + fin_diff[2 * n][-(n - 1) + mid]) / 2
        result += (1 / fact[2 * n + 1]) * (t - (1 / 2)) * mul * (fin_diff[2 * n + 1][-n + mid])
    return result
