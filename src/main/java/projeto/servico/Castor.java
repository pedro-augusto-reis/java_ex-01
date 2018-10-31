package projeto.servico;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projeto.modelo.MergeMinuto;
import projeto.modelo.WsInf;

import java.io.*;

public class Castor {

    private static Logger log = LoggerFactory.getLogger("castorLogger");

    private static final String AMBIENTE_WS_INFO = "";
    private static final String AMBIENTE_WS_VIEW = "";
    private static final String AMBIENTE_WS_WRITE = "";

    public HttpResponse<String> getWsInfo(MergeMinuto md){

        HttpResponse<String> response = null;
        try{
            response = Unirest.post(AMBIENTE_WS_INFO)
                    .header("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                    .header("Cache-Control", "no-cache")
                    .body("------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"xml\"\r\n\r\n" +
                            "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n    <request>\n        <header>\n            " +
                            "<app>INTE</app>\n            <version>1.0</version>\n            <created>2017-11-13T14:15:00-02:00</created>\n        " +
                            "</header>\n        <body>\n            <params>\n                <sg_aplicacao>"+md.getAplicacao()+"</sg_aplicacao>\n                 " +
                            "<arquivo>                               <nu_seq_arquivo>"+md.getId()+"</nu_seq_arquivo>\n                  </arquivo>\n            </params>\n        " +
                            "</body>\n    </request>\n\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--")
                    .asString();
        } catch (UnirestException e) {
            log.error("::CASTOR:Upload::\n>> "+e);
        }
        return response;
    }

    public String getWsView(WsInf inf){
        FileOutputStream fos = null;
        try{
            HttpResponse<InputStream> response = null;
            response = Unirest.get(AMBIENTE_WS_VIEW+"/sg_aplicacao/" + inf.getAplicacao() + "/nu_seq_arquivo/" + inf.getId())
                    .header("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                    .header("Cache-Control", "no-cache")
                    .asBinary();
            String path = "tmp/" + inf.getId() + "." + inf.getType().split("\\/")[1]; // application/pdf
            fos = new FileOutputStream(new File(path));
            int read = 0;
            byte[] buffer = new byte[32768];
            while ( (read = response.getBody().read(buffer)) > 0){
                fos.write(buffer, 0, read);
            }
            return path;
        }
        catch (Exception e){
            log.error("::CASTOR:Upload::\n>> "+e);
            return null;
        }
        finally {
            try {
                if(fos != null){
                    fos.close();
                }
            } catch (IOException e) {
                log.error("::CASTOR:Upload::\n>> "+e);
            }
        }
    }

    public HttpResponse<String> wsWriteProd(WsInf inf, String pathArq){

        HttpResponse<String> response = null;

        try{
            response = Unirest.post(AMBIENTE_WS_WRITE)
                    .field("arquivo", new File(pathArq), inf.getType())
                    .field("sg_aplicacao",inf.getAplicacao())
                    .field("ds_login","")
                    .field("nome_arquivo",inf.getId() +"."+inf.getType().split("\\/")[1])
                    .field("extensao",inf.getType().split("\\/")[1])
                    .asString();
        }
        catch(Exception e){
            log.error("::CASTOR:Upload::\n>> "+e);
        }
        return  response;
    }
}
