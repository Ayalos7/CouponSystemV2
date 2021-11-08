package Ayal.ProjectSpring.CLR;

import Ayal.ProjectSpring.Beans.Category;
import Ayal.ProjectSpring.Beans.Coupon;
import Ayal.ProjectSpring.CustomExceptions.CompanyApiResponseException;
import Ayal.ProjectSpring.Login.ClientType;
import Ayal.ProjectSpring.Login.LoginManager;
import Ayal.ProjectSpring.Services.CompanyService;
import Ayal.ProjectSpring.utils.Art;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;


@Component
@Order(2)
@RequiredArgsConstructor
/**
 * Command line runner 1- simulates company with all methods related to coupons
 */
public class CompanyCLR implements CommandLineRunner {
    private final CompanyService companyService;
    private final LoginManager loginManager;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(Art.company);
        System.out.println("----------\nTrying to log with incorrect details\n----------");
        if (loginManager.login(ClientType.Company, "Wrong@gmail.com", "Company3Pass")) {
            start();
        }
        System.out.println("Trying to log with correct details\n----------");
        if (loginManager.login(ClientType.Company, "Company3@gmail.com", "Company3Pass")) {
            start();
        }

    }

    public void start() {
        try {
            int LoggedCompanyID = companyService.getLoggedCompanyID();
            Coupon coupon1 = Coupon.builder()
                    .companyID(LoggedCompanyID)
                    .category(Category.Electric)
                    .title("Tv Discount")
                    .description("25% Off")
                    .startDate(Date.valueOf("2021-10-07"))
                    .endDate(Date.valueOf("2022-10-17"))
                    .amount(40)
                    .price(200)
                    .image("https://backendlessappcontent.com/E97C0748-D36F-035C-FFC0-4FB516E81300/console/ztiiwzqhxxdjzlyizenqrvmdbzyxdbeunmww/files/view/tv.jpg")
                    .build();
            Coupon coupon2 = Coupon.builder()
                    .companyID(LoggedCompanyID)
                    .category(Category.Food)
                    .title("Large Hamburger")
                    .description("Present this coupon for a large hamburger")
                    .startDate(Date.valueOf("2021-09-07"))
                    .endDate(Date.valueOf("2022-05-07"))
                    .amount(30)
                    .price(35)
                    .image("https://backendlessappcontent.com/E97C0748-D36F-035C-FFC0-4FB516E81300/console/ztiiwzqhxxdjzlyizenqrvmdbzyxdbeunmww/files/view/hamburger.jpg")
                    .build();
            Coupon coupon3 = Coupon.builder()
                    .companyID(LoggedCompanyID)
                    .category(Category.Vacation)
                    .title("Hotel Coupon")
                    .description("Present at the reception for 2 free nights")
                    .startDate(Date.valueOf("2021-01-07"))
                    .endDate(Date.valueOf("2022-09-27"))
                    .amount(35)
                    .price(600)
                    .image("https://backendlessappcontent.com/E97C0748-D36F-035C-FFC0-4FB516E81300/console/ztiiwzqhxxdjzlyizenqrvmdbzyxdbeunmww/files/view/hotel.jpg")
                    .build();
            Coupon coupon4 = Coupon.builder()
                    .companyID(LoggedCompanyID)
                    .category(Category.Food)
                    .title("Sushi")
                    .description("Present this coupon for 8 pieces of sushi ")
                    .startDate(Date.valueOf("2021-03-07"))
                    .endDate(Date.valueOf("2022-11-11"))
                    .amount(45)
                    .price(25)
                    .image("https://backendlessappcontent.com/E97C0748-D36F-035C-FFC0-4FB516E81300/console/ztiiwzqhxxdjzlyizenqrvmdbzyxdbeunmww/files/view/sushi.jpg")
                    .build();
            Coupon coupon5 = Coupon.builder()
                    .companyID(LoggedCompanyID)
                    .category(Category.Electric)
                    .title("Dishwasher Discount")
                    .description("40% Off")
                    .startDate(Date.valueOf("2021-03-10"))
                    .endDate(Date.valueOf("2022-10-12"))
                    .amount(15)
                    .price(300)
                    .image("https://backendlessappcontent.com/E97C0748-D36F-035C-FFC0-4FB516E81300/console/ztiiwzqhxxdjzlyizenqrvmdbzyxdbeunmww/files/view/washingmachine.jpg")
                    .build();
            Coupon coupon6 = Coupon.builder()
                    .companyID(LoggedCompanyID)
                    .category(Category.Vacation)
                    .title("Vacation")
                    .description("Flight & Hotel")
                    .startDate(Date.valueOf("2021-05-11"))
                    .endDate(Date.valueOf("2022-09-12"))
                    .amount(20)
                    .price(1500)
                    .image("https://backendlessappcontent.com/E97C0748-D36F-035C-FFC0-4FB516E81300/console/ztiiwzqhxxdjzlyizenqrvmdbzyxdbeunmww/files/view/vacationbeach.jpg")
                    .build();
            Coupon coupon7 = Coupon.builder()
                    .companyID(LoggedCompanyID)
                    .category(Category.Food)
                    .title("Restaurant")
                    .description("Appetizer+Drink+Main course+ Dessert  ")
                    .startDate(Date.valueOf("2021-05-11"))
                    .endDate(Date.valueOf("2022-09-12"))
                    .amount(50)
                    .price(120)
                    .image("https://backendlessappcontent.com/E97C0748-D36F-035C-FFC0-4FB516E81300/console/ztiiwzqhxxdjzlyizenqrvmdbzyxdbeunmww/files/view/kitchen.jpg")
                    .build();
            Coupon coupon8 = Coupon.builder()
                    .companyID(LoggedCompanyID)
                    .category(Category.Clothing)
                    .title("Jeans")
                    .description("2 pairs of jeans")
                    .startDate(Date.valueOf("2021-07-11"))
                    .endDate(Date.valueOf("2022-09-12"))
                    .amount(40)
                    .price(100)
                    .image("https://backendlessappcontent.com/E97C0748-D36F-035C-FFC0-4FB516E81300/console/ztiiwzqhxxdjzlyizenqrvmdbzyxdbeunmww/files/view/jeans.jpg")
                    .build();
            Coupon coupon9 = Coupon.builder()
                    .companyID(LoggedCompanyID)
                    .category(Category.Fitness)
                    .title("Dumbbells")
                    .description("set of dumbbells")
                    .startDate(Date.valueOf("2021-11-11"))
                    .endDate(Date.valueOf("2022-12-12"))
                    .amount(15)
                    .price(100)
                    .image("https://backendlessappcontent.com/E97C0748-D36F-035C-FFC0-4FB516E81300/console/ztiiwzqhxxdjzlyizenqrvmdbzyxdbeunmww/files/view/dumbbells.jpg")
                    .build();
//adding a coupon
            System.out.println("Adding 7 coupons to the logged company\n----------");
            try {
                companyService.addCoupon(coupon1);
                companyService.addCoupon(coupon2);
                companyService.addCoupon(coupon3);
                companyService.addCoupon(coupon4);
                companyService.addCoupon(coupon5);
                companyService.addCoupon(coupon6);
                companyService.addCoupon(coupon7);
                companyService.addCoupon(coupon8);
                companyService.addCoupon(coupon9);
            } catch (CompanyApiResponseException companyApiResponseException) {
                System.out.println(companyApiResponseException.getMessage());
            }

            System.out.println("Deleting 1 coupon, updating 1 coupon\n----------");

//updating 1 coupon
            //coupon2.setTitle("Meal Discount Updated");
            //coupon2.setDescription("50% Off Updated");
            //try {
            //companyService.updateCoupon(coupon2);
            //}catch (CompanyApiResponseException companyApiResponseException){
            //System.out.println(companyApiResponseException.getMessage());
            //}

//deleting 1 coupon
            //try {
            //companyService.deleteCoupon(coupon3.getId());
            //}catch (CompanyApiResponseException companyApiResponseException){
            //System.out.println(companyApiResponseException.getMessage());
            //}

//printing Logged Company's coupons
            System.out.println("Printing Logged Company's coupons\n----------");
            for (Coupon coupon : companyService.getAllCoupons()) {
                System.out.println(coupon);
            }

//printing Logged Company's coupons from a specific category
            System.out.println("----------\nprinting Logged Company's coupons from a specific category\n----------");
            System.out.println(companyService.getAllCouponsFromCategory(Category.Electric));

//printing Logged Company's coupons up to a specific price
            System.out.println("----------\n//printing Logged Company's coupons up to a specific price\n----------");
            System.out.println(companyService.getAllCouponsWithPriceLessThan(100));

//printing Logged Company's details
            System.out.println("----------\nprinting Logged Company's details\n----------");
            System.out.println(companyService.getCompanyDetails());
            System.out.println("----------\nCompany facade is over\n----------");
        }catch (Exception e){

        }
    }
}
