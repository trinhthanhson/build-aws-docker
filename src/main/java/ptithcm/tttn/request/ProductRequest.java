package ptithcm.tttn.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProductRequest 
{
    private String product_name;
     
    private int quantity;

    private String image;

    private int price;
     
    private String detail;
     
    private String technology;
     
    private String glass;
     
    private String func;
     
    private String color;
     
    private String machine;
     
    private String sex;
     
    private String accuracy;
     
    private String battery_life;
     
    private String water_resistance;
     
    private String weight;
     
    private String other_features;
     
    private String brand_name;
     
    private String category_name;

    private String status;

}
