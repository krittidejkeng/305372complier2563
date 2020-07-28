import java.io.*;
import java.util.Scanner;
class state{
    String[] request()throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("source"+".cpp"));
        String st;
        int count = 0;
        while((st = br.readLine()) != null)
            count++;
        String value_file[] = new String[count];
        String str_temp = "";
        int i = 0;
        try{
            BufferedReader br_loop = new BufferedReader(new FileReader("source"+".cpp"));
            while(str_temp != null){
                if(str_temp == null) break;
                str_temp = br_loop.readLine();
                value_file[i] = str_temp;
                i++;
            }
            br_loop.close();
        }
        catch(Exception e){}
        return value_file;
    }
}

/*********************************************************/
class comment extends state{
    void slash(String file[]){
        int count_slash = 0;
        int count_begin = 0;
        int n_comment = 0;
        int line_comment = 0;
        for(int i=0; i<file.length; i++){
            String[] kkkk = file[i].split(" "); 
            if(kkkk[0].equals("//")){
                for(int p=1; p<kkkk.length; p++){
                    n_comment += kkkk[p].length(); 
                } 
                line_comment++;
            }
        }
        System.out.println("the number of characters in the comment : "+n_comment);
        System.out.println("the number of comment lines : "+line_comment);
    }
}


class line extends state{
    void begin(String file[]){
        int count_slash = 0;
        int count_begin = 0;
        int n_comment = 0;
        int line_comment = 0;
        for(int i=0; i<file.length; i++){
            String[] kkkk = file[i].split(" "); 
            if(file[i].length() >= 1 && !file[i].equals("")&& !kkkk[0].equals("//")) 
                count_begin++;
        }
        System.out.println("the number of source line : "+count_begin);

    }
}

public class comp4_1_3 {
    public static void main(String args[])throws IOException {
        comment obj_com = new comment();
        line obj_line = new line();
        obj_com.slash(obj_com.request());
        obj_line.begin(obj_line.request());
    }
}