import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class CsvDownloader {

  public static void main(String[] args) throws UnknownHostException, IOException {
    Socket socket = new Socket("www2.in.tum.de", 80);

    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    
    String requestLine = "GET /~kranzj/temperaturesEurope1963Till2013ByCity.csv HTTP/1.1";
    String hostLine = "Host: www2.in.tum.de";
    
    out.print(requestLine + "\r\n" + hostLine + "\r\n\r\n");
    out.flush();
    
    OutputStream os = new FileOutputStream("temperaturesEurope1963Till2013ByCity.csv");
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
    
    // Skip header, no error handling
    in.lines().dropWhile(l -> !l.equals("")).skip(1).forEach(l -> {
      try {
        writer.write(l);
        writer.newLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    
    writer.close();
    socket.close();
  }

}
