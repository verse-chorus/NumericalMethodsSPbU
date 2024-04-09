#include <iostream>
#include <cmath>

using namespace std; 

// факториал
long factorial(int k) 
{
    long fact = 1;
    for (int i = 2; i <= k; i++) {fact *= i;}
    return(fact);
}

// синус через ряд Тейлора
double sinus(double x, double eps) 
{
    int k = 1;
    double sum = x;
    double h = x;
    while (abs(h) > eps) 
    {
        h = pow(-1, k) * pow(x, 2 * k + 1) / factorial(2 * k + 1);
        sum += h;
        k++;
    }
    return (sum);
}

// разложение экспоненты в ряд Тейлора
double exponent(double x, double eps) 
{
    int k = 1;
    double sum = 1;
    double h = x;
    while (abs(h) > eps && k > (abs(x)-2)) 
    {
        h = pow(x, k) / factorial(k);
        sum += h;
        k++;
    }
    return (sum);
}

// реализация вычислиения корня квадратного по формуле Герона
double square_Root(double x, double eps)
{
    double sq_1 = 1;
    if (x < 1) {double sq_1 = 1;}
    else {double sq_1 = x;}
    double sq_2 = (sq_1 + x / sq_1) / 2;
    int k = 1;
    while (abs(sq_1 - sq_2) >= eps)
    {
        k++;
        sq_1 = sq_2;
        sq_2 = (sq_1 + x / sq_1) / 2;
    }
    return sq_2;
}

int main()
{
    int epsilon = 6; //указал 6 вместо 10^-6 для краткости записи (см. дальнейшие строки кода, там 6 возводится в степень: "pow(10, ((-1) * epsilon))")
    cout.precision(4); // для кол-ва знаков после запятой
    double xx;
    double delta_u = pow(10, ((-1) * epsilon)) / (2*0.78); // вычисление delta_u, delta_v - в pdf-файле, 
    double delta_v = pow(10, ((-1) * epsilon)) / (2*3.85); // который я прикрепил вместе с исходным кодом
    double delta_phi = pow(10, ((-1) * epsilon)) / (3); 

    cout << "\nТаблица для функции z(x):" << endl;
    cout << "x \t" << "f_exact " << "epsilon " << "f_approximated \t" << "difference" << endl; // выводим названия столбцов
    for (int x = 50; x <= 60; x = x + 1) {
        xx = x / 100.0;
        double f_exact = sqrt(1+xx)*exp(xx+0.5)*sin(0.3*xx + 0.7); // точное значение, вычисленное через встроенные в c++ функции
        double f_approximated = square_Root(1 + xx, delta_phi) * exponent(xx + 0.5, delta_u) * sinus(0.3 * xx + 0.7, delta_v); // значение функции,
        // вычисленное приближенно за несколько итераций: применяя формулу Герона для корня и ряд Тейлора для синуса и экспоненты 
        // с точностью delta_u, delta_v, delta_phi
        cout << xx << "\t" << f_exact << "\t" << pow(10, -6) << "\t" << f_approximated << "\t \t" << abs(f_exact - f_approximated) << endl;
    }

    cout << "\n\nТаблица для exp(x + 0.5):" << endl;
    cout << "x \t" << "exp_exact " << "delta_u \t" << "exp_approx\t" << "difference" << endl;
    for (int x = 50; x <= 60; x = x + 1) { 
        xx = x / 100.0; 
        cout << xx << "\t" << exp(1 + xx) << "\t  " << delta_u << "\t" << exponent(1 + xx, delta_u) << "\t \t" << abs(exp(1 + xx) - exponent(1 + xx, delta_u)) << endl;
    }
    
    cout << "\n\nТаблица для sin(0.3x + 0.7):" << endl;
    cout << "x \t" << "sin_exact " << "delta_v \t" << "sin_approx \t" << "difference  \t" << endl;
    for (int x = 50; x <= 60; x = x + 1) {
        xx = x / 100.0;
        cout << xx << "\t" << sin(0.3*xx + 0.7) << "\t  " << delta_v << "\t" << sinus(0.3*xx + 0.7, delta_v) << "\t \t" << abs(sin(0.3*xx + 0.7) - sinus(0.3*xx + 0.7, delta_v))  << endl;
    }
    
    cout << "\n\nТаблица для sqrt(1+x):" << endl;
    cout << "x \t" << "sqrt_exact \t" << "delta_phi \t" << "sqrt_approx \t" << "difference  \t" << endl;
    for (int x = 50; x <= 60; x = x + 1) 
    {
        xx = x / 100.0;
        cout << xx << "\t" << sqrt(1 + xx) << "\t \t" << delta_phi << "\t" << square_Root(1 + xx, delta_phi) << "\t \t" << abs(sqrt(1 + xx) - square_Root(1 + xx, delta_phi)) << endl;
    }
    return 0;
}
