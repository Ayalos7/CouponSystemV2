package Ayal.ProjectSpring.Repositories;

import Ayal.ProjectSpring.Beans.Category;
import Ayal.ProjectSpring.Beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CouponRepo extends JpaRepository<Coupon, Integer> {

    List<Coupon> findByCategoryAndCompanyID(Category category, int companyID);

    List<Coupon> findByCategory(Category category);

    List<Coupon> findByPriceLessThanAndCompanyID(double price, int companyID);

    List<Coupon> findByCompanyID (int companyID);

    List<Coupon> findByTitleContaining (String title);


}
