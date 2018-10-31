package projeto.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnFactory{

    private static final String DRIVER_BD = "oracle.jdbc.driver.OracleDriver";

    public Connection getConn(String url, String usuario, String senha){

        try{
            Class.forName(DRIVER_BD);
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        Connection conn = null;

        try{
            conn = DriverManager.getConnection(url, usuario, senha);
            return conn;
        }

        catch(SQLException e){
            e.printStackTrace();
        }

        return conn;
    }
}
