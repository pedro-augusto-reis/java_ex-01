package projeto.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projeto.dao.jdbc.ConnFactory;
import projeto.modelo.SDocEmitido;
import projeto.util.RsArListSDocEmitido;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClasseUmDao {

    private static Logger log = LoggerFactory.getLogger("classUmDaoLogger");

    private static final String URL_BANCO = "jdbc:oracle:thin:@//SERVIDOR:1521/SERVICO";
    private static final String USUARIO = "";
    private static final String SENHA = "";
    private ArrayList<SDocEmitido> al = null;
    private ConnFactory fabrica = null;
    private Connection conn = null;
    private Statement st = null;
    private ResultSet rs = null;

    public ArrayList<SDocEmitido> recuperarListaSig() throws SQLException {

        String sql = "";

        try {
            fabrica = new ConnFactory();
            conn = fabrica.getConn(URL_BANCO, USUARIO, SENHA);
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            RsArListSDocEmitido cv = new RsArListSDocEmitido();
            al = cv.transformarSDocEmitido(rs);
        }

        catch (SQLException e) {
            log.error(":::AtualizarId::\n>> "+e);
        }
        finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (conn != null) conn.close();
        }
        return al;
    }

    public void atualizarId(String idAntiga, String idNova){

        String sqlTrigger = "{CALL DBMS_APPLICATION_INFO.SET_CLIENT_INFO('')}"; // ALTERAR ID

        String sqlQuery = "";

        try {
            fabrica = new ConnFactory();
            conn = fabrica.getConn(URL_BANCO, USUARIO, SENHA);
            st = conn.createStatement();
            st.execute(sqlTrigger);
            st.executeUpdate(sqlQuery);

        }

        catch (SQLException e) {
            log.error("::AtualizarId::\n>> "+e);
        }
        finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    log.error("::AtualizarId::\n>> "+e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    log.error("::AtualizarId::\n>> "+e);
                }
            }
        }

    }
}
