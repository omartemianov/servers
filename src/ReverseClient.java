import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ReverseClient {

  public static void main(String[] args) throws UnknownHostException, IOException {
    Socket socket = new Socket("127.0.0.1", 8000);

    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

      String input = Terminal.ask("Bitte einen String zum Umdrehen eingeben: ");

      out.println(input);
      out.flush();

      String reversed = in.readLine();

      System.out.println("Umgedreht: " + reversed);
    } finally {
      socket.close();
    }

  }

}
