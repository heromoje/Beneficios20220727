package bcp.bo.service.model.response;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by BC2078 on 10/17/2015.
 */
public class Category implements Serializable{
    public final static String CATEGORY_RESTAURANTES="RESTAURANTES";
    public final static String CATEGORY_ROPA="ROPA";
    public final static String CATEGORY_ACCESORIOS="ACCESORIOS";
    public final static String CATEGORY_DIVERSION="DIVERSION";
    public final static String CATEGORY_SUPERMERCADOS="SUPERMERCADOS";
    public final static String CATEGORY_SALUD="SALUD Y CUIDADO";
    public final static String CATEGORY_VARIOS="VARIOS";
    public final static String CATEGORY_HOTELERIA="HOTELERIA";
    public final static String CATEGORY_HOGAR="HOGAR";
    public final static String CATEGORY_DELIVERY="DELIVERY";
    public final static String CATEGORY_CAFETERIA="CAFETERIA Y CONFITERIA";
    public final static String CATEGORY_TECNOLOGIA="TECNOLOGIA Y ELECTRONICOS";


    @JsonProperty("id")
    public int Id;
    @JsonProperty("name")
    public String Name;
    @JsonProperty("countPromotion")
    public int CountPromotion;
}