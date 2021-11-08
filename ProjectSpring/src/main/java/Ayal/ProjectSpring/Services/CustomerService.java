package Ayal.ProjectSpring.Services;

import Ayal.ProjectSpring.Beans.Category;
import Ayal.ProjectSpring.Beans.Coupon;
import Ayal.ProjectSpring.Beans.Customer;

import Ayal.ProjectSpring.Repositories.CouponRepo;
import Ayal.ProjectSpring.Repositories.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;
    private final CouponRepo couponRepo;
    private int LoggedCustomerId;


    public int getLoggedCustomerId() {
        return this.LoggedCustomerId;
    }

    public Boolean login(String email, String password) {
        Customer customer = customerRepo.findByEmailAndPassword(email, password);
        if (customer == null) {
            return false;
        }
        LoggedCustomerId = customer.getId();
        return true;
    }

    public void purchaseCoupon(Coupon coupon) {
        if(coupon.getAmount()==0){
            System.out.println("Coupon is not in stock!");
            return;
        }
        if(coupon.getEndDate().before(new Date(System.currentTimeMillis()))){
            System.out.println("Coupon expiration date have passed");
            return;
        }
        Customer customer = getCustomerDetails();
        List<Coupon> customerCoupons = customer.getCoupons();
        customerCoupons.add(couponRepo.getOne(coupon.getId()));
        customer.setCoupons(customerCoupons);
        coupon.setAmount(coupon.getAmount()-1);
        couponRepo.saveAndFlush(coupon);
        customerRepo.saveAndFlush(customer);


    }

    public List<Coupon> getAllPurchasedCoupons() {
        return getCustomerDetails().getCoupons();
    }

    public List<Coupon> getAllPurchasedCouponsFromCategory(Category category) {
       List<Coupon> coupons= getAllPurchasedCoupons();
       List<Coupon> couponsFromSpecificCategory = new ArrayList<>();
       for(Coupon coupon:coupons){
           if (coupon.getCategory().equals(category)){
               couponsFromSpecificCategory.add(coupon);
           }
       }
       return couponsFromSpecificCategory;
     }

      public List<Coupon> getAllPurchasedCouponsWithPriceLessThan(double price) {
          List<Coupon> coupons= getAllPurchasedCoupons();
          List<Coupon> couponsUpToSpecificPrice = new ArrayList<>();
          for(Coupon coupon:coupons){
              if (coupon.getPrice()<price){
                  couponsUpToSpecificPrice.add(coupon);
              }
          }
          return couponsUpToSpecificPrice;
     }

    public Customer getCustomerDetails() {
        return customerRepo.findById(LoggedCustomerId);
    }


    //No need for this now, implemented in another way
    public void updateCouponAmount(Coupon coupon)  {

        Coupon couponToBeUpdated = couponRepo.getOne(coupon.getId());
        couponToBeUpdated.setTitle(coupon.getTitle());
        couponToBeUpdated.setDescription(coupon.getDescription());
        couponToBeUpdated.setImage(couponToBeUpdated.getImage());
        couponToBeUpdated.setAmount(coupon.getAmount());
        couponToBeUpdated.setStartDate(coupon.getStartDate());
        couponToBeUpdated.setEndDate(coupon.getEndDate());
        couponToBeUpdated.setCategory(coupon.getCategory());
        couponToBeUpdated.setPrice(coupon.getPrice());
        couponRepo.saveAndFlush(couponToBeUpdated);
    }

}
