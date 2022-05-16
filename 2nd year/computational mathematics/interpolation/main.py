from calculations import *
from io_functions import *
from functions import *

if ask_input_data() == 1:
    x, y = get_points_file()
    temp_func = None
else:
    print("Введите границы интервала для набора точек")
    a = ask_num()
    b = ask_num()
    if a > b:
        a, b = b, a
    st = 0
    print("Введите шаг: ")
    while st <= 0:
        st = ask_num()
    x, y = get_points_from_func(func, a, b, st)
    temp_func = func

x_plot = np.linspace(np.min(x) - plot_area, np.max(x) + plot_area, 1000)
plot_lag_newton(x_plot, temp_func,
                [lagrange_method(x, y, x_now) for x_now in x_plot],
                [newton_method(x, y, x_now) for x_now in x_plot],
                [stirling_method(x, y, x_now) for x_now in x_plot],
                [bessel_method(x, y, x_now) for x_now in x_plot], x, y)
while True:
    print("Введите X: ")
    n = ask_num()
    print("Методом Лагранжа: ", lagrange_method(x, y, n))
    print("Методом Ньютона: ", newton_method(x, y, n))
    print("Методом Стирлинга: ", stirling_method(x, y, n))
    print("Методом Бесселя: ", bessel_method(x, y, n))