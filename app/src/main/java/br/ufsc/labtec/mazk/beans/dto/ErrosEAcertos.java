package br.ufsc.labtec.mazk.beans.dto;

/**
 *
 * @author Mihael Zamin
 */
public class ErrosEAcertos {
    private int erros;
    private int acertos;

    /**
     * @return the erros
     */
    public int getErros() {
        return erros;
    }

    /**
     * @param erros the erros to set
     */
    public void setErros(int erros) {
        this.erros = erros;
    }

    /**
     * @return the acertos
     */
    public int getAcertos() {
        return acertos;
    }

    /**
     * @param acertos the acertos to set
     */
    public void setAcertos(int acertos) {
        this.acertos = acertos;
    }

    public ErrosEAcertos()
    {
        this.erros = 0;
        this.acertos = 0;
    }

}
