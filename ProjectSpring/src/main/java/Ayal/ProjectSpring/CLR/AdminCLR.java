package Ayal.ProjectSpring.CLR;

import Ayal.ProjectSpring.Beans.Company;
import Ayal.ProjectSpring.Beans.Customer;
import Ayal.ProjectSpring.CustomExceptions.AdminApiExceptions.AdminApiResponseException;
import Ayal.ProjectSpring.Login.ClientType;
import Ayal.ProjectSpring.Login.LoginManager;
import Ayal.ProjectSpring.Services.AdminService;
import Ayal.ProjectSpring.utils.Art;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(1)
@RequiredArgsConstructor
/**
 * Command line runner 1- simulates admin with all methods related to company/customers
 */
public class AdminCLR implements CommandLineRunner {
    private final AdminService adminService;
    private final LoginManager loginManager;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(Art.startMsg);
        System.out.println(Art.admin);
        System.out.println("----------\nTrying to log with incorrect details\n----------");
        if (loginManager.login(ClientType.Administrator, "Wrong@gmail.com", "WrongPass")) {
            start();
        }
        System.out.println("Trying to log with correct details\n----------");
        if (loginManager.login(ClientType.Administrator, "*****@*****.***", "*****")) {
            start();
        }
    }

    private void start() {
        try {
            Company company1 = Company.builder()
                    .name("Company1")
                    .email("Company1@gmail.com")
                    .password("Company1Pass")
                    .build();
            Company company2 = Company.builder()
                    .name("Company2")
                    .email("Company2@gmail.com")
                    .password("Company2Pass")
                    .build();
            Company company3 = Company.builder()
                    .name("Company3")
                    .email("Company3@gmail.com")
                    .password("Company3Pass")
                    .build();

//adding 3 companies
            System.out.println("Adding 3 companies\n----------");

            try {
                adminService.addCompany(company1);
                adminService.addCompany(company2);
                adminService.addCompany(company3);
            } catch (AdminApiResponseException adminApiResponseException) {
                System.out.println(adminApiResponseException.getMessage());
            }


//updating 1 company
            System.out.println("Deleting 1 company, updating 1 company\n----------");
            company1.setEmail("Company1Updated@gmail.com");
            company1.setPassword("Company1UpdatedPass");
            try {
                adminService.updateCompany(company1);
            } catch (AdminApiResponseException adminApiResponseException) {
                System.out.println(adminApiResponseException.getMessage());
            }
//deleting 1 company
            try {
                adminService.deleteCompany(company2.getId());
            } catch (AdminApiResponseException adminApiResponseException) {
                System.out.println(adminApiResponseException.getMessage());
            }
//printing all companies
            System.out.println("Printing all companies\n----------");
            for (Company company : adminService.getAllCompanies()) {
                System.out.println(company);
            }
//print 1 specific company
            System.out.println("----------\n-Printing only 1 company\n----------");
            System.out.println(adminService.getOneCompany(1));

            Customer customer1 = Customer.builder()
                    .firstName("Customer1First")
                    .lastName("Customer1Last")
                    .email("Customer1@gmail.com")
                    .password("Customer1Pass")
                    .build();
            Customer customer2 = Customer.builder()
                    .id(2)
                    .firstName("Customer2First")
                    .lastName("Customer2Last")
                    .email("Customer2@gmail.com")
                    .password("Customer2Pass")
                    .build();
            Customer customer3 = Customer.builder()
                    .firstName("Customer3First")
                    .lastName("Customer3Last")
                    .email("Customer3@gmail.com")
                    .password("Customer3Pass")
                    .build();
//adding 3 customers
            System.out.println("----------\nAdding 3 customers\n----------");
            try {
                adminService.addCustomer(customer1);
                adminService.addCustomer(customer2);
                adminService.addCustomer(customer3);
            } catch (AdminApiResponseException adminApiResponseException) {
                System.out.println(adminApiResponseException.getMessage());
            }
//updating 1 customer
            System.out.println("Deleting 1 customer, updating 1 customer\n----------");
            customer2.setEmail("Customer2Updated@gmail.com");
            customer2.setPassword("Customer2Updated@gmail.com");
            try {
                adminService.updateCustomer(customer2);
            } catch (AdminApiResponseException adminApiResponseException) {
                System.out.println(adminApiResponseException.getMessage());
            }
//deleting 1 customer
            try {
                adminService.deleteCustomer(customer3.getId());
            } catch (AdminApiResponseException adminApiResponseException) {
                System.out.println(adminApiResponseException.getMessage());
            }
//printing all customers
            System.out.println("Printing all customers\n----------");
            for (Customer customer : adminService.getAllCustomers()) {
                System.out.println(customer);
            }
//printing 1 specific customer
            System.out.println("Printing only 1 customer\n----------");
            System.out.println(adminService.getOneCustomer(1));
            System.out.println("----------\nadmin facade is over\n----------");
        }catch (Exception e){

        }
    }
}
