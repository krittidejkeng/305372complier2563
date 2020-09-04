import java.util.Scanner;
public class test1 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        int y=0;
        if(x == 10)
            y = sc.nextInt();
        System.out.println(x+"/nthis is y: "+y);
    }
}