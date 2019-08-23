package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static String SERVER_IP = "192.168.1.92";
	private static int SERVER_PORT = 8000;
	
	public static void main(String[] args) {
		
		Socket socket = null;
		Scanner sc = null;
		
		try {
			//socket과 서버를 연결
			socket = new Socket();
			InetSocketAddress inetSocketAddress
			= new InetSocketAddress(SERVER_IP, SERVER_PORT);
			socket.connect(inetSocketAddress);
			
			//socket의 I/O Stream가져오기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			//사용자로부터 입력값을 Write
			sc = new Scanner(System.in);

			while(true) {
				System.out.print(">>");
				String data = sc.nextLine();
				
				if(data.equals("exit")) {
					return;
				}
				os.write(data.getBytes("UTF-8"));
			
				//server로 부터 받은 값 read
				byte[] buffer = new byte[256];
				int readByteCount = is.read(buffer); //Blocking
				if(readByteCount == -1) {
					System.out.println("[TCPClient] closed by client");
					return;
				}
				
				data = new String(buffer, 0, readByteCount, "UTF-8");
				System.out.println("<<" + data);				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//8. Server Socket 자원정리
			
			sc.close();
			
			
			try {
				if(socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
	}
}