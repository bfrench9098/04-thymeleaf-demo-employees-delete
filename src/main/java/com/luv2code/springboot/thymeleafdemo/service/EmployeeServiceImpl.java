package com.luv2code.springboot.thymeleafdemo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

//    @Autowired
//    public EmployeeServiceImpl(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

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

//    @Override
//    public Employee findById(int theId) {
//        Optional<Employee> result = employeeRepository.findById(theId);
//
//        Employee theEmployee = null;
//
//        if (result.isPresent()) {
//            theEmployee = result.get();
//        }
//        else {
//            // we didn't find the employee
//            throw new RuntimeException("Did not find employee id - " + theId);
//        }
//
//        return theEmployee;
//    }
//
//    @Override
//    public Employee save(Employee theEmployee) {
//
//        return employeeRepository.save(theEmployee);
//    }
//
//    @Override
//    public void deleteById(int theId) {
//
//        employeeRepository.deleteById(theId);
//    }
}






