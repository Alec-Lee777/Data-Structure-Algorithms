import java.util.Scanner;

/**
 * ClassName: t20230301
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/8 16:19
 * @Version 1.0
 */

/*
用右上角代表一块土地，所以不要0就好了

*/
public class T20230301 {

    int n,a,b;
    int x1,y1,x2,y2;

    int res = 0;

    Scanner scan = new Scanner(System.in);

    void init(){
        this.n = scan.nextInt();
        this.a = scan.nextInt();
        this.b = scan.nextInt();
    }

    int countUsed(){
        if(x1 < 0){
            x1 = 0;
        }
        if(x2 > a){
            x2 = a;
        }
        if(y1 < 0){
            y1 = 0;
        }
        if(y2 > b){
            y2 = b;
        }
        if(x1 > a || y1 > b || y2 <= 0 || x2 <= 0){
            return 0;
        }
        else{
            return (y2-y1)*(x2-x1);
        }


    }

    void run(){
        init();
        int res = 0;
        for(int i = 0; i < n; i++){
            this.x1 = scan.nextInt();
            this.y1 = scan.nextInt();
            this.x2 = scan.nextInt();
            this.y2 = scan.nextInt();
            res += countUsed();
        }
        System.out.println(res);
    }


    /*int[][] area = new int[20000][20000];
    int n,a,b;
    int x1,y1,x2,y2;

    int res = 0;

    void setUsed(int x1,int y1,int x2,int y2){
        for(int i = x1; i < x2 ; i ++ ){
            for(int j = y1; j < y2 ;j ++){
                this.area[i+10000][j+10000] = 1;
            }
        }
    }

    void getFinalMap(){
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        a = scan.nextInt();
        b = scan.nextInt();
        for(int i = 0; i < n; i ++) {
            this.x1 = scan.nextInt();
            this.y1 = scan.nextInt();
            this.x2 = scan.nextInt();
            this.y2 = scan.nextInt();
            setUsed(this.x1, this.y1, this.x2, this.y2);
        }
        scan.close();
    }

    void getTotolUsed(){
        for (int i = 10000; i < 10000+a; i++) {
            for (int j = 10000; j < 10000+b; j++) {
                this.res += this.area[i][j];
            }
        }
    }

    void run(){
        getFinalMap();
        getTotolUsed();
    }*/
}
