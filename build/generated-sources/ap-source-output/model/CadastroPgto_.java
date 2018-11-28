package model;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.CadastroContas;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-26T13:56:41")
@StaticMetamodel(CadastroPgto.class)
public class CadastroPgto_ { 

    public static volatile SingularAttribute<CadastroPgto, Integer> id;
    public static volatile SingularAttribute<CadastroPgto, Date> dataPgto;
    public static volatile SingularAttribute<CadastroPgto, CadastroContas> fkConta;
    public static volatile SingularAttribute<CadastroPgto, BigInteger> vrPago;

}