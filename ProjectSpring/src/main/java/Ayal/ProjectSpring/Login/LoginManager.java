package Ayal.ProjectSpring.Login;


import Ayal.ProjectSpring.Services.AdminService;
import Ayal.ProjectSpring.Services.CompanyService;
import Ayal.ProjectSpring.Services.CustomerService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class LoginManager {
    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;

    public boolean login(ClientType clientType, String email, String password) {
        switch (clientType) {
            case Administrator:
                if (adminService.login(email, password)) {
                    System.out.println("Logging in...\n----------");
                    return true;
                }
                break;
            case Company:
                if (companyService.login(email, password)) {
                    System.out.println("Logging in...\n----------");
                    return true;
                }
                break;
            case Customer:
                if (customerService.login(email, password)) {
                    System.out.println("Logging in...\n----------");
                    return true;
                }
                break;
        }
        System.out.println("Incorrect email/password...\n----------");
        return false;
    }

}
