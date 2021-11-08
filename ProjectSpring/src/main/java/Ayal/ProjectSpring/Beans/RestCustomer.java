package Ayal.ProjectSpring.Beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestCustomer {
    private String token;
    private List<Coupon> coupons;
    private List<Customer> customer;
}
