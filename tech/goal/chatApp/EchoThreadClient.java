import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class EchoThreadClient{
 public static void main(String[] args){
        String host;
        int port= 5000;
        try{
            if (args.length > 0){
                host = args[0];
            }else{
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("IP�ּҸ� �Է��Ͻðų� quit. �����ø� ������.");
                host = in.readLine();
            }
            if (host.length() == 0){
                host = "127.0.0.1";
            }
            AdvancedEchoeThread thread = new AdvancedEchoeThread(host, port);
            thread.start();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    static class AdvancedEchoeThread extends Thread{
        private Socket sc;
        
        public AdvancedEchoeThread(String host, int port) throws IOException {
            this.sc = new Socket(host, port);
        }

        public void quit(){
            if (sc != null){
                try{
                    sc.close();
                    sc = null;
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        public void run(){
            try{
                InetAddress inetAddress = sc.getInetAddress();
                System.out.println(inetAddress.getHostAddress() + " �� �����Ͽ����ϴ�.");
                OutputStream out = sc.getOutputStream();
                InputStream in = sc.getInputStream();
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, "UTF-8"));
                BufferedReader br_key = new BufferedReader(new InputStreamReader(System.in, "MS949"));
                BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String line = null;
                try{
                    while((line = br_key.readLine()) != null){
                        if (line.equals("quit.")){
                            break;
                        }
                        System.out.println("������ ������ ���ڿ� : " + line);
                        pw.println(line);
                        pw.flush();
                        String echo = br.readLine();
                        System.out.println("�����κ��� ���۹��� ���ڿ� : " + echo);
                    }
                }catch (IOException e){
                    System.out.println(e.getMessage());
                }finally {
                    System.out.println(inetAddress.getHostAddress() + " �� ������ �����Ͽ����ϴ�.");
                    pw.close();
                    br.close();
                    sc.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

    }
}