package model.dao;

import db.DB;
import model.dao.implementation.DepartmentDAOJDBC;
import model.dao.implementation.SellerDAOJDBC;
import model.entities.Department;
import model.entities.Seller;

public class DAOFacatory {
	public static DAO<Seller> createSellerDAO() {
		return new SellerDAOJDBC(DB.getConnection());
	}
	
	public static DAO<Department> createDepartmentDAO() {
		return new DepartmentDAOJDBC();
	}
}
