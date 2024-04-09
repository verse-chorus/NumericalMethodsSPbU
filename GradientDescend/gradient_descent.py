import numpy as np
eps = 1e-08


def fun(x, y, z, N=None):
    print('Значение функции в точке минимума:')
    print(2 * x ** 2 + (3 + 0.1 * N) * y ** 2 + (4 + 0.1 * N) * z ** 2 + x * y - y * z + x * z + x - 2 * y + 3 * z + N)


# наискорейший градиентный спуск
def steepest_gradient_descent(matrix, vector):
    print('Вызвался метод Наискорейшего Градиентного Спуска\n')
    print('Номер N в списке группы:', N)
    print('matrix A:\n', matrix)
    print('vector b:\n', vector)

    eigen_values = np.linalg.eigvals(matrix)
    print('\nthe spectrum of A:')

    flag = 0
    for eig in eigen_values:
        print(eig)
        if eig < 0 or eig == 0:
            flag += 1
    min_eigen_val = min(eigen_values)
    max_eigen_val = max(eigen_values)
    print('min eigen value is:', min_eigen_val)
    print('max eigen value is:', max_eigen_val)

    if flag != 0:
        print('\nНеравенство '
              'm ||x||^2 <= (x, Ax) <= M ||x||^2\nне выполнено, '
              'так как есть отрицательное или '
              'равное нулю собственное число')
    else:
        print('\nНеравенство\n'
              'm ||x||^2 <= (x, Ax) <= M ||x||^2\n'
              'выполнено, так как собственные '
              'числа матрицы А положительны.')
        print('Значит, рассматриваемая функция удовл. усл-ю\n'
              'соотв. теоремы и эта функция имеет единств.'
              'точку минимума. Тогда условие\n'
              'm ||x||^2 <= (x, Ax) <= M ||x||^2\n'
              'запишется так:')
        print(min_eigen_val, '||x||^2 <= (x, Ax) <=', max_eigen_val, '||x||^2')

        print('Поэтому можно найти минимальное значение квадратичной функции\n'
              'как решение СЛАУ: Ax + b = 0')
        x_true = np.linalg.solve(matrix, -vector)

        # steepest_gradient_descend
        xk = np.array([
            [1],
            [0],
            [0]
        ])
        print('\nНачальное приближение x0:\n', xk)
        xk1 = np.array([
            [0],
            [0],
            [0]
        ])
        while np.linalg.norm(xk - xk1, 2) >= eps:
            xk1 = xk
            q = matrix.dot(xk) + vector
            Aq = matrix.dot(q)
            mu = (-1) * np.transpose(q).dot(q) / (np.transpose(q).dot(Aq) + eps)
            xk = xk + mu * q
        print('True min:\n', x_true + N)
        print('Approximated with gradient descent:\n', xk + N)
        print('Абсолютная погрешность:\n', np.linalg.norm(x_true - xk, 2))

        k = -1
        for coordinate in (xk + N):
            k += 1
            if k == 0:
                x = float(coordinate)
            elif k == 1:
                y = float(coordinate)
            elif k == 2:
                z = float(coordinate)

        fun(x, y, z, N)
        print('------------------------------------------------')


def steepest_coordinate_descent(matrix, vector):
    print('Вызвался метод Наискорейшего Покоординатного Спуска\n')
    print('Номер N в списке группы:', N)
    print('matrix A:\n', matrix)
    print('vector b:\n', vector)

    eigen_values = np.linalg.eigvals(matrix)
    print('\nthe spectrum of A:')

    flag = 0
    for eig in eigen_values:
        print(eig)
        if eig < 0 or eig == 0:
            flag += 1
    min_eigen_val = min(eigen_values)
    max_eigen_val = max(eigen_values)
    print('min eigen value is:', min_eigen_val)
    print('max eigen value is:', max_eigen_val)

    if flag != 0:
        print('\nНеравенство\n'
              'm ||x||^2 <= (x, Ax) <= M ||x||^2\nне выполнено, '
              'так как есть отрицательное или '
              'равное нулю собственное число')
    else:
        print('\nНеравенство\n'
              'm ||x||^2 <= (x, Ax) <= M ||x||^2\n'
              'выполнено, так как собственные '
              'числа матрицы А положительны.')
        print('Значит, рассматриваемая функция удовл. усл-ю\n'
              'соотв. теоремы и эта функция имеет единств.'
              'точку минимума. Тогда условие\n'
              'm ||x||^2 <= (x, Ax) <= M ||x||^2\n'
              'запишется так:')
        print(min_eigen_val, '||x||^2 <= (x, Ax) <=', max_eigen_val, '||x||^2')

        print('Поэтому можно найти минимальное значение квадратичной функции\n'
              'как решение СЛАУ: Ax + b = 0\n')
        x_true = np.linalg.solve(matrix, -vector)

    # наискорейший покоординатный спуск
    # e_i = (0, 0,.., (i позиция) 1, 0,...0) \in E^n

    xk = np.array([
        [1],
        [0],
        [0]
    ])
    print('Начальное приближение x0:\n', xk)
    xk1 = np.array([
        [0],
        [0],
        [0]
    ])

    e1 = np.array([
        [1],
        [0],
        [0]
    ])
    e2 = np.array([
        [0],
        [1],
        [0]
    ])
    e3 = np.array([
        [0],
        [0],
        [1]
    ])

    i = 0
    while np.linalg.norm(xk - xk1, 2) >= eps:
        i += 1
        if i % 2 != 0:
            xk1 = xk
            q = matrix.dot(xk) + vector
            Ae = matrix.dot(e2)
            mu = (-1) * np.transpose(e2).dot(q) / (np.transpose(e2).dot(Ae))
            xk = xk + mu * e2
        elif i % 3 != 0:
            xk1 = xk
            q = matrix.dot(xk) + vector
            Ae = matrix.dot(e3)
            mu = (-1) * np.transpose(e3).dot(q) / (np.transpose(e3).dot(Ae))
            xk = xk + mu * e3
        else:
            xk1 = xk
            q = matrix.dot(xk) + vector
            Ae = matrix.dot(e1)
            mu = (-1) * np.transpose(e1).dot(q) / (np.transpose(e1).dot(Ae))
            xk = xk + mu * e1

    print('True min:\n', x_true + N)
    print('Approximated with coordinate descent:\n', xk + N)
    print('Абсолютная погрешность:\n', np.linalg.norm(x_true - xk, 2))

    k = -1
    for coordinate in (xk + N):
        k += 1
        if k == 0:
            x = float(coordinate)
        elif k == 1:
            y = float(coordinate)
        elif k == 2:
            z = float(coordinate)

    fun(x, y, z, N)
    print('------------------------------------------------')


N = 10
A = np.array([
    [2*2, 1, 1],
    [1, 2 * (3 + 0.1 * N), -1],
    [1, -1, 2 * (4 + 0.1 * N)]
])
b = np.array([
    [1],
    [-2],
    [3]
])
steepest_gradient_descent(A, b)
steepest_coordinate_descent(A, b)


