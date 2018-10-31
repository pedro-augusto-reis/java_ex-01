package projeto.servico;

import com.mashape.unirest.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.xml.sax.SAXException;
import projeto.modelo.MergeMinuto;
import projeto.modelo.WsInf;
import projeto.util.XmlParser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import java.util.ArrayList;

public class ValidarWsInf {

    private static Logger log = LoggerFactory.getLogger("validarWsInfLogger");

    public ArrayList<WsInf> pesquisarId(ArrayList<MergeMinuto> listaMergeDados){

        ArrayList<WsInf> lisResWsCasInf = new ArrayList<WsInf>();
        HttpResponse<String> response = null;
        Castor cas = new Castor();
        String status = "";
        String tipo = "";
        int qtdDifWsSus = 0;
        int qtdDifWsRej = 0;

        for (MergeMinuto md : listaMergeDados){
            response = cas.getWsInfo(md);

            // xml parse
            XmlParser xmlP = new XmlParser();
            try{
                status = xmlP.
                        stringParseXml(response.getBody()).
                        getElementsByTagName("url").
                        item(0).
                        getTextContent();
                tipo = xmlP.
                        stringParseXml(response.getBody()).
                        getElementsByTagName("type").
                        item(0).
                        getTextContent();
            }
            catch (ParserConfigurationException e) { e.printStackTrace(); }
            catch (IOException e) { e.printStackTrace(); }
            catch (SAXException e) { e.printStackTrace(); }

            if (!status.equals("")){
                qtdDifWsSus++;
                WsInf wsInf = new WsInf();
                wsInf.setId(md.getId());
                wsInf.setAplicacao(md.getAplicacao());
                wsInf.setType(tipo);
                lisResWsCasInf.add(wsInf);
                log.info("::VALIDARWSINF:WsInfo::\n[" +qtdDifWsSus+ "] - [{"+ md.getId() +" - "+md.getAplicacao()+" - "+tipo+"} encontrado com sucesso no Castor]\n");
            }
            else{
                qtdDifWsRej++;
                log.error("::VALIDARWSINF:WsInfo::\n[" + qtdDifWsRej + "] - [ID: " + md.getId() + " não foi encontrada no Castor] - " +
                        "[Blob: " + md.getBlob()  + "]\n");
            }
        }

        return lisResWsCasInf;
    }
}
