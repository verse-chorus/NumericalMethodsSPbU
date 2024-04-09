package solver;

import java.util.ArrayList;

public class slauSolver {
    public static double[] LUP(double[][] a, double[] b) {
        int n = a.length;

        double[][] E = matrix.createE(n);
        double[][] L = matrix.createE(n);

        double[][] U = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                U[i][j] = a[i][j];

        double[] res = new double[n];

        for (int k = 0; k < n; k++)
        {
            int max = matrix.mainEl(U, k);
            if (max != k) {
                U = matrix.perestanovka(U, max, k);
                E = matrix.perestanovka(E, max, k);
                for (int i = 0; i < k; i++)
                {
                    double o, p;
                    o = L[max][i];
                    p = L[k][i];
                    L[k][i] = o;
                    L[max][i] = p;
                }
            }

            double del = U[k][k];

            for (int i = k + 1; i < n; i++)
            {
                double prod = U[i][k];
                for (int j = k; j < n; j++)
                    U[i][j] -= U[k][j] * prod / del;
                L[i][k] = prod / del;
            }
        }

        double[] y = new double[n];
        double[] Eb = matrix.mult(E, b);

        for (int i = 0; i < n; i++)
        {
            double temp = 0;
            for (int j = 0; j < i; j++)
                temp += L[i][j] * y[j];
            y[i] = Eb[i] - temp;
        }

        for (int i = n - 1; i >= 0; i--)
        {
            double temp = 0;
            double del = U[i][i];
            for (int j = i + 1; j < n; j++)
                temp += U[i][j] * res[j];
            res[i] = (y[i] - temp) / del;
        }

        return res;
    }

    public static double[] QR(double[][] a, double[] b) {
        int n = a.length;
        ArrayList<double[][]> QR = matrix.matrixesQR(a);

        double[] res = new double[n];

        double[] y1 = matrix.mult(matrix.transp(QR.get(0)), b);
        for (int i = n - 1; i >= 0; i--)
        {
            double temp = 0;
            double del = QR.get(1)[i][i];
            for (int j = i + 1; j < n; j++)
                temp += QR.get(1)[i][j] * res[j];
            res[i] = (y1[i] - temp) / del;
        }

        return res;
    }

    public static Pair Zeidel(double[][] a, double[] b1, double e) {
        int n = a.length;
        int count = 0;

        double[] b = new double[n];

        double[][] A = new double[a.length][a.length];
        if (matrix.isMainDiag(a) || matrix.isPositDef(a)) {
            A = matrix.copy(a);
            b = matrix.copy(b1);
        } else {
            A = matrix.mult(matrix.transp(a), a);
            b = matrix.mult(matrix.transp(a), b1);
        }

        double[] d = new double[n];
        for (int i = 0; i < n; i++) {
            d[i] = b[i] / A[i][i];
        }

        double[][] C = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j)
                    C[i][j] = -A[i][j] / A[i][i];
                else
                    C[i][j] = 0;
            }
        }

        double[] xk = new double[n];
        for (int i = 0; i < n; i++) {
            xk[i] = d[i];
        }

        double[] xk1 = new double[n];

        while (matrix.norma(matrix.sum(matrix.mult(A, xk), matrix.mult(b, -1))) > e) {
            count++;
            for (int i = 0; i < n; i++)
            {
                double temp = 0;
                for (int j = 0; j < i; j++)
                {
                    temp += C[i][j] * xk1[j];
                }
                for (int j = i; j < n; j++)
                {
                    temp += C[i][j] * xk[j];
                }
                xk1[i] = temp + d[i];
            }
            for (int i = 0; i < n; i++) {
                xk[i] = xk1[i];
            }
        }

        Pair res = new Pair(xk, count);

        return res;
    }

    public static Pair simple_iteration(double[][] a, double[] b1, double e) {
        int n = a.length;
        int count = 0;

        double[][] A = new double[n][n];
        double[] b = new double[n];

        if (matrix.isPositDef(a)) {
            A = matrix.copy(a);
            b = matrix.copy(b1);
        } else {
            A = matrix.mult(matrix.transp(a), a);
            b = matrix.mult(matrix.transp(a), b1);
        }

        double[][] E = matrix.createE(n);

        double mu = 1 / matrix.norma(A);
        double[][] B = matrix.sum(E, matrix.mult(A, -mu));

        double[] c = matrix.mult(b, mu);

        double[] xk = new double[n];

        double[] xk1 = matrix.copy(c);

        while (matrix.norma2(B) / (1 - matrix.norma2(B)) * matrix.norma(matrix.sum(xk, matrix.mult(xk1, -1))) > e)
        {
            count++;
            xk = matrix.copy(xk1);
            xk1 = matrix.sum(matrix.mult(B, xk), c);
        }

        Pair res = new Pair(xk1, count);

        return res;
    }
}