package purestrategyconsole;

import java.util.Scanner;

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
      
    PureStrategyConsole(){
      x1 = 0; y1 = 0;
      x2 = 0; y2 = 0;
      maxmin = Integer.MIN_VALUE;
      minmax = Integer.MAX_VALUE;
      arrayS = new int [0][0];
    }
    
    void startMove() {
    int a = 0; int n = 0;
        Scanner sc = new Scanner(System.in);
        while (a < 1 || a > 2) {
        System.out.print(" -------------------------------------------------\n");
        System.out.print(" Для выбора существующей матрицы нажмите - 1.");
        System.out.print("\n Для ручного ввода нажмите - 2.\n ");    
        if(sc.hasNextInt()) { 
                    a = sc.nextInt();
                }
                 else {
                    System.out.println("\n Вы ввели не целое число.");
                    endMove();
                }
          }
        if (a == 1) {
         Scanner sc1 = new Scanner(System.in);
         System.out.print(" Есть 4 матрицы. Выберите число от 1 до 4.\n ");
            if(sc1.hasNextInt()) { 
             n = sc1.nextInt();      
            }
             else {
                    System.out.println("\n Вы ввели не целое число.");
                    endMove();
                }
             arrayS = createMatrix(n); 
        }
        
        if (a == 2) {
          arrayS = createMatrix();   
        }
    }
    
    void endMove() {
        String s = "";
        System.out.print(" -------------------------------------------------\n");
        System.out.println("\n Нажмите Enter для повтора. Введите N для выхода.");
        Scanner scEnd = new Scanner(System.in);
        s = scEnd.nextLine();
         if (s.matches("n")) {
         scEnd.close();
         System.exit(0);
         }
         else
        launchProject();        

}
    
    int[][] createMatrix(){
        int a = 0, b = 0;            
            Scanner in = new Scanner(System.in);
            while (a < 2) {
            System.out.print(" Введите количество строк матрицы (минимум 2): ");
                if(in.hasNextInt()) { 
                    a = in.nextInt();
                }
                 else {
                    System.out.println("\n Вы ввели не целое число.");
                    endMove();      
                }
            }
            while (b < 2) {
            System.out.print(" Введите количество столбцов матрицы (минимум 2): ");
                if(in.hasNextInt()) {
                     b = in.nextInt();
                }
                else {
                    System.out.println("\n Вы ввели не целое число."); 
                    endMove();
                }
            }
        PureStrategyConsole newArray = new PureStrategyConsole();
        int[][] array = newArray.createMatrix(a,b);
        
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(" Введите элемент матрицы [" + (i+1) + "][" + (j+1) + "]:");
                    if(in.hasNextInt()) { 
                        array[i][j] = in.nextInt();
                    }
                    else {
                        System.out.println("\n Вы ввели не целое число.");
                        endMove();
                }
            }   
        }
        //in.close();
        System.out.println();
      return array;     
    }
    
    int[][] createMatrix(int n) {
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
    
    void displayMatrix(int[][] target){
        int[][] array = target;
        System.out.println("\n Матрица чисел: ");
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    System.out.print(" "+array[i][j] + "\t");
                }
                System.out.println();
            }
        System.out.println();
    }
    
    int culumnOps(int[][] target){
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
            System.out.println("\n Верхняя чистая цена игры (MinMax) = " + minmax + "("+x1 +";"+y1+").");
            System.out.println();
            
        return minmax;
    }
    
    int rowOps(int[][] target){
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


                 System.out.print(" " + min + "  ");
            }
              x2 = iMaxMin;
              y2 = jMaxMin;
              System.out.println("\n Нижняя чистая цена игры (MaxMin) = " + maxmin + "("+x2 +";"+y2+").");
              System.out.println();
              
        return maxmin;
    }
    
    void clearPrice() {
          if (maxmin == minmax)
          System.out.println(" Чистая цена игры: MaxMin = MinMax = " + maxmin + ".");
          else
          System.out.println(" Эта задача решения в чистых стратегиях не имеет, так как MaxMin не равно MinMax."); 
      
}
    void saddlePoint() {
     if (maxmin == minmax) {
      if (x1 == x2 & y1 == y2){
      System.out.println(" Седловая точка: (Io = " + x1 + "; Jo = " + y2 + ")." );     
      }
      else
      System.out.println(" Эта задача решения в чистых стратегиях не имеет."); 
     }    
    }
    
    void launchProject(){
    startMove();
    displayMatrix(arrayS);
    culumnOps(arrayS);
    rowOps(arrayS);
    clearPrice();
    saddlePoint();
    endMove();
    }
    
    
    public static void main(String[] args) {
    System.out.print("\n -------------------------------------------------\n");    
    System.out.println("\n Решение матричных игр в чистых стратегиях.\n ");    
        
    PureStrategyConsole newArray = new PureStrategyConsole();
    newArray.launchProject();
     
   }        
}
