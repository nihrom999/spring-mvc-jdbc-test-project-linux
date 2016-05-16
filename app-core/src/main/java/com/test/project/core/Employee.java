package com.test.project.core;

import java.sql.Date;

/**
 * Created by Arty on 21.02.2016.
 */
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Double salary;
    private Long departmentId;

    public Employee() {
    }

    private Employee(Builder builder){
        firstName = builder.firstName;
        lastName = builder.lastName;
        dateOfBirth = builder.dateOfBirth;
        salary = builder.salary;
        departmentId = builder.departmentId;
    }

    public Employee(String firstName, String lastName, Date datoOfBirth, Double salary, Long departmentId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = datoOfBirth;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDatoOfBirth(Date datoOfBirth) {
        this.dateOfBirth = datoOfBirth;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", salary=" + salary +
                ", departmentId=" + departmentId +
                '}';
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private Date dateOfBirth;
        private Double salary;
        private Long departmentId;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder dateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder salary(Double salary) {
            this.salary = salary;
            return this;
        }

        public Builder departmentId(Long departmentId) {
            this.departmentId = departmentId;
            return this;
        }

        public Employee build(){
            return new Employee(this);
        }
    }
}
