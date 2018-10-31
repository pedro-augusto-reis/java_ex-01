package projeto.servico;

import com.mashape.unirest.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projeto.dao.ClasseUmDao;
import projeto.modelo.WsInf;
import projeto.util.XmlParser;
import java.util.ArrayList;

public class Processo {

    private static Logger log = LoggerFactory.getLogger("processoLogger");

    public void processarLista(ArrayList<WsInf> list) {
        String nuSeqArqNovo = "";
        HttpResponse<String> responseArqNovo = null;
        String pathArqSalvo = "";
        ClasseUmDao sigDao;
        Castor cas;
        int count = 1;

        for (WsInf wsInf : list) {
            cas = new Castor();
            try {
                /*baixar arquivo WS-Castor DEV e salvar no diretorio src/main/tmp/ */
                pathArqSalvo = cas.getWsView(wsInf);
                log.info("::Iteracao:: > "+count+"\n::PROCESSO:Download::[Download arquivo " + wsInf.getId() + " realizado com sucesso]");
            } catch (Exception e) {
                log.error("::Processo:Download:: [Falha no download do arquivo " + wsInf.getId() + "]");
            }

            try {
                // salvar o arquivo no WS-CASTOR PROD
                responseArqNovo = cas.wsWriteProd(wsInf, pathArqSalvo);
                // xml parse
                XmlParser xmlP = new XmlParser();
                nuSeqArqNovo = xmlP.
                        stringParseXml(responseArqNovo.getBody()).
                        getElementsByTagName("nu_seq_arquivo").
                        item(0).getTextContent();
                log.info("::PROCESSO:Upload:: [Upload arquivo realizado com sucesso]\n>> Id Download: "+wsInf.getId()+"\n>> Id Upload: " + nuSeqArqNovo);
            } catch (Exception e) {
                log.error("::PROCESSO:Upload:: [Falha no upload do arquivo " + wsInf.getId() + " para o castor de prod]");
            }

            try {
                // atualiza banco de dados valor de ID antiga para ID nova
                sigDao = new ClasseUmDao();
                sigDao.atualizarId(wsInf.getId(), nuSeqArqNovo);
                log.info("::PROCESSO:Atualiza Banco:: [ID atualizado com sucesso]\n>> Id antiga: "+wsInf.getId()+"\n>> Id nova: " + nuSeqArqNovo+"\n") ;
            } catch (Exception e) {
                log.error("::PROCESSO:Atualiza Banco:: [Falha na atualização na base de dados para o arquivo id: "+wsInf.getId()+"]\n");
            }
            count++;
        }
    }
}
