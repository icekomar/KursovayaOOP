package models;

import javax.persistence.*;

@MappedSuperclass
abstract  class Human {
    protected String Name;
    protected String Surname;
    protected int Age;
    protected int Salary;


    public int getAge() {
        return Age;
    }

    public int getSalary() {
        return Salary;
    }

    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setAge(int age) {
        Age = age;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

}
