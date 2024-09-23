package com.luv2code.springboot.thymeleafdemo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    RestTemplate restTemplate = new RestTemplate();

    public EmployeeServiceImpl() {
        restTemplate = new RestTemplate();
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = null;

        String url = "http://localhost:8088/api/employees";

        try {
            String result = restTemplate.getForObject(url, String.class);
            // deserialize result into list of employee
            employees = new ObjectMapper().readValue(result, new TypeReference<List<Employee>>() {
            });
//            System.out.println(employees.get(1).getLastName());
//            System.out.println("Response from API: " + employees);
        } catch (Exception e) {
            System.err.println("Error while consuming API: " + e.getMessage());
        }

        return employees;
    }

    @Override
    public Employee findById(int theId) {
        //Optional<Employee> result = employeeRepository.findById(theId);
        // call rest api on localhost 8088
        String url = "http://localhost:8088/api/employees";

        Employee theEmployee = null;

        ResponseEntity<Employee> responseEntity = restTemplate.getForEntity(url + "/" + theId, Employee.class);

        //if (result.isPresent()) {
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            theEmployee = responseEntity.getBody();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find employee id - " + theId);
        }

        return theEmployee;
    }

    @Override
    public Employee save(Employee theEmployee, String theMethod) {

        // call rest api on localhost 8088
        String url = "http://localhost:8088/api/employees";

        try {
            // serialize thEmployee to JSON
            String employeeJson = new ObjectMapper().writeValueAsString(theEmployee);

            // call rest endpoint
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(employeeJson, headers);
            if ( theMethod.equalsIgnoreCase("POST") ) {
                restTemplate.postForObject(url, request, String.class);
            } else {
                restTemplate.put(url, request, String.class);
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return theEmployee;
    }

    @Override
    public void deleteById(int theId) {

        //employeeRepository.deleteById(theId);
        // call rest api on localhost 8088
        String url = "http://localhost:8088/api/employees";

        //Employee theEmployee = null;

        RestTemplate restTemplate = new RestTemplate();

        // return errors
        ResponseEntity<Void> resp = restTemplate.exchange(url + "/" + theId, HttpMethod.DELETE, null, Void.class);
        //restTemplate.delete(url + "/" + theId, Employee.class);

        //if (result.isPresent()) {
        if (resp.getStatusCode().isError()) {
//            theEmployee = responseEntity.getBody();
//        }
//        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find employee id - " + theId);
        }

    }
}






