package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainServer {

	public static void main(String[] args) {
		
		ArrayList<Socket> listaSockets = new ArrayList<Socket>();
		
		ServerSocket ss = null;
		try {
			 ss = new ServerSocket(1522);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while(true) {
			Socket s = null;
			try {
				s = ss.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listaSockets.add(s);
						
			HiloConsultas h = new HiloConsultas(s);
			h.start();
			
		}

	}
}