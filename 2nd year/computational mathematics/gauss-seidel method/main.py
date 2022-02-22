import math
import random

INPUT = "input.txt"


def get_matrix_file():
    # Получение матрицы из файла
    with open(INPUT, 'rt') as file:
        try:
            n = int(file.readline())
            acc = float(file.readline())
            max_iterations = int(file.readline())
            matrix = []
            for line in file:
                new_row = list(map(float, line.strip().split()))
                if len(new_row) != (n + 1):
                    raise ValueError
                matrix.append(new_row)
            if len(matrix) != n:
                raise ValueError
        except ValueError:
            return None
    return matrix, n, acc, max_iterations


def get_matrix_input():
    #Получение матрицы с клавиатуры
    while True:
        try:
            n = int(input("Порядок матрицы: "))
            if n <= 0:
                print("Порядок матрицы должен быть положительным.")
            else:
                break
        except ValueError:
            print("Порядок матрицы должен быть целым числом.")
    while True:
        try:
            acc = float(input("Точность ответа: "))
            if acc <= 0:
                print("Точность матрицы должна быть положительным числом.")
            else:
                break
        except ValueError:
            print("Точность матрицы должна быть числом.")
    while True:
        try:
            max_iterations = int(input("Максимальное количество итераций: "))
            if max_iterations <= 0:
                print("Максимальное количество итераций должно быть положительным числом.")
            else:
                break
        except ValueError:
            print("Максимальное количество итераций должно быть целым числом.")
    matrix = []
    print("Введите коэффициенты матрицы через пробел:")
    try:
        for i in range(n):
            matrix.append(list(map(float, input().strip().split())))
            if len(matrix[i]) != (n + 1):
                raise ValueError
    except ValueError:
        return None
    return matrix, n, acc, max_iterations

def get_random_matrix():
    #Создание рандомной матрицы заданного размера
    while True:
        try:
            n = int(input("Порядок матрицы: "))
            if n <= 0:
                print("Порядок матрицы должен быть положительным числом.")
            else:
                break
        except ValueError:
            print("Порядок матрицы должен быть целым числом.")
    while True:
        try:
            acc = float(input("Точность ответа: "))
            if acc <= 0:
                print("Точность матрицы должна быть положительным числом.")
            else:
                break
        except ValueError:
            print("Точность матрицы должна быть числом.")
    while True:
        try:
            max_iterations = int(input("Максимальное количество итераций: "))
            if max_iterations <= 0:
                print("Максимальное количество итераций должно быть положительным числом.")
            else:
                break
        except ValueError:
            print("Максимальное количество итераций должно быть целым числом.")
    matrix = [[0]*(n+1) for i in range(n)]
    for i in range(n):
        for j in range(n + 1):
            matrix[i][j] = random.randrange(1, 10)
    return matrix, n, acc, max_iterations


#Проверка диагонального преобладания
def check_diagonal(matrix):
    not_equals = 0
    for i in range(len(matrix)):
        sum = 0
        for j in range(len(matrix)):
            if (i == j):
                continue
            sum += abs(matrix[i][j])
        if (sum > abs(matrix[i][i])):
            return False
        if (sum < abs(matrix[i][i])):
            not_equals = 1
            #Если хотя бы для одного строго меньше, то всё классссс
    if not_equals == 0:
        return False
    return True

def print_array(matrix):
    for i in range(len(matrix)):
        output = ""
        for j in range(len(matrix)):
            output += str(matrix[i][j]) + " "
        output += "| " + str(matrix[i][len(matrix)])
        print(output)


# main logic

input_format = ""

while input_format != "1" and input_format != "2" and input_format != "3":
    input_format = input("Введите \"1\" - чтобы ввести матрицу с клавиатуры, \"2\" - чтобы взять матрицу из файла, \"3\" - заполнить случайными числами: ")

if input_format == "1":
    array, n, epsilon, max_iterations = get_matrix_input()
elif input_format == "2":
    array, n, epsilon, max_iterations = get_matrix_file()
else:
    array, n, epsilon, max_iterations = get_random_matrix()

print("Полученный массив:")

print_array(array)

print("Полученная точность: " + str(epsilon))

print("Полученное количество итераций: " + str(max_iterations))

#Сохранение порядка неизвестных первоначальной матрицы
answer_order = [i for i in range(n)]

if (check_diagonal(array)):
    print("Выполнено условие преобладания диагональных элементов")
else:
    print("Не выполнено условие преобладания диагональных элементов")
    for i in range(n):
        max_element = array[i][0]
        max_index = 0
        for j in range(n):
            if (array[j][i] > max_element):
                max_element = array[j][i]
                max_index = j
        if (max_index != i and max_element != array[i][i]):
            #Меняем местами столбцы, если максимальный элемент не на диагонали
            for j in range(n):
                array[j][i], array[j][max_index] = array[j][max_index], array[j][i]
            answer_order[i], answer_order[max_index] = answer_order[max_index], answer_order[i]

        if(check_diagonal(array)):
            #проверим, не выполняется ли теперь условие диагонального преобладания. если да, то всё супер
            break
    if (check_diagonal(array)):
        print("Матрицу получилось привести к виду, в котором выполняется условие преобладания диагонали")
    else:
        print("Матрицу не получилось привести к виду, в котором выполняется условие преобладания диагонали")
    print("Новый вид матрицы:")
    print_array(array)

for i in range(n):
    if (array[i][i] == 0):
        #Если на диагонали вдруг оказался ноль, значит будет деление на ноль, а это очень плохо :(
        print("Не получится найти ответ, так как на диагонали есть 0")
        exit()

vector_old_ans = [0]*n
vector_ans = [0]*n
difference = epsilon + 1
num = 0
found_answer = True
errors = [0]*n

while difference > epsilon:
    for i in range (n):
        sum = 0
        for j in range (n):
            if i!=j:
              sum += array[i][j]/array[i][i]*vector_ans[j]
        vector_ans[i] = array[i][n]/array[i][i] - sum
    for i in vector_ans:
        if i == None or i == math.inf or i == -math.inf:
            print("Значения расходятся, невозможно найти ответ")
            found_answer = False
            exit(0)

    max_difference = 0.0
    for i in range(n):
        errors[i] = abs(vector_old_ans[answer_order[i]] - vector_ans[answer_order[i]])
        if abs(vector_old_ans[i] - vector_ans[i])>max_difference:
            max_difference = abs(vector_old_ans[i] - vector_ans[i])
    difference = max_difference
    for i in range(n):
        vector_old_ans[i] = vector_ans[i]
    num += 1
    if (num >= max_iterations):
        print("Не удалось получить ответ за максимальное количество итераций")
        found_answer = False
        break

if (found_answer):
    print("Ответ найден за " + str(num) + " итераций:")
    # print(vector_ans)
    print([vector_ans[answer_order[i]] for i in range(n)])
    print("Вектор погрешностей:")
    print(errors)