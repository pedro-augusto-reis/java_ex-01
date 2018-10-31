package projeto.util;

import projeto.modelo.SDocEmitido;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RsArListSDocEmitido {

    public ArrayList<SDocEmitido> transformarSDocEmitido(ResultSet rs) throws SQLException {
        SDocEmitido doc;
        ArrayList<SDocEmitido> al = new ArrayList<SDocEmitido>();

        while(rs.next()){
            doc = new SDocEmitido();
            doc.setNuSeqDocEmitido(rs.getString("NU_SEQ_DOC_EMITIDO"));
            doc.setDtEmissao(rs.getDate("DT_EMISSAO"));
            doc.setCoIdentificadorCastor(rs.getString("CO_IDENTIFICADOR_CASTOR"));
            doc.setBlob(rs.getString("BLOB"));
            al.add(doc);
        }
        return al;
    }
}
