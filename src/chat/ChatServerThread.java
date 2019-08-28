package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class ChatServerThread extends Thread {

	private Socket socket = null;
	private List<PrintWriter> listPrintWriter;
	private String name = null;
	
	
	public ChatServerThread(Socket socket, List<PrintWriter> listPrintWriter) {
		this.socket = socket;
		this.listPrintWriter = listPrintWriter;
	}
	
	
	@Override
	public void run() {
		try {
			//I/O Stream생성
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			
			while(true) {
				String data = br.readLine();
				
				if("QUIT".equals(data)) {
					listPrintWriter.remove(pw);
					System.out.println("Clinet[" + socket.getLocalSocketAddress() + "] 와의 연결이 종료됐습니다.");
					//리스트에서 해당 Client writer 삭제
					broadcasting(listPrintWriter , this.name + "이 채팅창을 종료했습니다.");
					break;
				}
				
				String[] check = data.split(":");
				if("JOIN".equals(check[0])) {
					//리스트에 해당 Client writer 추가
					listPrintWriter.add(pw);
					//이름 기억해두기
					this.name = check[1];
					//
					pw.println("JOIN성공");
					
					broadcasting(listPrintWriter, this.name + "이 대화방에 들어왔습니다.");
					
				}else if("MESSAGE".equals(check[0])) {
					broadcasting(listPrintWriter, this.name + ": " + check[1]);
				}
			}
	
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			System.out.println("Client가 채팅방을 나갔습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	
	private static void broadcasting(List<PrintWriter> listPrintWriter, String string) {
		for(PrintWriter PrintWriter : listPrintWriter) {
			PrintWriter.println(string);
		}
	}
	
	
	
	
	

}
