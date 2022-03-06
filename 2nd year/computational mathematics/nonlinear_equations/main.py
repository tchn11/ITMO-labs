import functions
import functions as fn
import data_io
import calculations

def find_solution():

    fn_num = data_io.get_function_num()

    function = fn.get_function(fn_num)

    data_io.plot_function(fn.get_function(fn_num), -9, 9, -9, 9, 1)

    interval_type = data_io.get_interval_input_type()

    if (interval_type == 1):
        a, b = data_io.get_interval(function)
    else:
        a, b = calculations.find_interval(function, 0.5)

    print("a = " + str(a) + ", " + "b = " + str(b))

    error = data_io.ask_error()

    if (data_io.ask_methods() == 1):
        ans, iterations = calculations.secant_method(function, a, b, error)
    else:
        ans, iterations = calculations.iterrations_method(function, a, b, error)

    print("Корень: " + str(ans) + " найден за " + str(iterations) + " итераций, f(x) = " + str(function(ans)))

    data_io.plot_function(function, a - 0.5, b + 0.5, function(a) - 0.5, function(b) + 0.5, 0.2)

def find_system():
    num1, num2 = data_io.get_system_function_num()
    data_io.plot_system(num1, num2)
    start_x, start_y = data_io.get_start()
    error = data_io.ask_error()
    x, y, err_x, err_y, it = calculations.calc_newton_system(functions.get_system_function(num1),
                                                             functions.get_system_function(num2), start_x, start_y, error)
    print("x = " + str(x) + ", y = " + str(y) + ", найден за " + str(it) + " итераций, вектор погрешностей: [" + str(err_x) + ", " + str(err_y) + "]")

if data_io.ask_task() == 1:
    find_solution()
else:
    find_system()