package com.crestwood.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by ryan on 11/14/17.
 */
@Entity
@Table(name = "payDate")
public class PayDate implements Serializable{


    @EmbeddedId
    private PayDateKey payDateKey;








    public PayDate(){}
    public PayDate(String id, Date date) {
        payDateKey = new PayDateKey(id, date);
       /* paymentPlanId = id;
        dueDate = date;*/
    }

    public PayDateKey getPayDateKey() {
        return payDateKey;
    }

    public void setPayDateKey(PayDateKey payDateKey) {
        this.payDateKey = payDateKey;
    }
}
