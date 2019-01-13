import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ReverseService {

  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(8000);

    try {
      while (true) {
        Socket client = serverSocket.accept();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter socketWriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

        String line;
        do {
          line = bufferedReader.readLine();
          if (line == null) // Verbindung geschlossen
            break;

          String result = "";
          for (int i = 0; i < line.length(); i++)
            result += line.charAt(line.length() - i - 1);

          socketWriter.println(result);
          socketWriter.flush();
        } while (!line.equals("exit"));

        client.close();
      }
    } finally {
      serverSocket.close();
    }
  }
}
