package solver;

import java.util.ArrayList;

public class Test {

    static int N = 17;
    //тест 0
    private static double[][] test0a = {
            { 0, 2, 3 },
            { 1, 2, 4 },
            { 4, 5, 6 } };
    private static double[] test0b = { 13, 17, 32 };

    //тест 1
    private static double[][] test1a = {
            { N+2, 1, 1 },
            { 1, N+4, 1 },
            { 1, 1, N+6 } };
    private static double[] test1b = { N+4, N+6, N+8 };

    //тест 2
    private static double[][] test2a = {
            { -(N+2), 1, 1 },
            { 1, -(N+4), 1 },
            { 1, 1, -(N+6) } };
    private static double[] test2b = { -(N+4), -(N+6), -(N+8) };

    //тест 3
    private static double[][] test3a = {
            { -(N+2), N+3, N+4 },
            { N+5, -(N+4), N+1 },
            { N+4, N+5, -(N+6) } };
    private static double[] test3b = { N+4, N+6, N+8 };

    //тест 4
    private static double[][] test4a = {
            { N+2, N+1, N+1 },
            { N+1, N+4, N+1 },
            { N+1, N+1, N+6 } };
    private static double[] test4b = { N+4, N+6, N+8 };

    //точное решение, полученное с помощью внешних сервисов
    private static double[][] xPrecise = {
            { -1, -2, -3 },
            { -1, -1, -1 },
            { -1403.0/1139, -1379.0/1139, -1359.0/1139 },
            { -21529.0/17726, -9643.0/8863, -18837.0/17726 },
            { 27.0/143, -259.0/429, -109.0/143 } };

    static public double[][] getRes(){
        return xPrecise;
    }

    //создаем вектор из пар: матрица A и вектор b
    public static ArrayList<Pair> getTests(){
        ArrayList<Pair> tests = new ArrayList<>();
        tests.add(new Pair(test0a, test0b));
        tests.add(new Pair(test1a, test1b));
        tests.add(new Pair(test2a, test2b));
        tests.add(new Pair(test3a, test3b));
        tests.add(new Pair(test4a, test4b));
        return tests;
    }

    //тест №5
    static public Pair createTest5(int n, double e) {
        double[][] test5a = new double[n][n];
        double[][] temp1 = matrix.createE(n);
        for (int i = 0; i < test5a.length; i++) {
            for (int j = i + 1; j < test5a.length; j++) {
                temp1[i][j] = -1;
            }
        }
        double[][] temp2 = matrix.copy(temp1);
        for (int i = 0; i < temp2.length; i++) {
            for (int j = 0; j < i; j++) {
                temp2[i][j] = 1;
            }
        }
        test5a = matrix.sum(temp1, matrix.mult(temp2, e * N));

        double[] test5b = new double[n];
        for (int i = 0; i < test5b.length - 1; i++) {
            test5b[i] = -1;
        }
        test5b[n - 1] = 1;

        Pair test5 = new Pair(test5a, test5b);

        return test5;
    }
}