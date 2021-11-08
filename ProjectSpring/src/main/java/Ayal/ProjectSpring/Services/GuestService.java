package Ayal.ProjectSpring.Services;

import Ayal.ProjectSpring.Beans.Category;
import Ayal.ProjectSpring.Beans.Coupon;
import Ayal.ProjectSpring.Repositories.CompanyRepo;
import Ayal.ProjectSpring.Repositories.CouponRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final CompanyRepo companyRepo;
    private final CouponRepo couponRepo;


    public List<Coupon> AllCoupons(){
        return couponRepo.findAll();
    }

    public List<Coupon> AllCouponsByName(String name){
        return couponRepo.findByTitleContaining(name);
    }

    public List<Coupon> AllCouponsByCategory(Category category){
        return couponRepo.findByCategory(category);
    }
}
