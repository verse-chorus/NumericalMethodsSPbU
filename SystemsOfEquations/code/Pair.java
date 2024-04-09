package solver;

public class Pair {
    private double[] first1;
    private int numOfIter; //пригодится для Exel - кол-во итераций

    private double[][] matr;
    private double[] vector;

    public Pair(double[] a, int count) {
        this.first1 = matrix.copy(a);
        this.numOfIter = count;
    }

    //для работы с матрицей и вектором
    public Pair(double[][] a, double[] b){
        this.matr = matrix.copy(a);
        this.vector = matrix.copy(b);
    }

    public double[] getX(){
        return this.first1;
    }

    //получить кол-во итераций
    public int getCount(){
        return this.numOfIter;
    }

    public double[][] getMatr(){
        return this.matr;
    }

    public double[] getVect(){
        return this.vector;
    }
}