package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;

public class ChatClientThread extends Thread {
	
	Socket socket = null;
	BufferedReader br = null;
	
	public ChatClientThread(Socket socket) {
		this.socket = socket;
	}

	
	
	@Override
	public void run() {
		
		try {
			while(true) {				
				br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				String message = br.readLine();
				System.out.println(message);
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			//e.printStackTrace();
			System.out.println("채팅을 종료합니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
