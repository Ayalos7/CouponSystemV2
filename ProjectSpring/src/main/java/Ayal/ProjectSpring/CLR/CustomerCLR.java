package Ayal.ProjectSpring.CLR;

import Ayal.ProjectSpring.Beans.Category;
import Ayal.ProjectSpring.Beans.Coupon;
import Ayal.ProjectSpring.Login.ClientType;
import Ayal.ProjectSpring.Login.LoginManager;
import Ayal.ProjectSpring.Services.CompanyService;
import Ayal.ProjectSpring.Services.CustomerService;
import Ayal.ProjectSpring.utils.Art;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * Command line runner 1- simulates customer with all methods related to acquiring coupons
 */
@Component
@Order(3)
@RequiredArgsConstructor
public class CustomerCLR implements CommandLineRunner {
    private final CustomerService customerService;
    private final CompanyService companyService;
    private final LoginManager loginManager;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(Art.customer);
        System.out.println("----------\nTrying to log with incorrect details\n----------");
        if (loginManager.login(ClientType.Customer, "Wrong@gmail.com", "Customer1Pass")) {
            start();
        }
        System.out.println("Trying to log with correct details\n----------");
        if (loginManager.login(ClientType.Customer, "Customer1@gmail.com", "Customer1Pass")) {
            start();
        }
    }

    private void start() {
        try {
            int LoggedCustomerID = customerService.getLoggedCustomerId();

            Coupon coupon1 = Coupon.builder()
                    .id(1)
                    .companyID(3)
                    .category(Category.Electric)
                    .title("Tv Discount")
                    .description("77% Off")
                    .startDate(Date.valueOf("2021-10-07"))
                    .endDate(Date.valueOf("2021-10-17"))
                    .amount(7)
                    .price(77)
                    .image("TV.jpeg")
                    .build();
            Coupon coupon2 = Coupon.builder()
                    .id(2)
                    .companyID(3)
                    .category(Category.Food)
                    .title("Meal Discount")
                    .description("50% Off")
                    .startDate(Date.valueOf("2021-09-07"))
                    .endDate(Date.valueOf("2021-09-17"))
                    .amount(17)
                    .price(7)
                    .image("Meal.jpeg")
                    .build();

            System.out.println("Purchasing 2 coupons\n----------");
            //customerService.purchaseCoupon(coupon1);
            //customerService.purchaseCoupon(coupon2);

            System.out.println("Printing all customer's coupons\n----------");
            for (Coupon coupon : customerService.getAllPurchasedCoupons()) {
                //System.out.println(coupon);
            }

            System.out.println("----------\nPrinting all customer's coupons from a specific category\n----------");
            for (Coupon coupon : customerService.getAllPurchasedCouponsFromCategory(Category.Electric)) {
                System.out.println(coupon);
            }
            System.out.println("----------\nPrinting all customer's coupons up to a specific price\n----------");
            for (Coupon coupon : customerService.getAllPurchasedCouponsWithPriceLessThan(10)) {
                System.out.println(coupon);
            }
            System.out.println("----------\nPrinting customer details\n----------");
            System.out.println(customerService.getCustomerDetails());
        }catch (Exception e){

        }
    }
}
