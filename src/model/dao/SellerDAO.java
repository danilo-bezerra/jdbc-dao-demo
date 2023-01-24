package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDAO extends DAO<Seller>{
	List<Seller> findByDepartament(Department dep);
}
