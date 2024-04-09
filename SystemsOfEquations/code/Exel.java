package solver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class Exel {
    private static int testNumber = 0;
    private static Sheet sheet;
    private static Sheet sheet1;
    private static HSSFCellStyle myStyle;

    public static void initialization() throws IOException {
        try (Workbook plainWorkbook = new HSSFWorkbook())
        {
            sheet = plainWorkbook.createSheet("Тесты №0-4");
            sheet1 = plainWorkbook.createSheet("Тест №5");
            myStyle = (HSSFCellStyle) plainWorkbook.createCellStyle(); //стиль ячейки

            //стиль границы ячейки
            myStyle.setBorderTop(BorderStyle.THIN);
            myStyle.setBorderBottom(BorderStyle.THIN);
            myStyle.setBorderLeft(BorderStyle.THIN);
            myStyle.setBorderRight(BorderStyle.THIN);

            myStyle.setWrapText(true); //перенос слов

            myStyle.setAlignment(HorizontalAlignment.CENTER);
            myStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            testProgram(Test.getTests());

            for (int i = 0; i < 13; i++)
                sheet.autoSizeColumn(i, true);
            for (int j = 0; j < 15; j++)
                sheet1.autoSizeColumn(j, true);

            FileOutputStream output = new FileOutputStream("tableOfResults.xls");
            plainWorkbook.write(output);
            output.close();
        }
    }

    private static void testProgram(ArrayList<Pair> tests) {
        first_line();
        for (Pair test : tests) {
            test(test.getMatr(), test.getVect());
        }

        first_line1();
        test5();
    }

    private static void first_line() {
        Row row1 = sheet.createRow(0);

        Cell[] cell1 = new Cell[13];

        //sheet.addMergedRegion добавляет объединенную область ячеек
        //номер теста
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        //точное решение
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
        //эпсилон
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
        //МПИ
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 5));
        //3 столбца для метода Зейделя
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 8));
        //2 столбца под метод Гаусса
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 10));
        //2 столбца под метод Хаусхолдера
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 12));

        for (int i = 0; i < cell1.length; i++)
        {
            cell1[i] = row1.createCell(i);
            cell1[i].setCellStyle(myStyle);
        }

        cell1[0].setCellValue("№ теста");
        cell1[1].setCellValue("точный ответ");
        cell1[2].setCellValue("epsilon");

        cell1[3].setCellValue("МПИ");
        cell1[6].setCellValue("метод Зейделя");
        cell1[9].setCellValue("метод Гаусса");
        cell1[11].setCellValue("метод Хаусхолдера");
        //первый листочек
        Row row2 = sheet.createRow(1);

        Cell[] cell2 = new Cell[10];
        for (int i = 0; i < 3; i++)
        {
            Cell cellTemp = row2.createCell(i);
            cellTemp.setCellStyle(myStyle);
        }
        for (int i = 0; i < cell2.length; i++)
        {
            cell2[i] = row2.createCell(i + 3);
            cell2[i].setCellStyle(myStyle);
        }

        cell2[0].setCellValue("x");
        cell2[1].setCellValue("delta");
        cell2[2].setCellValue("k");
        cell2[3].setCellValue("x");
        cell2[4].setCellValue("delta");
        cell2[5].setCellValue("k");
        cell2[6].setCellValue("x");
        cell2[7].setCellValue("delta");
        cell2[8].setCellValue("x");
        cell2[9].setCellValue("delta");
    }

    private static void first_line1() {
        Row row1 = sheet1.createRow(0);

        Cell[] cell1 = new Cell[15];
        //для второго листочка
        sheet1.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        sheet1.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
        sheet1.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
        sheet1.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
        sheet1.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
        sheet1.addMergedRegion(new CellRangeAddress(0, 0, 5, 7));
        sheet1.addMergedRegion(new CellRangeAddress(0, 0, 8, 10));
        sheet1.addMergedRegion(new CellRangeAddress(0, 0, 11, 12));
        sheet1.addMergedRegion(new CellRangeAddress(0, 0, 13, 14));

        for (int i = 0; i < cell1.length; i++)
        {
            cell1[i] = row1.createCell(i);
            cell1[i].setCellStyle(myStyle);
        }

        cell1[0].setCellValue("№ теста");
        cell1[1].setCellValue("n");
        cell1[2].setCellValue("epsAdd");
        cell1[3].setCellValue("");
        cell1[4].setCellValue("epsSlau");

        cell1[5].setCellValue("МПИ");
        cell1[8].setCellValue("метод Зейделя");
        cell1[11].setCellValue("метод Гаусса");
        cell1[13].setCellValue("метод Хаусхолдера");

        Row row2 = sheet1.createRow(1);

        Cell[] cell2 = new Cell[10];
        for (int i = 0; i < 5; i++)
        {
            Cell cellTemp = row2.createCell(i);
            cellTemp.setCellStyle(myStyle);
        }
        for (int i = 0; i < cell2.length; i++)
        {
            cell2[i] = row2.createCell(i + 5);
            cell2[i].setCellStyle(myStyle);
        }
        cell2[0].setCellValue("x");
        cell2[1].setCellValue("delta");
        cell2[2].setCellValue("k");
        cell2[3].setCellValue("x");
        cell2[4].setCellValue("delta");
        cell2[5].setCellValue("k");
        cell2[6].setCellValue("x");
        cell2[7].setCellValue("delta");
        cell2[8].setCellValue("x");
        cell2[9].setCellValue("delta");
    }

    //добавление результатов тестов №0-4 в таблицу
    private static void test(double[][] a, double[] b) {
        int n = b.length;
        double[] e = { 1e-2, 1e-3, 1e-4, 1e-5 };

        double[] x1 = matrix.copy(Test.getRes()[testNumber]);

        Pair[] SimpleIterResults = new Pair[4];
        for (int i = 0; i < 4; i++)
            SimpleIterResults[i] = slauSolver.simple_iteration(a, b, e[i]);


        //вектор с результатами МПИ для тестов №0-4
        Pair[] ZeidelResults = new Pair[4];
        for (int i = 0; i < 4; i++)
            ZeidelResults[i] = slauSolver.Zeidel(a, b, e[i]);


        //результаты LU(P)
        double[] LUpResults = slauSolver.LUP(a, b);
        //результаты QR
        double[] qrResults = slauSolver.QR(a, b);

        Row[] row = new Row[4];
        for (int i = 0; i < 4; i++)
        {
            row[i] = sheet.createRow(i+2+4*testNumber);
            row[i].setHeightInPoints(15);
        }

        sheet.addMergedRegion(new CellRangeAddress(2+4*testNumber, 5+4*testNumber, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(2+4*testNumber, 5+4*testNumber, 9, 9));
        sheet.addMergedRegion(new CellRangeAddress(2+4*testNumber, 5+4*testNumber, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(2+4*testNumber, 5+4*testNumber, 10, 10));
        sheet.addMergedRegion(new CellRangeAddress(2+4*testNumber, 5+4*testNumber, 11, 11));
        sheet.addMergedRegion(new CellRangeAddress(2+4*testNumber, 5+4*testNumber, 12, 12));

        Cell[][] cell = new Cell[4][13];

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 13; j++)
            {
                cell[i][j] = row[i].createCell(j);
                cell[i][j].setCellStyle(myStyle);
                switch (j) {
                    case 2:
                    {
                        cell[i][j].setCellValue(e[i]);
                        break;
                    }
                    //заполнение столбца МПИ
                    case 3:
                    {
                        String temp = "";
                        for (int k = 0; k < n; k++)
                            temp += SimpleIterResults[i].getX()[k] + ", "; //пополнение ячеек
                        cell[i][j].setCellValue(temp);
                        break;
                    }
                    //delta
                    case 4:
                    {
                        cell[i][j].setCellValue(matrix.norma(matrix.sum(SimpleIterResults[i].getX(), x1)));
                        break;
                    }
                    //номер итерации: k
                    case 5:
                    {
                        cell[i][j].setCellValue(SimpleIterResults[i].getCount());
                        break;
                    }
                    //заполнение столбца с методом Зейделя
                    case 6:
                    {
                        String temp = "";
                        for (int k = 0; k < n; k++)
                            temp += ZeidelResults[i].getX()[k] + ", ";
                        cell[i][j].setCellValue(temp);
                        break;
                    }
                    case 7:
                    //дельта
                    {
                        cell[i][j].setCellValue(matrix.norma(matrix.sum(ZeidelResults[i].getX(), x1)));
                        break;
                    }
                    case 8:
                    //кол-во итераций метода Зейделя
                    {
                        cell[i][j].setCellValue(ZeidelResults[i].getCount());
                        break;
                    }
                    case 9:
                        //заполнение столбца с LU(P)
                    {
                        String temp = "";
                        for (int k = 0; k < n; k++)
                            temp += LUpResults[k] + ", ";
                        cell[i][j].setCellValue(temp);
                        break;
                    }
                    case 10:
                    //дельта
                    {
                        cell[i][j].setCellValue(matrix.norma(matrix.sum(LUpResults, x1)));
                        break;
                    }
                    case 11:
                    //столбец с методом Хаусхолдера
                    {
                        String temp = "";
                        for (int k = 0; k < n; k++)
                            temp += qrResults[k] + ", ";
                        cell[i][j].setCellValue(temp);
                        break;
                    }
                    case 12:
                    //дельта
                    {
                        cell[i][j].setCellValue(matrix.norma(matrix.sum(qrResults, x1)));
                        break;
                    }
                    default:
                        break;
                }
            }
        }
        cell[0][0] = row[0].createCell(0);
        cell[0][0].setCellStyle(myStyle);
        cell[0][0].setCellValue(testNumber);
        cell[0][1] = row[0].createCell(1);
        cell[0][1].setCellStyle(myStyle);
        cell[0][1].setCellValue((-1) * x1[0] + ", " + (-1) * x1[1] + ", " + (-1) * x1[2]);
        testNumber++;
    }

    private static void test5() {
        int from_n = 4;
        double[] e = { 1e-2, 1e-3, 1e-4, 1e-5, 1e-6 };
        Pair[][] test5 = new Pair[4][2];

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 2; j++)
                test5[i][j] = Test.createTest5(i+from_n, e[3*j+1]);
        }

        Pair[][][] SimpleIterResults = new Pair[4][2][4];
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                for (int j2 = 0; j2 < 4; j2++)
                    SimpleIterResults[i][j][j2] = slauSolver.simple_iteration(test5[i][j].getMatr(), test5[i][j].getVect(), e[j2]);
            }
        }

        Pair[][][] ZeidelResults = new Pair[4][2][4];
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                for (int j2 = 0; j2 < 4; j2++)
                    ZeidelResults[i][j][j2] = slauSolver.Zeidel(test5[i][j].getMatr(), test5[i][j].getVect(), e[j2]);
            }
        }

        double[][][] LUpResults = new double[4][2][4];
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 2; j++)
                LUpResults[i][j] = slauSolver.LUP(test5[i][j].getMatr(), test5[i][j].getVect());
        }

        double[][][] qrResults = new double[4][2][4];
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 2; j++)
                qrResults[i][j] = slauSolver.QR(test5[i][j].getMatr(), test5[i][j].getVect());
        }

        Row[] row = new Row[32];
        for (int i = 0; i < 32; i++)
        {
            row[i] = sheet1.createRow(i + 2);
            row[i].setHeightInPoints(15);
        }

        sheet1.addMergedRegion(new CellRangeAddress(2, 33, 0, 0));
        sheet1.addMergedRegion(new CellRangeAddress(2, 9, 1, 1));
        sheet1.addMergedRegion(new CellRangeAddress(10, 17, 1, 1));
        sheet1.addMergedRegion(new CellRangeAddress(18, 25, 1, 1));
        sheet1.addMergedRegion(new CellRangeAddress(26, 33, 1, 1));
        for (int i = 0; i < 8; i++)
        {
            sheet1.addMergedRegion(new CellRangeAddress(2+i*4, 5+i*4, 2, 2));
            sheet1.addMergedRegion(new CellRangeAddress(2+i*4, 5+i*4, 3, 3));
            sheet1.addMergedRegion(new CellRangeAddress(2+i*4, 5+i*4, 11, 11));
            sheet1.addMergedRegion(new CellRangeAddress(2+i*4, 5+i*4, 12, 12));
            sheet1.addMergedRegion(new CellRangeAddress(2+i*4, 5+i*4, 13, 13));
            sheet1.addMergedRegion(new CellRangeAddress(2+i*4, 5+i*4, 14, 14));
        }

        Cell[][] cell = new Cell[32][15];

        for (int i = 0; i < 32; i++)
        {
            for (int j = 0; j < 15; j++)
            {
                cell[i][j] = row[i].createCell(j);
                cell[i][j].setCellStyle(myStyle);
                int n = i / 8;
                int ee = ((i < 4) || (8 <= i && i < 12) || (16 <= i && i < 20) || (24 <= i && i < 28)) ? 0 : 1;
                switch (j) {
                    case 1:
                    {
                        if ((i) % 8 == 0)
                            cell[i][j].setCellValue(n + from_n);
                        break;
                    }
                    case 2:
                    {
                        if (i % 8 == 0)
                            cell[i][j].setCellValue(e[i%8+1]);
                        else if (i % 8 == 4)
                            cell[i][j].setCellValue(e[i%8]);
                        break;
                    }
                    case 4:
                    {
                        cell[i][j].setCellValue(e[i%4]);
                        break;
                    }
                    case 5:
                    {
                        String temp = "" + SimpleIterResults[n][ee][i%4].getX()[0];
                        for (int k = 1; k < n + from_n; k++)
                            temp += ", " + SimpleIterResults[n][ee][i % 4].getX()[k];
                        cell[i][j].setCellValue(temp);
                        break;
                    }
                    case 6:
                    {
                        double[] x1 = new double[n + from_n];
                        x1[n + from_n - 1] = -1;
                        cell[i][j].setCellValue(matrix.norma(matrix.sum(SimpleIterResults[n][ee][i % 4].getX(), x1)));
                        break;
                    }
                    case 7:
                    {
                        cell[i][j].setCellValue(SimpleIterResults[n][ee][i % 4].getCount());
                        break;
                    }
                    case 8:
                    {
                        String temp = "" + ZeidelResults[n][ee][i % 4].getX()[0];
                        for (int k = 1; k < n + from_n; k++)
                            temp += ", " + ZeidelResults[n][ee][i % 4].getX()[k];
                        cell[i][j].setCellValue(temp);
                        break;
                    }
                    case 9:
                    {
                        double[] x1 = new double[n + from_n];
                        x1[n + from_n - 1] = -1;
                        cell[i][j].setCellValue(matrix.norma(matrix.sum(ZeidelResults[n][ee][i % 4].getX(), x1)));
                        break;
                    }
                    case 10:
                    {
                        cell[i][j].setCellValue(ZeidelResults[n][ee][i % 4].getCount());
                        break;
                    }
                    case 11:
                    {
                        String temp = "" + LUpResults[n][ee][0];
                        for (int k = 1; k < n + from_n; k++)
                            temp += ", " + LUpResults[n][ee][k];
                        cell[i][j].setCellValue(temp);
                        break;
                    }
                    case 12:
                    {
                        double[] x1 = new double[n + from_n];
                        x1[n + from_n - 1] = -1;
                        cell[i][j].setCellValue(matrix.norma(matrix.sum(LUpResults[n][ee], x1)));
                        break;
                    }
                    case 13:
                    {
                        String temp = "" + qrResults[n][ee][0];
                        for (int k = 1; k < n + from_n; k++)
                            temp += ", " + qrResults[n][ee][k];
                        cell[i][j].setCellValue(temp);
                        break;
                    }
                    case 14:
                    {
                        double[] x1 = new double[n + from_n];
                        x1[n + from_n - 1] = -1;
                        cell[i][j].setCellValue(matrix.norma(matrix.sum(qrResults[n][ee], x1)));
                        break;
                    }
                    default:
                        break;
                }
            }
        }

        cell[0][0] = row[0].createCell(0);
        cell[0][0].setCellStyle(myStyle);
        cell[0][0].setCellValue(5);
    }
}