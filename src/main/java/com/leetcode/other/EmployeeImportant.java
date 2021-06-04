package com.leetcode.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeImportant {

    static class Employee {
        public int id;
        public int importance;
        public List<Integer> subordinates;
    }

    /**
     * 690. 员工的重要性
     *
     * @author yue.liu
     * @since 2021/6/4 12:01
     **/
    public int getImportance(List<Employee> employees, int id) {
        Map<Integer, Employee> map = new HashMap<>();

        for (Employee employee : employees) {
            map.put(employee.id, employee);
        }
        return dfs(id, map);
    }

    private int dfs(int id, Map<Integer, Employee> map) {
        Employee employee = map.get(id);
        int sum = employee.importance;
        if (employee.subordinates != null) {
            for (Integer sub : employee.subordinates) {
                sum += dfs(sub, map);
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        Employee employee2 = new Employee();
        employee2.id = 2;
        employee2.importance = 3;

        Employee employee3 = new Employee();
        employee3.id = 3;
        employee3.importance = 3;

        Employee employee1 = new Employee();
        employee1.id = 1;
        employee1.importance = 5;

        List<Integer> subordinates = new ArrayList<>();
        subordinates.add(2);
        subordinates.add(3);
        employee1.subordinates = subordinates;

        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);

        EmployeeImportant employeeImportant = new EmployeeImportant();
        int importance = employeeImportant.getImportance(employees, 1);
        System.out.println(importance);
    }

}
