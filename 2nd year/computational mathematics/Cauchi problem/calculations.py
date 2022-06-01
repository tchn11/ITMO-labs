

def rungeKuttNext(func, x, y, h):
    k1 = h * func(x, y)
    k2 = h * func(x + h / 2, y + k1 / 2)
    k3 = h * func(x + h / 2, y + k2 / 2)
    k4 = h * func(x + h, y + k3)
    return y + (1 / 6) * (k1 + 2 * k2 + 2 * k3 + k4)

def rungeKutt(func, x_0, y_0, h, b, e):
    y_prev = y_0
    y_current = 0
    answer_x = [x_0]
    answer_y = [y_0]
    x_prev = x_0
    y_current_h_div_2 = y_0 * 1000 + 10000
    print("h    y_h    y_h/2")
    while (abs(y_current_h_div_2 - y_current) > e):
        y_current = rungeKuttNext(func, x_prev, y_prev, h)
        y_current_h_div_1 = rungeKuttNext(func, x_prev, y_prev, h/2)
        y_current_h_div_2 = rungeKuttNext(func, x_prev + h/2, y_current_h_div_1, h/2)
        print(h, y_current, y_current_h_div_2, )
        h /= 2

    h *= 2

    while x_prev < b:
        y_current = rungeKuttNext(func, x_prev, y_prev, h)

        answer_y.append(y_current)
        answer_x.append(x_prev + h)
        x_prev += h
        y_prev = y_current
    return list(zip(answer_x, answer_y))


def rungeKuttMethod(func, a, b, x_0, y_0, h, e):
    ans = rungeKutt(func, x_0, y_0, h, b, e)
    return ans


def adams(func, x_0, y_0, h, n):
    answer_x = []
    answer_y = []
    answer_x.append(x_0)
    answer_y.append(y_0)
    y_1 = rungeKuttNext(func, x_0, y_0, h)
    func_prev_1 = func(x_0 + h, y_1)
    answer_x.append(x_0 + h)
    answer_y.append(y_1)
    y_2 = rungeKuttNext(func, x_0 + h, y_1, h)
    func_prev_2 = func(x_0 + 2 * h, y_2)
    answer_x.append(x_0 + 2*h)
    answer_y.append(y_2)
    y_3 = rungeKuttNext(func, x_0 + 2 * h, y_2, h)
    func_prev_3 = func(x_0 + 3 * h, y_3)
    answer_x.append(x_0 + 3*h)
    answer_y.append(y_3)
    y_prev = rungeKuttNext(func, x_0 + 3 * h, y_3, h)
    func_prev_4 = func(x_0 + 4 * h, y_prev)
    answer_x.append(x_0 + 4*h)
    answer_y.append(y_prev)
    for i in range(5, n + 1):
        delta_f_i = func_prev_4 - func_prev_3
        delta2_f_i = func_prev_4 - 2 * func_prev_3 + func_prev_2
        delta3_f_i = func_prev_4 - 3 * func_prev_3 + 3 * func_prev_2 - func_prev_1
        x_next = x_0 + i * h
        x_prev = x_0 + (i - 1) * h
        y_next = y_prev + h * func(x_prev, y_prev) + ((h ** 2) / 2) * delta_f_i + ((5 * (h ** 3)) / 12) * delta2_f_i + \
                 ((3 * (h ** 4)) / 8) * delta3_f_i

        answer_x.append(x_next)
        answer_y.append(y_next)
        func_prev_1 = func_prev_2
        func_prev_2 = func_prev_3
        func_prev_3 = func_prev_4
        func_prev_4 = func(x_next, y_next)
        y_prev = y_next
    return list(zip(answer_x, answer_y))

def adamsMethod(func, a, b, x_0, y_0, h, e):
    ans = adams(func, x_0, y_0, h, int((b - x_0) / h))
    return ans