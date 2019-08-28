package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	
	private static final String SERVER_IP = "127.0.0.1";
	private static final int PORT = 9000;
	
	public static void main(String[] args) {
		
		ServerSocket serverSocket = null;
		//클라이언트 소켓들을 담을 arrayList생성
		List<PrintWriter> listPrintWriter = new ArrayList<PrintWriter>();
		
		try {
			//1. 서버 소켓 생성
			serverSocket = new ServerSocket();
			
			//2. 서버 소켓에 Binding
			InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVER_IP, PORT);
			serverSocket.bind(inetSocketAddress);
			System.out.println("[serverSocket bind]" + SERVER_IP + ":" + PORT);
			
			
			while(true) {				
				//3. client소켓 연결 기다림
				Socket socket = serverSocket.accept();
				
				System.out.println("Clinet[" + socket.getLocalSocketAddress() + "] 와 연결됐습니다.");
				//4. client소켓과 소켓들을 담을 arrayList를 쓰레드로 처리
				new ChatServerThread(socket, listPrintWriter).start();
			}
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
