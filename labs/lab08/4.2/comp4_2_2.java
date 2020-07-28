import java.io.*;

public class comp4_2_2 {
    public static void main(String[] args)throws IOException{
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
        call(value_file);
    }


    public static void call(String[] n_file)throws IOException{
        String file[] = new String[n_file.length];
        for(int i=0; i<n_file.length; i++) file[i] = "";
        for(int i=0; i<n_file.length; i++){
            String[] check = n_file[i].split("");
                if(check[0].equals("/"))
                    file[i] = "state_slash";
                else if (!check[0].equals("}") && n_file[i].length() >= 1 && !check[0].equals("\n"))
                    file[i] = "state_begin";
                else if (check[0].equals("}"))
                    file[i] = "state_eof";
        }
      
        int count_begin = 0;
        int n_comment = 0;
        int line_comment = 0;
        for(int i=0; i<n_file.length; i++){
            if(file[i].equals("state_slash")){
                String[] one_slash = n_file[i].split("");
                String[] double_slash = n_file[i].split(" "); 
                if(double_slash[0].equals("//")) {
                    for (int p = 1; p < double_slash.length; p++)
                        n_comment += double_slash[p].length();
                    line_comment++;
                }
                else if(one_slash[0].equals("/") && one_slash[1].equals("*")) {
                    line_comment++;
                    int loop = 0;
                    for(loop=i; loop<n_file.length; loop++){
                        String kkkk3[] = n_file[loop].split("");
                        if(kkkk3[kkkk3.length-2].equals("*") && kkkk3[kkkk3.length-1].equals("/")){
                            count_begin--;
                            n_comment += (kkkk3.length-2);
                            line_comment++;
                            break;
                        }
                    }
                    String str_file[] = n_file[i].split("");
                    for(int j=0; j<str_file.length; j++){
                        if(!str_file[j].equals(" ") && !str_file[j].equals("/") && !str_file[j].equals("*"))
                            n_comment++;
                    }
                }
            }
            else if(file[i].equals("state_begin") || file[i].equals("state_eof"))
                count_begin++;
        }
        System.out.println("the number of characters in the comment : "+n_comment);
        System.out.println("the number of comment lines : "+line_comment);
        System.out.println("the number of source line : "+count_begin);
    }
}