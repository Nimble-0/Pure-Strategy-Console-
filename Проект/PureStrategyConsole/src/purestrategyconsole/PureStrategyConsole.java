package purestrategyconsole;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.output.TeeOutputStream;

/*
Приложения для решения задач теории игр:
Решение задач в чистых стратегиях (максимин, минимакс, цена игры, седловая точка)*/
/**
 *
 * @author Sergey
 */
public class PureStrategyConsole {
      int x1, y1, x2, y2;
      int maxmin, minmax;
      int[][] arrayS;
      boolean save = false;
      boolean bNaN = false;
    ByteArrayOutputStream buffer;
    OutputStream teeStream;
          
    PureStrategyConsole(){
      x1 = 0; y1 = 0;
      x2 = 0; y2 = 0;
      maxmin = Integer.MIN_VALUE;
      minmax = Integer.MAX_VALUE;
      arrayS = new int [0][0];

    }
    
    protected void saveToFileBeg() throws IOException{
        buffer = new ByteArrayOutputStream();
        teeStream = new TeeOutputStream(System.out, buffer);
        System.setOut(new PrintStream(teeStream));
        //System.setOut(new PrintStream(teeStream, true, "windows-1251"));
    }
    
    protected void saveToFileEnd() throws IOException{
        try(OutputStream fileStream = new FileOutputStream("temp.txt")) {
            buffer.writeTo(fileStream);
        }

    }
    
    void startMove() throws IOException, InterruptedException {    
    int a = 0; int n = 0;
        Scanner sc = new Scanner(System.in);
        while (a < 1 || a > 3) {
          //System.out.println(" -------------------------------------------------\n");
            System.out.println(" 1 - Выбрать существующую матрицу.");
            System.out.println(" 2 - Ручной ввод. ");
            System.out.println(" 3 - Открыть из файла.\n "); 

            if(sc.hasNextInt()) { 
                        a = sc.nextInt();
                    }
                     else {
                        System.out.println("\n Вы ввели не целое число.");
                        bNaN = true;
                        endMove();
                    }
          }
        if (a == 1) {
         Scanner sc1 = new Scanner(System.in);
         System.out.println(" Есть 4 матрицы. Введите число от 1 до 4.\n ");
            if(sc1.hasNextInt()) { 
             n = sc1.nextInt();      
            }
             else {
                    System.out.println("\n Вы ввели не целое число.");
                    bNaN = true;
                    endMove();
                }
             arrayS = createMatrix(n); 
        }
        
        if (a == 2) {
          arrayS = createMatrix();   
        }
        
        if (a == 3) {
        try {   
            arrayS = openFile();
        } catch (IOException ex) {
            System.out.println(" Неверный файл.");
        }
        }
    }

    void endMove() throws IOException, InterruptedException {
        String s = "";
        System.out.println(" -------------------------------------------------\n");
        System.out.println(" Enter - начать заново.");
        if (save && bNaN != true){
        System.out.println(" Введите S для сохранения результатов.");
        }
        System.out.println(" Введите N для выхода из программы.");
        Scanner scEnd = new Scanner(System.in);
        s = scEnd.nextLine();
         if (s.matches("n")) {
         scEnd.close();
         System.exit(0);
         }
         if (s.matches("s")) {
         
            try { 
                saveToFile();
            } catch (FileNotFoundException ex) {
                System.out.println("\n Ошибка сохранения.");
            } catch (IOException ex) {
                System.out.println("\n Ошибка сохранения.");
            }
         endMove();
         }
         else
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        bNaN = false;
        launchProject();        
    }  
    
   private static int currentIndex = -1; 
   private static Integer next(String numbers[]) {
      ++currentIndex;
      while (currentIndex < numbers.length && numbers[currentIndex].equals(""))
         ++currentIndex;
      return currentIndex < numbers.length ? Integer.parseInt(numbers[currentIndex]) : null;
   }
    
    int[][] openFile() throws FileNotFoundException, IOException, InterruptedException{
        System.out.println(" Файл должен содержать: размер матрицы, матрицу.");
        System.out.println(" Пример:");
        System.out.println(" 2 3");
        System.out.println(" 1 2 3");
        System.out.println(" 4 5 6");  
        
        int[][] array = new int [0][0];
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(null, "Открыть файл");                
        if (ret == JFileChooser.APPROVE_OPTION) {
          FileInputStream inFile = new FileInputStream(fileopen.getSelectedFile());
            byte[] str = new byte[inFile.available()];
             inFile.read(str);
             String text = new String(str);

             try{
             String[] numbers = text.split("\\D");
             int i, j;
             int n = next(numbers), m = next(numbers);
             int matr[][] = new int[n][m];

             for (i = 0; i < n; ++i)
                for (j = 0; j < m; ++j){
                    matr[i][j] = next(numbers);
                    array = matr;
                }
             }
             catch(Throwable th){
                System.out.println(" Неверное содержимое файла.");
                endMove();
             }
             inFile.close();
             }
    currentIndex = -1;
    return array;
    }
       
    private static void copyFileUsingStream(File dest) throws IOException {
    InputStream is = null;
    OutputStream os = null;
    try {       
        is = new FileInputStream("temp.txt");
        os = new FileOutputStream(dest);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }
    } finally {
        is.close();
        os.close();
    }
}
    
    void saveToFile() throws FileNotFoundException, IOException{
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files","txt");
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filter);
        if ( fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
             File file = fc.getSelectedFile(); {
                copyFileUsingStream(file);
            }
        }       
       System.out.println("\n Готово.");
       save = false;
    }
    
    int[][] createMatrix() throws IOException, InterruptedException{
        int a = 0, b = 0;            
            Scanner in = new Scanner(System.in);
            while (a < 2) {
            System.out.println(" Введите количество строк матрицы (минимум 2): ");
                if(in.hasNextInt()) { 
                    a = in.nextInt();
                }
                 else {
                    System.out.println("\n Вы ввели не целое число.");
                    bNaN = true;
                    endMove();      
                }
            }
            while (b < 2) {
            System.out.println(" Введите количество столбцов матрицы (минимум 2): ");
                if(in.hasNextInt()) {
                     b = in.nextInt();
                }
                else {
                    System.out.println("\n Вы ввели не целое число.");
                    bNaN = true;
                    endMove();
                }
            }
        PureStrategyConsole newArray = new PureStrategyConsole();
        int[][] array = newArray.createMatrix(a,b);
        
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.println(" Введите элемент матрицы [" + (i+1) + "][" + (j+1) + "]:");
                    if(in.hasNextInt()) { 
                        array[i][j] = in.nextInt();
                    }
                    else {
                        System.out.println("\n Вы ввели не целое число.");
                        bNaN = true;
                        endMove();
                }
            }   
        }
        System.out.println();
      return array;     
    }
    
    int[][] createMatrix(int n) throws IOException {
        int[][] array1 = {{1,-3,-2},
                          {0,5,4},
                          {2,3,2}};
        
        switch (n) {
         case 0:
        int[][] array0 = {{3,9,2},
                          {7,8,5}};
        return array0;    
            case 1:
        return array1;
            case 2:
        int[][] array2 = {{3,9,2,1},
                          {7,8,5,6},
                          {4,7,3,5},
                          {5,6,1,7}};
        return array2;
            case 3:
        int[][] array3 = {{3,1,2,5},
                          {2,0,0,3},
                         {-3,-5,-5,-2},
                          {0,-2,-2,1}}; 
        return array3;
            case 4:
        int[][] array4 = {{3,1} ,
                          {1,3}};
        return array4;
            default:
        System.out.println("\n Выбрана матрица 1 [по умолчанию].");
        return array1;
    }
}
    
    int[][] createMatrix(int width, int height){
        int[][] array = new int [width][height];
        
        return array;
}
    
    void displayMatrix(int[][] target) throws IOException{
        int[][] array = target;
        System.out.println(" -------------------------------------------------");
        System.out.println("\n Платежная матрица: ");
        System.out.println(" " + array.length + " x " + array[0].length);
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    System.out.print(" "+array[i][j] + "\t");
                }
                System.out.println();
            }
        System.out.println();
    }
    
    int culumnOps(int[][] target) throws IOException{
        int iMaxMin = -1;
        int jMaxMin = -1;              
        int[][] array = target;
        
        System.out.println(" MAX по столбцам: ");
            for(int i = 0; i < array[0].length; i++){
                int max = array[0][i];
                int jMin = 0;
              for(int j = 0; j < array.length; j++) 
              {              
                  if(array[j][i] > max)
                  {
                      max = array[j][i];
                      jMin = j;
                  }
               }
                   if (i == 0 || max < minmax){
                        minmax = max;
                        iMaxMin = i+1;
                        jMaxMin = jMin+1;
                   }


              System.out.print(" " + max + "  ");

            }
            x1 = jMaxMin;
            y1 = iMaxMin;
            System.out.println();
            System.out.println(" Верхняя чистая цена игры (MinMax) = " + minmax + "("+x1 +";"+y1+").");
            System.out.println();    
        return minmax;
    }
    
    int rowOps(int[][] target) throws IOException{
        int iMaxMin = -1;
        int jMaxMin = -1;  
        int[][] array = target; 
        
        System.out.println(" MIN по строкам: ");   
               for(int i = 0; i < array.length; i++){ 
                  int min = array[i][0];
                  int jMin = 0;
                 for(int j = 0; j < array[0].length; j++)
                  {          
                      if(array[i][j] < min)
                      {
                         min=array[i][j];
                         jMin = j;
                        }
                       }             
                        if (i == 0 || min > maxmin)
                        {
                             maxmin = min;
                             iMaxMin = i+1;
                             jMaxMin = jMin+1;
                  } 


                 System.out.println(" " + min + "  ");
            }
              x2 = iMaxMin;
              y2 = jMaxMin;
              System.out.println("\n Нижняя чистая цена игры (MaxMin) = " + maxmin + "("+x2 +";"+y2+").");
              System.out.println();     
        return maxmin;
    }
    
    void clearPrice() throws IOException {
         save = true;
          if (maxmin == minmax)
          System.out.println(" Чистая цена игры: MaxMin = MinMax = " + maxmin + ".");
          else
          System.out.println(" Эта задача решения в чистых стратегиях не имеет, так как MaxMin не равно MinMax."); 
}
    void saddlePoint() throws IOException {
     if (maxmin == minmax) {
      if (x1 == x2 & y1 == y2){
      System.out.println(" Седловая точка: (Io = " + x1 + "; Jo = " + y2 + ")." ); 
      System.out.println(" Оптимальная стратегия игрока А = " + x1 + "." ); 
      System.out.println(" Оптимальная стратегия игрока Б = " + y2 + "." ); 
      
      }
      else
      System.out.println(" Эта задача решения в чистых стратегиях не имеет."); 
     }
    }
    
    void launchProject() throws IOException, InterruptedException{
    startMove();
    saveToFileBeg();
    displayMatrix(arrayS);
    culumnOps(arrayS);
    rowOps(arrayS);
    clearPrice();
    saddlePoint();
    saveToFileEnd();
    endMove();
    }
    
    
    public static void main(String[] args) throws IOException, InterruptedException {
    //System.setProperty("console.encoding","Cp866");
    PureStrategyConsole newArray = new PureStrategyConsole();
    
    System.out.println("\n -------------------------------------------------");    
    System.out.println(" Решение матричных игр в чистых стратегиях. "); 
    System.out.println(" -------------------------------------------------\n"); 

    newArray.launchProject();
    
   }        
}
