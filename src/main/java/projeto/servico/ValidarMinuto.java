package projeto.servico;

import projeto.modelo.MergeMinuto;
import projeto.modelo.SDocEmitido;
import projeto.modelo.SGerenciamentoCastor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidarMinuto {

    private static Logger log = LoggerFactory.getLogger("validarMinutoLogger");

    public ArrayList<MergeMinuto> validarLista(ArrayList<SDocEmitido> alDoc, ArrayList<SGerenciamentoCastor> alGer){

        int qtdDifMinutErro = 0;
        int qtdDifMinutAprov = 0;
        ArrayList<MergeMinuto> alF = new ArrayList<MergeMinuto>();

        for (SDocEmitido sDoc : alDoc){
                for (SGerenciamentoCastor sGer : alGer){
                    if (sDoc.getCoIdentificadorCastor().equals(sGer.getNuSeqArquivo())){

                        int intervalorMinuto = 10; // regra para trazer apenas as IDs em que a diferenca de entrada seja de 10 minutos para mais ou menos;
                        int intervalorMinutoAprovado = 4; // regra para gerar log. Caso a ID seja identificada no espaco de 10 minutos, ira determinar quais sao mais propicias ao erro, estando proximas dos 10 minutos;
                        Date d1 = sDoc.getDtEmissao();
                        Date d2 = sGer.getDtInclusao();

                        long minuto = d1.getTime();

                        Date minutosMais = new Date(minuto + (intervalorMinuto * 60000));
                        Date minutoMenos = new Date(minuto - (intervalorMinuto * 60000));
                        Date minutosMaisAprovado = new Date(minuto + (intervalorMinutoAprovado * 60000));

                        Calendar cal1 = Calendar.getInstance();
                        Calendar cal2 = Calendar.getInstance();

                        cal1.setTime(d1);
                        StringBuilder um = new StringBuilder();
                        um.append(String.valueOf(cal1.get(Calendar.HOUR)));
                        um.append(":");
                        um.append(String.valueOf(cal1.get(Calendar.MINUTE)));
                        um.append(":");
                        um.append(String.valueOf(cal1.get(Calendar.SECOND)));


                        cal2.setTime(d2);
                        StringBuilder dois = new StringBuilder();
                        dois.append(String.valueOf(cal2.get(Calendar.HOUR)));
                        dois.append(":");
                        dois.append(String.valueOf(cal2.get(Calendar.MINUTE)));
                        dois.append(":");
                        dois.append(String.valueOf(cal2.get(Calendar.SECOND)));

                        if (d2.before(minutosMais) && d2.after(minutoMenos)){
                            MergeMinuto fin = new MergeMinuto();
                            fin.setId(sDoc.getCoIdentificadorCastor());
                            fin.setBlob(sDoc.getBlob());
                            fin.setAplicacao(sGer.getCoAplicacao());
                            alF.add(fin);
                            qtdDifMinutAprov++;
                            if(d2.after(minutosMaisAprovado)){
                                log.info("::VALIDARMINUTO::\n["+qtdDifMinutAprov+"][ID: "+sDoc.getCoIdentificadorCastor()+" aprovada nas regras] - [Entre "+intervalorMinutoAprovado+" e "+intervalorMinuto+" minutos]\n>> :     "
                                        + d1 + " - " + um.toString() + "\n>> WS-Castor: "
                                        + d2 + " - " + dois.toString() +"\n");
                            }
                            else{
                                log.info("::VALIDARMINUTO::\n["+qtdDifMinutAprov+"][ID: "+sDoc.getCoIdentificadorCastor()+" aprovada nas regras] - [Abaixo de "+intervalorMinutoAprovado+" minutos]\n>> :     "
                                        + d1 + " - " + um.toString() + "\n>> WS-Castor: "
                                        + d2 + " - " + dois.toString() +"\n");
                            }
                        }
                        else{
                            qtdDifMinutErro++;
                            log.error("::VALIDARMINUTO::\n[" + qtdDifMinutErro + "] - [ID: " + sDoc.getNuSeqDocEmitido() +
                                    " não está no intervalo de " + intervalorMinuto + " minutos]\n>> :     "
                                    + d1 + " - " + um.toString() + "\n>> WS-Castor: "
                                    + d2 + " - " + dois.toString() +"\n");
                        }
                    }
                }
            }
        return alF;
    }
}
