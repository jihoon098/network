package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ChatClient {

	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 9000;
	
	public static void main(String[] args) {
		
		Socket socket = null;
		Scanner sc = null;
		
		try {
			//1. socket생성
			socket = new Socket();
			//2. socket과 server를 연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			
			//3. I/O Stream생성
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
	
			//4. JOIN:사용자이름 전달
			System.out.println("대화에 참여할 사용자 이름을 적어주세요<<");
			sc = new Scanner(System.in);
			String name = sc.nextLine();
			pw.println("JOIN:" + name);
			
			String check = br.readLine();
			
			if("JOIN성공".equals(check)) {
				//System.out.println(name + "님이 참여했습니다." );
				new ChatClientThread(socket).start();
			}

			//채팅
			while(true) {
				String message = sc.nextLine();
				if("QUIT".equals(message)) {
					pw.println("QUIT");
					System.exit(0);
					break;
				}else {
					pw.println("MESSAGE:" + message);
				}
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(sc != null) {
					sc.close();
				}
				if(socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		
		
		
		
	}

}
