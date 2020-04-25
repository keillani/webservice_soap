package br.com.caelum.estoque.modelo.usuario;

import javax.xml.ws.WebFault;

@WebFault(name="AutorizacaoFault")
public class AutorizacaoException extends Exception {
    //esse numero eh relacionado com a serializacao do java.io mas nao importa nesse contexto
    private static final long serialVersionUID = 1L;

    public AutorizacaoException(String mensagem) {
        super(mensagem);
    }

    public String getFaultInfo() {
        return "Token invalido";
    }
}
