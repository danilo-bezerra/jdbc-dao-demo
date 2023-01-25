package application;

import java.util.List;

import model.dao.DAO;
import model.dao.DAOFactory;
import model.entities.Department;
import model.entities.Seller;

public class Program2 {

	public static void main(String[] args) {
		DAO<Department> depDAO = DAOFactory.createDepartmentDAO();
		
		System.out.println("=== TEST | Department - findAll ===");
		List<Department> allDeps = depDAO.findAll();
		allDeps.forEach(System.out::println);
		
		System.out.println();
		
		System.out.println("=== TEST | Department - findById ===");
		Department dep = depDAO.findById(2);
		System.out.println(dep);
		
		System.out.println();
		
		System.out.println("=== TEST | Department - insert ===");
		Department newDep = new Department(null, "Hunt");
		depDAO.insert(newDep);
		System.out.println("inserted");
		
		System.out.println();
		
		System.out.println("=== TEST | Department - update ===");
		Department changedDep = new Department(8, "Toys");
		depDAO.update(changedDep);
		System.out.println("updated");
		
		System.out.println();
		
		System.out.println("=== TEST | Department - deleted ===");
		depDAO.deleteById(10);
		System.out.println("deleted");
		
	}

}
