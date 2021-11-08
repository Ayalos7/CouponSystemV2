package Ayal.ProjectSpring.Threads;

import Ayal.ProjectSpring.Beans.Company;
import Ayal.ProjectSpring.Beans.Coupon;
import Ayal.ProjectSpring.CustomExceptions.CompanyApiResponseException;
import Ayal.ProjectSpring.Services.AdminService;
import Ayal.ProjectSpring.Services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class CouponExpirationDailyJob {
    private final CompanyService companyService;
    private final AdminService adminService;


    @Async
    @Scheduled(fixedRate = 1000*60*60)
    public void eraseCoupon(){
          List<Coupon> couponList= companyService.getAllCoupons();
            for (Coupon coupon:couponList){
                if (coupon.getEndDate().before(new java.sql.Date(System.currentTimeMillis()))){
                    try {
                        companyService.deleteCoupon(coupon.getId());
                    }catch (CompanyApiResponseException companyApiResponseException){
                        System.out.println(companyApiResponseException.getMessage());
                    }
                    System.out.println("this coupon expired\n" +coupon);

            }
        }
    }



}
