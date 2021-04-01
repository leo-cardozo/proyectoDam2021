package Servidor;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControladorOracle {
		
	
	private Connection conexion;
	
	public void conectarOracleBBDD(String ip, int puerto,String nomServicio,String usuario,String contrasena) {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			
			//jdbc:oracle:thin@192:1521:XE
			String db= "jdbc:oracle:thin:@" + ip+":"+puerto+":"+ nomServicio;
			
			conexion = DriverManager.getConnection(db,usuario,contrasena);
			
			if(conexion != null) {
				System.out.println("Conexion establecida");
			}
			else {
				System.out.println("Conexion fallida");
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean consultaLogin(String correo, String contrasena) {
		
		boolean coincidencia = false;
		Statement st = null;
		try {
			st = conexion.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ResultSet rs = null;
		try {
			rs = st.executeQuery("select * from USUARIOS where correo= '"+correo+"' and contrasena='"+contrasena+"' ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		try {
			if(rs.next()) {
				coincidencia=false;
			}else {
				coincidencia=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return coincidencia;
	}
	
	public ResultSet consultaPlazas() {
		
		Statement st = null;
		try {
			st = conexion.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet rs = null;
		try {
			rs = st.executeQuery("select COD_P, TAMANO,DIRECCION from PLAZASAPARCAMIENTO PLAZAS INNER JOIN PARKINGS ON plazas.parking=parkings.id_parking");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}
	
	public ResultSet consultaParkings() {
		
		Statement st = null;
		try {
			st = conexion.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet rs = null;
		try {
			rs = st.executeQuery("select * from PARKINGS ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//INSERCION
	
	public void insertarUsuario(String DNI, String NOMBRE, String APELLIDOS, String TELEFONO, String CORREO, String FECHANACIMIENTO, String CONTRASENA) {
		
		Statement st = null;
		try {
			st = conexion.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			st.executeUpdate("INSERT INTO USUARIOS(DNI,NOMBRE,APELLIDOS,FECHANACIMIENTO,TELEFONO,CORREO,CONTRASENA) VALUES('"+DNI+"','"+NOMBRE+"','"+APELLIDOS+"','"+FECHANACIMIENTO+"',"+TELEFONO+",'"+CORREO+"','"+CONTRASENA+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertarDireccion(String CALLE,String NUMERO,String PORTAL,String PISO,String CP,String CIUDAD,String DNI) {
		
		Statement st = null;
		try {
			st = conexion.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			st.executeUpdate("INSERT INTO DIRECCIONES(CALLE,NUMERO,PORTAL,PISO,CP,CIUDAD,DNI) VALUES('"+CALLE+"','"+NUMERO+"','"+PORTAL+"','"+PISO+"','"+CP+"','"+CIUDAD+"','"+DNI+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertarVehiculo(String MATRICULA, String TIPO, String MARCA, String MODELO,String USUARIO) {
		
		Statement st = null;
		try {
			st = conexion.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			st.executeUpdate("INSERT INTO VEHICULOS(MATRICULA,TIPO,MARCA,MODELO,USUARIO) VALUES('"+MATRICULA+"','"+TIPO+"','"+MARCA+"','"+MODELO+"','"+USUARIO+"')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
