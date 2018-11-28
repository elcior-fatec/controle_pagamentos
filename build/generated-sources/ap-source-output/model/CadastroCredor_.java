package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.CadastroContas;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-26T13:56:41")
@StaticMetamodel(CadastroCredor.class)
public class CadastroCredor_ { 

    public static volatile SingularAttribute<CadastroCredor, String> endereco;
    public static volatile CollectionAttribute<CadastroCredor, CadastroContas> cadastroContasCollection;
    public static volatile SingularAttribute<CadastroCredor, String> nome;
    public static volatile SingularAttribute<CadastroCredor, Integer> id;
    public static volatile SingularAttribute<CadastroCredor, String> cnpj;

}