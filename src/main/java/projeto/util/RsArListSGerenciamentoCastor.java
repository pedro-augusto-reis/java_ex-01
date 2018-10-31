package projeto.util;

import projeto.modelo.SGerenciamentoCastor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RsArListSGerenciamentoCastor {

    public ArrayList<SGerenciamentoCastor> transformarSDocEmitido(ResultSet rs) throws SQLException {
        SGerenciamentoCastor doc;
        ArrayList<SGerenciamentoCastor> al = new ArrayList<SGerenciamentoCastor>();

        while(rs.next()){
            doc = new SGerenciamentoCastor();
            doc.setNuSeqArquivo(rs.getString("NU_SEQ_ARQUIVO"));
            doc.setCoAplicacao(rs.getString("CO_APLICACAO"));
            doc.setDtInclusao(rs.getDate("DT_INCLUSAO"));
            doc.setStatus(rs.getString("STATUS"));
            al.add(doc);
        }
        return al;
    }

}
