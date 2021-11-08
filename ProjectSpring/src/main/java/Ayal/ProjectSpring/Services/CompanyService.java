package Ayal.ProjectSpring.Services;

import Ayal.ProjectSpring.Beans.Category;
import Ayal.ProjectSpring.Beans.Company;
import Ayal.ProjectSpring.Beans.Coupon;
import Ayal.ProjectSpring.CustomExceptions.CompanyApiResponseException;
import Ayal.ProjectSpring.Repositories.CompanyRepo;
import Ayal.ProjectSpring.Repositories.CouponRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepo companyRepo;
    private final CouponRepo couponRepo;
    private int LoggedCompanyID;

    public int getLoggedCompanyID() {
        return this.LoggedCompanyID;
    }

    public Boolean login(String email, String password) {
        Company company = companyRepo.findByEmailAndPassword(email, password);
        if (company == null) {
            return false;
        }
        LoggedCompanyID = company.getId();
        return true;
    }

    public void addCoupon(Coupon coupon) throws CompanyApiResponseException {
        for (Coupon coupIterator : getAllCoupons()) {
            if (coupon.getTitle().equals(coupIterator.getTitle())) {
                throw new CompanyApiResponseException("Can't use this coupon title - It exists already");
            }
        }
        coupon.setCompanyID(getLoggedCompanyID());
        couponRepo.save(coupon);
    }

    public void updateCoupon(Coupon coupon) throws CompanyApiResponseException {
        if (!couponRepo.existsById(coupon.getId())) {
            throw new CompanyApiResponseException("coupon does not exist in data");
        }
        Coupon couponToBeUpdated = couponRepo.getOne(coupon.getId());
        couponToBeUpdated.setTitle(coupon.getTitle());
        couponToBeUpdated.setDescription(coupon.getDescription());

        couponToBeUpdated.setAmount(coupon.getAmount());
        couponToBeUpdated.setStartDate(coupon.getStartDate());
        couponToBeUpdated.setEndDate(coupon.getEndDate());
        couponToBeUpdated.setCategory(coupon.getCategory());
        couponToBeUpdated.setPrice(coupon.getPrice());
        couponRepo.saveAndFlush(couponToBeUpdated);
    }

    public void deleteCoupon(int couponID) throws CompanyApiResponseException {
        if (!couponRepo.existsById(couponID)) {
            throw new CompanyApiResponseException("coupon does not exist in data");
        }
        Coupon coupon = couponRepo.getOne(couponID);
        if (coupon.getCompanyID() != getLoggedCompanyID()) {
            throw new CompanyApiResponseException("the coupon you tried to delete does not belong to you");
        }
        couponRepo.deleteById(couponID);
    }

    public List<Coupon> getAllCoupons() {
        return couponRepo.findByCompanyID(LoggedCompanyID);
    }

    public List<Coupon> getAllCouponsFromCategory(Category category) {
        return couponRepo.findByCategoryAndCompanyID(category, LoggedCompanyID);
    }

    public List<Coupon> getAllCouponsWithPriceLessThan(double price) {
        return couponRepo.findByPriceLessThanAndCompanyID(price, LoggedCompanyID);
    }

    public Company getCompanyDetails() {
        return companyRepo.findById(LoggedCompanyID);
    }
}
