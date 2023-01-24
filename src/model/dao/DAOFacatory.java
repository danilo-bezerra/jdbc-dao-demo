package model.dao;

import model.dao.implementation.DepartmentDAOJDBC;
import model.dao.implementation.SellerDAOJDBC;
import model.entities.Department;
import model.entities.Seller;

public class DAOFacatory {
	public static DAO<Seller> createSellerDAO() {
		return new SellerDAOJDBC();
	}
	
	public static DAO<Department> createDepartmentDAO() {
		return new DepartmentDAOJDBC();
	}
}
