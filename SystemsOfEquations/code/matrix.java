package solver;

import java.util.ArrayList;
import java.util.Random;

public class matrix {

    static private int perest = 0;

    public static double[][] sum(double[][] a, double[][] b) {
        double[][] c = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < a[0].length; j++)
                c[i][j] = a[i][j] + b[i][j];
        }
        return c;
    }

    static public double[] sum(double[] a, double[] b) {
        double[] c = new double[a.length];
        for (int i = 0; i < c.length; i++)
            c[i] = a[i] + b[i];
        return c;
    }

    static public double[][] mult(double[][] a, double[][] b) {
        double[][] c = new double[a.length][b[0].length];
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < b[0].length; j++)
            {
                for (int k = 0; k < b.length; k++)
                    c[i][j] += a[i][k] * b[k][j];
            }
        }
        return c;
    }

    static public double[] mult(double[][] a, double[] b) {
        double[] c = new double[b.length];
        for (int i = 0; i < b.length; i++)
        {
            for (int j = 0; j < a[0].length; j++)
                c[i] += a[i][j] * b[j];
        }
        return c;
    }

    static public double[] mult(double[] a, double b) {
        double[] c = new double[a.length];
        for (int i = 0; i < a.length; i++)
            c[i] = b * a[i];
        return c;
    }

    static public double[][] mult(double[][] a, double b) {
        double[][] c = new double[a.length][a.length];
        for (int i = 0; i < c.length; i++)
        {
            for (int j = 0; j < c.length; j++)
                c[i][j] = a[i][j] * b;
        }
        return c;
    }

    static public double mult(double[] a, double[] b) {
        double s = 0;
        for (int i = 0; i < b.length; i++) {
            s += a[i] * b[i];
        }
        return s;
    }

    static public double[][] transp(double[][] a) {
        double[][] c = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < a[0].length; j++)
                c[i][j] = a[j][i];
        }
        return c;
    }

    static public double norma(double[][] a) {
        double max = 0;
        double s = 0;
        for (int j = 0; j < a.length; j++)
        {
            s = 0;
            for (int i = 0; i < a[0].length; i++)
                s += Math.abs(a[i][j]);
            if (s > max)
                max = s;
        }
        return max;
    }

    static public double norma(double[] a) {
        double res = 0;
        for (int i = 0; i < a.length; i++)
            res += a[i] * a[i];
        return Math.sqrt(res);
    }

    static public double[][] copy(double[][] a) {
        double[][] b = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[0].length; j++)
                b[i][j] = a[i][j];
        return b;
    }

    static public double[] copy(double[] a) {
        double[] c = new double[a.length];
        for (int i = 0; i < c.length; i++)
            c[i] = a[i];
        return c;
    }

    static public double determinator(double[][] a) {
        double det = 1;
        matrix.perest = 0;
        double[][] c = new double[a.length][a.length];
        c = matrix.copy(a);
        c = matrix.toUpTriangle(c);
        for (int i = 0; i < a.length; i++)
        {
            double prod = c[i][i];
            det *= prod;
        }
        if (matrix.perest % 2 == 1)
            det *= -1;
        matrix.perest = 0;
        return det;
    }

    static public double[][] toUpTriangle(double[][] a) {
        int n = a.length;
        int f = a[0].length;

        double[][] c = new double[n][f];
        c = matrix.copy(a);

        for (int k = 0; k < n; k++)
        {
            if (c[k][k] == 0)
            {
                int l = 0;
                for (int h = k; h < n; h++)
                    if (c[h][k] != 0)
                    {
                        l = h;
                        break;
                    }
                if (c[l][k] != 0)
                {
                    matrix.perest += 1;
                    c = matrix.perestanovka(c, k, l);
                }
                else continue;
            }

            double del = c[k][k];
            for (int i = k + 1; i < n; i++)
            {
                double prod = c[i][k];
                for (int j = k; j < f; j++)
                    c[i][j] -= c[k][j] * prod / del;
            }
        }
        return c;
    }

    static public double[][] createE(int n) {
        double[][] E = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            {
                E[i][j] = 0;
                if (i == j)
                    E[i][j] = 1.0;
            }
        return E;
    }

    //степенной метод - алгоритм поиска максимального собственного числа матрицы
    static public double maxEig(double[][] a) {
        int n = a.length;
        double e = 10e-6;

        double[][] A = matrix.copy(a);

        double[] xk = new double[n];
        double[] aTimesXk = new double[n];
        aTimesXk[0] = 1;

        double lambda1 = 1;
        double lambda2 = 0;

        while (Math.abs(lambda1 - lambda2) > e) {
            xk = aTimesXk;
            aTimesXk = matrix.mult(A, xk);
            lambda1 = lambda2;
            lambda2 = matrix.mult(aTimesXk, xk) / matrix.mult(xk, xk);
        }

        return lambda2;
    }

    static public double norma2(double[][] a) {
        return Math.sqrt(matrix.maxEig(matrix.mult(matrix.transp(a), a)));
    }

    //перестановка строк
    static public double[][] perestanovka(double[][] a, int k, int l) {
        double[][] c = matrix.copy(a);
        for (int m = 0; m < a[0].length; m++) {
            double o;
            double p;
            p = c[k][m];
            o = c[l][m];
            c[k][m] = o;
            c[l][m] = p;
        }
        return c;
    }

    //главный элемент столбца матрицы
    static public int mainEl(double[][] a, int n) {
        int max = n;
        for (int i = n + 1; i < a.length; i++)
            if (Math.abs(a[max][n]) < Math.abs(a[i][n]))
                max = i;
        return max;
    }

    static public boolean isMainDiag(double[][] a) {
        double[] delta = new double[a.length];
        for (int i = 0; i < delta.length; i++)
        {
            double temp = 0;
            for (int j = 0; j < a.length; j++)
                temp += (i != j) ? Math.abs(a[i][j]) : 0;
            delta[i] = Math.abs(a[i][i]) - temp;
        }

        double deltaMatr = delta[0];
        for (int i = 1; i < delta.length; i++)
            if (deltaMatr > delta[i])
                deltaMatr = delta[i];

        return (deltaMatr > 0) ? true : false;
    }

    static public boolean isPositDef(double[][] a) {
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < a.length; j++)
            {
                if (a[i][j] != a[j][i])
                    return false;
            }
        }
        //диагональные миноры
        for (int i = 1; i < a.length; i++)
        {
            double[][] temp = new double[i][i];

            for (int j = 0; j < temp.length; j++)
                for (int k = 0; k < temp.length; k++)
                    temp[j][k] = a[j][k];

            if (matrix.determinator(temp) < 0)
                return false;
        }
        return true;
    }

    //нахождение матриц Q, R
    static public ArrayList<double[][]> matrixesQR(double[][] a) {
        int n = a.length;

        ArrayList<double[][]> Q = new ArrayList<>();
        Q.add(matrix.createE(n));

        ArrayList<double[][]> QR = new ArrayList<>();

        double[][] R = matrix.copy(a);

        for (int k = 0; k < n - 1; k++)
        {
            double[] y = new double[n - k];
            for (int i = 0; i < y.length; i++)
                y[i] = R[i + k][k];
            double alpha = (-1) * matrix.norma(y);
            double[] z = new double[n - k];
            z[0] = 1;

            //rho, норма y-alpha z
            double r = matrix.norma(matrix.sum(y, matrix.mult(z, alpha)));
            //нормируем w
            double[] w = matrix.mult(matrix.sum(y, matrix.mult(z, alpha)), 1 / r);

            //w*w', симметричная матрица
            double[][] wwt = new double[n - k][n - k];
            for (int i = 0; i < n - k; i++)
            {
                for (int j = 0; j < n - k; j++)
                    wwt[i][j] = w[i] * w[j];
            }

            double[][] HouseholderReflect = new double[n - k][n - k];
            double[][] E = matrix.createE(n - k);
            double[][] Qn = matrix.createE(n);
            double[][] R1 = new double[n - k][n - k];

            //матрица отражения
            HouseholderReflect = matrix.sum(E, matrix.mult(wwt, -2));

            for (int i = 0; i < R1.length; i++)
            {
                for (int j = 0; j < R1.length; j++)
                    R1[i][j] = R[i + k][j + k];
            }

            R1 = matrix.mult(HouseholderReflect, R1);

            for (int i = k; i < n; i++)
            {
                for (int j = k; j < n; j++)
                {
                    Qn[i][j] = HouseholderReflect[i - k][j - k];
                    R[i][j] = R1[i - k][j - k];
                }
            }
            Q.add(Qn);
        }

        double[][] Qres = matrix.createE(n);
        for (int i = 0; i < Qres.length; i++)
            Qres = matrix.mult(Qres, Q.get(i));

        QR.add(Qres); //матрица Q, ортогональная
        QR.add(R); //верхнетреугольная матрица R

        return QR;
    }

}