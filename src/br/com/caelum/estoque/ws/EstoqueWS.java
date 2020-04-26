package br.com.caelum.estoque.ws;

import br.com.caelum.estoque.modelo.item.*;
import br.com.caelum.estoque.modelo.usuario.AutorizacaoException;
import br.com.caelum.estoque.modelo.usuario.TokenDao;
import br.com.caelum.estoque.modelo.usuario.TokenUsuario;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.ResponseWrapper;
import java.util.List;

@WebService
//@SOAPBinding(style= SOAPBinding.Style.RPC)
//@SOAPBinding(style= SOAPBinding.Style.DOCUMENT, parameterStyle= SOAPBinding.ParameterStyle.BARE)
@SOAPBinding(style= SOAPBinding.Style.DOCUMENT, parameterStyle= SOAPBinding.ParameterStyle.WRAPPED)

public class EstoqueWS {

    private ItemDao dao = new ItemDao();

    @WebMethod (operationName = "TodosOsItens")
//    @ResponseWrapper(localName="itens")
    @WebResult (name = "itens")
    public ListaItens getItens(@WebParam(name= "filtros") Filtros filtros){
        System.out.println("Chamando getItens()");
        List<Filtro> lista = filtros.getLista();
        List<Item> itensResultado = dao.todosItens(lista);
        return new ListaItens(itensResultado);
    }

    @WebMethod(operationName = "CadastrarItem")
    @WebResult(name = "item")
    public Item cadastrarItem (@WebParam(name = "tokenUsuario", header = true)TokenUsuario tokenUsuario,
                               @WebParam(name = "item") Item item)
            throws AutorizacaoException {
        System.out.println("Cadastrando" + item+","+tokenUsuario);

        boolean valido = new TokenDao().ehValido(tokenUsuario);

        if (!valido){
            throw new AutorizacaoException("Autorizacao falhou");
        }

        new ItemValidador(item).validate();

        this.dao.cadastrar(item);
        return item;
    }
}
