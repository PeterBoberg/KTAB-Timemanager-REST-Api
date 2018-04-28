package se.karingotrafiken.timemanager.rest.dto.nonstored.response;

import se.karingotrafiken.timemanager.rest.dto.stored.EmployeeDTO;

public class LoginResponse {

    private String accessToken;
    private EmployeeDTO employee;

    public LoginResponse() {
    }

    public LoginResponse(String accesstoken, EmployeeDTO employee) {
        this.accessToken = accesstoken;
        this.employee = employee;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
