package projeto.dao;

import projeto.dao.jdbc.ConnFactory;
import projeto.modelo.SGerenciamentoCastor;
import projeto.util.RsArListSGerenciamentoCastor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClasseDoisDao {

    private static final String URL_BANCO = "jdbc:oracle:thin:@//SERVIDOR:1521/SERVICO";
    private static final String USUARIO = "";
    private static final String SENHA = "";

    public ArrayList<SGerenciamentoCastor> recuperarListaWsCastor() throws SQLException {

        ConnFactory fabrica;
        Connection conn = null;
        Statement st = null;
        ArrayList<SGerenciamentoCastor> al = null;
        ResultSet rs = null;

        String sql = "";

        try {
            fabrica = new ConnFactory();
            conn = fabrica.getConn(URL_BANCO, USUARIO, SENHA);
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            RsArListSGerenciamentoCastor cv = new RsArListSGerenciamentoCastor();
            al = cv.transformarSDocEmitido(rs);
            return al;

        }

        catch (SQLException e) {
            e.printStackTrace();
            return al;
        }

        finally {
            if (st != null) st.close();
            if (rs != null) rs.close();
            if (conn != null) conn.close();
        }
    }
}
