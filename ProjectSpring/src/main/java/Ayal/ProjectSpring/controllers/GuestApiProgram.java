package Ayal.ProjectSpring.controllers;

import Ayal.ProjectSpring.Beans.Company;
import Ayal.ProjectSpring.Beans.Coupon;
import Ayal.ProjectSpring.Beans.Customer;
import Ayal.ProjectSpring.Beans.RestAdmin;
import Ayal.ProjectSpring.CustomExceptions.AdminApiExceptions.AdminApiResponseException;
import Ayal.ProjectSpring.CustomExceptions.LoginException;
import Ayal.ProjectSpring.Services.AdminService;
import Ayal.ProjectSpring.Services.GuestService;
import Ayal.ProjectSpring.Tokens.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("apiGuest")

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class GuestApiProgram {
    private final GuestService guestService;
    private final AdminService adminService;


    @PostMapping("Guest/getAllCoupons")
    private ResponseEntity<?> getAllCoupons() throws AdminApiResponseException, LoginException {
        return ResponseEntity.ok()
                .body(guestService.AllCoupons());
    }

    @PostMapping("Guest/getAllCouponsByName")
    private ResponseEntity<?> getAllCoupons(@RequestBody Coupon coupon) throws AdminApiResponseException, LoginException {
        return ResponseEntity.ok()
                .body(guestService.AllCouponsByName(coupon.getTitle()));
    }
    @PostMapping("Guest/getAllCouponsFromCategory")
    private ResponseEntity<?> getAllCouponsFromCategory(@RequestBody Coupon coupon) throws AdminApiResponseException, LoginException {
        return ResponseEntity.ok()
                .body(guestService.AllCouponsByCategory(coupon.getCategory()));
    }

    @PostMapping("Guest/CreateCustomer")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) throws AdminApiResponseException, LoginException {

        try {
            adminService.addCustomer(customer);
        } catch (AdminApiResponseException adminApiResponseException) {
            throw new AdminApiResponseException(adminApiResponseException.getMessage());
        }
        return ResponseEntity.ok()

                .body("Account was created");
    }
}
