package Ayal.ProjectSpring.Services;

import Ayal.ProjectSpring.Beans.Company;
import Ayal.ProjectSpring.Beans.Coupon;
import Ayal.ProjectSpring.Beans.Customer;
import Ayal.ProjectSpring.CustomExceptions.AdminApiExceptions.AdminApiResponseException;
import Ayal.ProjectSpring.Repositories.CompanyRepo;
import Ayal.ProjectSpring.Repositories.CouponRepo;
import Ayal.ProjectSpring.Repositories.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminService {
    private final CompanyRepo companyRepo;
    private final CustomerRepo customerRepo;
    private final CouponRepo couponRepo;

    public Boolean login(String email, String password) {
        return (email.equals("************") && password.equals("*****"));
    }

    public void addCompany(Company company) throws AdminApiResponseException {
        if (!(companyRepo.findByEmail(company.getEmail()) == null)) {
            throw new AdminApiResponseException("Company email already exists in system");
        }
        if (!(companyRepo.findByName(company.getName()) == null)) {
            throw new AdminApiResponseException("Company name already exists in system");
        }
        companyRepo.save(company);
    }

    public void updateCompany(Company company) throws AdminApiResponseException {
        if (!companyRepo.existsById(company.getId())) {
            throw new AdminApiResponseException("Company does not exist in data");
        }
        Company companyToBeUpdated = companyRepo.findById(company.getId());
        companyToBeUpdated.setEmail(company.getEmail());
        companyToBeUpdated.setPassword(company.getPassword());
        companyRepo.saveAndFlush(companyToBeUpdated);
    }

    public void deleteCompany(int companyID) throws AdminApiResponseException {
        if (!companyRepo.existsById(companyID)) {
            throw new AdminApiResponseException("Company does not exist in data");
        }
        List<Coupon> coupons = couponRepo.findByCompanyID(companyID);
        for(Coupon coupon: coupons){
            couponRepo.deleteById(coupon.getId());
        }
        companyRepo.deleteById(companyID);

    }

    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    public Company getOneCompany(int id) {
        return companyRepo.findById(id);
    }

    public void addCustomer(Customer customer) throws AdminApiResponseException {
        if (!(customerRepo.findByEmail(customer.getEmail()) == null)) {
            throw new AdminApiResponseException("Customer email already exists in system");
        }
        customerRepo.save(customer);
    }

    public void updateCustomer(Customer customer) throws AdminApiResponseException {
        if (!customerRepo.existsById(customer.getId())) {
            throw new AdminApiResponseException("Customer does not exist in data");
        }
        Customer customerToBeUpdated = customerRepo.findById(customer.getId());
        customerToBeUpdated.setEmail(customer.getEmail());
        customerToBeUpdated.setPassword(customer.getPassword());
        customerToBeUpdated.setFirstName(customer.getFirstName());
        customerToBeUpdated.setLastName(customer.getLastName());
        customerRepo.saveAndFlush(customerToBeUpdated);
    }

    public void deleteCustomer(int customerID) throws AdminApiResponseException {
        if (!customerRepo.existsById(customerID)) {
            throw new AdminApiResponseException("Customer does not exist in data");
        }
        customerRepo.deleteById(customerID);
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public Customer getOneCustomer(int id) {
        return customerRepo.findById(id);
    }
}
