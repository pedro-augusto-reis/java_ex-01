package projeto.modelo;

public class SDocEmitido {

  private String nuSeqDocEmitido;
  private java.sql.Date dtEmissao;
  private String coIdentificadorCastor;
  private String blob;

  public String getNuSeqDocEmitido() {
    return nuSeqDocEmitido;
  }

  public void setNuSeqDocEmitido(String nuSeqDocEmitido) {
    this.nuSeqDocEmitido = nuSeqDocEmitido;
  }

  public java.sql.Date getDtEmissao() {
    return dtEmissao;
  }

  public void setDtEmissao(java.sql.Date dtEmissao) {
    this.dtEmissao = dtEmissao;
  }

  public String getCoIdentificadorCastor() {
    return coIdentificadorCastor;
  }

  public void setCoIdentificadorCastor(String coIdentificadorCastor) {
    this.coIdentificadorCastor = coIdentificadorCastor;
  }

  public String getBlob() {
    return blob;
  }

  public void setBlob(String blob) {
    this.blob = blob;
  }

}
