package projeto;

import projeto.dao.ClasseUmDao;
import projeto.dao.ClasseDoisDao;
import projeto.modelo.MergeMinuto;
import projeto.modelo.SDocEmitido;
import projeto.modelo.SGerenciamentoCastor;
import projeto.modelo.WsInf;
import projeto.servico.Processo;
import projeto.servico.ValidarMinuto;
import projeto.servico.ValidarWsInf;
import projeto.util.GerenciaDir;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;


public class Main {

    private static ArrayList<SDocEmitido> listDocEmi;
    private static ArrayList<SGerenciamentoCastor> listWs;
    private static ArrayList<MergeMinuto> listMin; // lista gerada apos aplicada regra entre as duas anteriores
    private static ArrayList<WsInf> listWsInf; // lista gerada apos consulta no WS-CASTOR/INF com a lista anterior

    public static void main(String[] args){

        System.out.println("Inicio processo: "+LocalTime.now());

        GerenciaDir gDir = new GerenciaDir();
        gDir.iniciar(1,1,1,1);

        // 1) recupera lista com IDs do banco
        ClasseUmDao sig = new ClasseUmDao();
        try{ listDocEmi = sig.recuperarListaSig(); }
        catch(SQLException e){ e.printStackTrace(); }

        // 2) recupera lista com IDs do banco
        ClasseDoisDao ws = new ClasseDoisDao();
        try{ listWs = ws.recuperarListaWsCastor(); }
        catch(SQLException e){ e.printStackTrace(); }

        // 3) valida a lista, comparando o horario de insercao com o horário informado
        ValidarMinuto vl = new ValidarMinuto();
        listMin = vl.validarLista(listDocEmi, listWs);

        // 4) consulta servico - identifica quais IDs existem
        ValidarWsInf wsCV = new ValidarWsInf();
        listWsInf = wsCV.pesquisarId(listMin);

        listDocEmi.clear();
        listWs.clear();
        listMin.clear();

        // 5) realiza download do arquivo no castor, inclui no castor de prod e atualiza tabela
        Processo proc = new Processo();
        proc.processarLista(listWsInf);

        System.out.println("Fim do processo: "+LocalTime.now());

    }

}

