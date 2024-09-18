package ptithcm.tttn.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class CouponRequest {

    private String content;
    private String title;
    private Date start_date;
    private Date end_date;
    private float percent;

}
