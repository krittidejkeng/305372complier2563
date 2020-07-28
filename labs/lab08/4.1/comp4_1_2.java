import java.io.*;

public class comp4_1_2 {
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
            String[] kkk = n_file[i].split("");
                if(kkk[0].equals("/")){
                    file[i] = "state_slash";
                }
                else if( !kkk[0].equals("}") && n_file[i].length() >= 1 && !kkk[0].equals("\n")){
                    file[i] = "state_begin";
                }
                else if(kkk[0].equals("}")){
                    file[i] = "state_eof";
                }
        }
      
        int count_slash = 0;
        int count_begin = 0;
        int n_comment = 0;
        int line_comment = 0;
        for(int i=0; i<n_file.length; i++){
            
                     if(file[i].equals("state_slash")){
                        String[] kkkk = n_file[i].split(" "); 
                        for(int p=1; p<kkkk.length; p++){
                            n_comment += kkkk[p].length(); 
                        } 
                        line_comment++;  
                }
                else if(file[i].equals("state_begin") || file[i].equals("state_eof")){
                    count_begin++;
                }
        }
        System.out.println("the number of characters in the comment : "+n_comment);
        System.out.println("the number of comment lines : "+line_comment);
        System.out.println("the number of source line : "+count_begin);
    }
}