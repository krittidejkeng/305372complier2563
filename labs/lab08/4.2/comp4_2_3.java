import java.io.*;

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
        int n_comment = 0;
        int line_comment = 0;
        for(int i=0; i<file.length; i++){
            String[] one_slash = file[i].split("");
            String[] double_slash = file[i].split(" "); 
            if(double_slash[0].equals("//")) {
                for (int p = 1; p < double_slash.length; p++)
                    n_comment += double_slash[p].length();
                line_comment++;
            }
            else if(one_slash[0].equals("/") && one_slash[1].equals("*")) {
                line_comment++;
                int loop = 0;
                for(loop=i; loop<file.length; loop++){
                    String slash_loop[] = file[loop].split("");
                        if(slash_loop[slash_loop.length - 2].equals("*")
                                && slash_loop[slash_loop.length - 1].equals("/")) {
                            n_comment += (slash_loop.length - 2);
                            line_comment++;
                            break;
                        }
                }
                String str_file[] = file[i].split("");
                for(int j=0; j<str_file.length; j++){
                    if(!str_file[j].equals(" ") && !str_file[j].equals("/") && !str_file[j].equals("*"))
                        n_comment++;
                }
            }
        }
        System.out.println("the number of characters in the comment : "+n_comment);
        System.out.println("the number of comment lines : "+line_comment);
    }
}


class line extends state{
    void begin(String file[]){
        int count_begin = 0;
        for(int i=0; i<file.length; i++){
            String[] double_slash = file[i].split(" "); 
            String[] one_slash = file[i].split("");
            if(file[i].length() >= 1 && !file[i].equals("")&& !double_slash[0].equals("//")) {
                if(one_slash[0].equals("/") && one_slash[1].equals("*"))
                    count_begin--;
                if (one_slash.length >= 2 && one_slash[one_slash.length - 2].equals("*")
                        && one_slash[one_slash.length - 1].equals("/"))
                    count_begin--;
                count_begin++;
            } 
        }
        System.out.println("the number of source line : "+count_begin);
    }
}

public class comp4_2_3 {
    public static void main(String args[])throws IOException {
        comment obj_com = new comment();
        line obj_line = new line();
        obj_com.slash(obj_com.request());
        obj_line.begin(obj_line.request());
    }
}