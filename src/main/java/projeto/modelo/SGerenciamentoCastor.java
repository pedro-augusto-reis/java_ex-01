package projeto.modelo;


public class SGerenciamentoCastor {

  private String nuSeqArquivo;
  private String coAplicacao;
  private java.sql.Date dtInclusao;
  private String status;


  public String getNuSeqArquivo() {
    return nuSeqArquivo;
  }

  public void setNuSeqArquivo(String nuSeqArquivo) {
    this.nuSeqArquivo = nuSeqArquivo;
  }

  public String getCoAplicacao() {
    return coAplicacao;
  }

  public void setCoAplicacao(String coAplicacao) {
    this.coAplicacao = coAplicacao;
  }

  public java.sql.Date getDtInclusao() {
    return dtInclusao;
  }

  public void setDtInclusao(java.sql.Date dtInclusao) {
    this.dtInclusao = dtInclusao;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
