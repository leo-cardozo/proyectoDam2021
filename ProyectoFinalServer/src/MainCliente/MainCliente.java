package MainCliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainCliente {

	public static void main(String[] args) {
		
		Socket s = null;
		String clave = null;
		
		try {
			s = new Socket("87.219.49.39", 1522);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DataOutputStream dos = null;
		
		try {
			dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 try {
             dos.writeUTF("miguel@gmail.com");
             dos.writeUTF("qwerty");
         } catch (IOException e) {
             e.printStackTrace();
         }

		 DataInputStream dis =null;

		 boolean existe = false ;
         try {
        	 dis = new DataInputStream(s.getInputStream());
             existe = dis.readBoolean();
         } catch (IOException e) {
             e.printStackTrace();
         }
         
         System.out.println(existe);


//		HiloLectura hl = new HiloLectura(s,num);
//		hl.start();
//		
//		HiloEscritura he = new HiloEscritura(s,num);
//		he.start();

	}

}
