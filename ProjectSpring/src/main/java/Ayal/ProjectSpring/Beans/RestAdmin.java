package Ayal.ProjectSpring.Beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestAdmin {
    private String token;
    private List<Company> company;
    private List<Coupon> coupon;
    private List<Customer> customer;
}
