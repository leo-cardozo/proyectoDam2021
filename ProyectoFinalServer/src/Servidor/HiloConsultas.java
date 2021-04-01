package Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HiloConsultas extends Thread{

	Socket cliente;
	ControladorOracle co = new ControladorOracle();

	public HiloConsultas(Socket s) {
		this.cliente=s;
	}

	@Override
	public void run() {
		super.run();
				
		co.conectarOracleBBDD ("192.168.1.18", 1521, "XE", "C##ProyectoFinal", "123");
		
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(cliente.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DataOutputStream dos = null;
		try {
			dos = new DataOutputStream(cliente.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ObjectInputStream ois = null;
       

		int id_consulta = 0;	
		try {
			id_consulta = dis.readInt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch (id_consulta) {
		case 1:

			String correo = null;
			String contrasena = null;

			try {
				
				correo = dis.readUTF();
				contrasena = dis.readUTF();
				
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
			boolean resultado;
			
			resultado = co.consultaLogin(correo,contrasena);
			
			
			try {
				dos.writeBoolean(resultado);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			System.out.println("Login: " + correo +" , " + contrasena);
			
			break;
		case 2:
			
			 try {
		            ois = new ObjectInputStream(cliente.getInputStream());
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        }
		
			String[] datosP = null;
			try {
				datosP = (String[]) ois.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String[] datosD = null;
			try {
				datosD = (String[]) ois.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			co.insertarUsuario( datosP[0],  datosP[1],  datosP[2],  datosP[3],  datosP[4],  datosP[5],  datosP[6]);
			co.insertarDireccion(datosD[0],  datosD[1],  datosD[2],  datosD[3],  datosD[4],  datosD[5], datosP[0]);
			
			System.out.println("Inserccion usuario");
			break;
						
		case 3:
			
			 try {
		            ois = new ObjectInputStream(cliente.getInputStream());
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        }
			
			String[] datosV = null;
			try {
				datosV = (String[]) ois.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			co.insertarVehiculo(datosV[0],datosV[1],datosV[2],datosV[3],datosV[4]);
			System.out.println("Inserccion vehiculo");
			
			break;
			
			
		case 4:
			
			ResultSet rs;
			rs = co.consultaPlazas();
			
			ArrayList<String[]> listaResultados = new ArrayList<>();
			
			try {
				
				while(rs.next()) {					
					listaResultados.add(new String[] {rs.getString(1),rs.getString(2),rs.getString(3)});
					System.out.println(rs.getString(1)+","+rs.getString(2)+","+rs.getString(3));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ObjectOutputStream oos=null;
			try {
				oos = new ObjectOutputStream(cliente.getOutputStream());
				oos.writeObject(listaResultados);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Listado plazas");
			
			break;

		default:
			System.out.println("nada");
			break;
		}
		
	}

}
