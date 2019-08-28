package chat.client.win;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import chat.ChatClientThread;

public class ChatClientApp {
	
	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 9000;
	
	public static void main(String[] args) {
		String name = null;
		Scanner scanner = new Scanner(System.in);
		Socket socket = null;;
		
		while( true ) {
			System.out.println("대화명을 입력하세요.");
			System.out.print(">>> ");
			name = scanner.nextLine();
			
			if (name.isEmpty() == false ) {
				break;
			}
			System.out.println("대화명은 한글자 이상 입력해야 합니다.\n");
		}


		try {
			//1. create socket
			socket = new Socket();
			//2. connect to server
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			
			//3. create iostream
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			
			//4. join protocol 구현
			pw.println("JOIN:" + name);
			
			String check = br.readLine();
			if("JOIN성공".equals(check)) {
				//JOIN성공하면  Window창 open
				new ChatWindow(name, socket).show();
			}
			
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(scanner != null) {
				scanner.close();					
			}			
		}
		
		
		
		
		
		
	}

}
