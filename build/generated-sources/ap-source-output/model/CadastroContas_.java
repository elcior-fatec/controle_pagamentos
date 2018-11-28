package model;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.CadastroCredor;
import model.CadastroPgto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-26T13:56:41")
@StaticMetamodel(CadastroContas.class)
public class CadastroContas_ { 

    public static volatile SingularAttribute<CadastroContas, Date> dataVencimento;
    public static volatile SingularAttribute<CadastroContas, BigInteger> valor;
    public static volatile SingularAttribute<CadastroContas, Integer> id;
    public static volatile CollectionAttribute<CadastroContas, CadastroPgto> cadastroPgtoCollection;
    public static volatile SingularAttribute<CadastroContas, CadastroCredor> fkCredor;
    public static volatile SingularAttribute<CadastroContas, String> descricao;

}