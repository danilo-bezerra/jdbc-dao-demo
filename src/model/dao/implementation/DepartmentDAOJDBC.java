package model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DAO;
import model.entities.Department;

public class DepartmentDAOJDBC implements DAO<Department> {
	private Connection conn;

	public DepartmentDAOJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		String sql = "INSERT INTO department (name) VALUES (?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, obj.getName());
			
			int rowsAffected = stmt.executeUpdate();
			
			if (rowsAffected < 1) {
				throw new SQLException();
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	@Override
	public void update(Department obj) {
		String sql = "UPDATE department "
				+ "SET name = ? "
				+ "WHERE id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, obj.getName());
			stmt.setInt(2, obj.getId());
			
			int rowsAffected = stmt.executeUpdate();
			
			if (rowsAffected < 1) {
				throw new SQLException();
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public void deleteById(Integer id) {
		String sql = "DELETE FROM department WHERE id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setInt(1, id);
			
			stmt.execute();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public Department findById(Integer id) {
		String sql = "SELECT * FROM department WHERE id = ?";
		ResultSet rs = null;
		try (PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			Department dep = null;
			if (rs.next()) {
				dep = instantianteDepartment(rs);
				return dep;
			}
			
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
		}
	}


	@Override
	public List<Department> findAll() {
		String sql = "SELECT * FROM department ORDER BY name";
		try (Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery(sql)){
			List<Department> deps = new ArrayList<>();
			while (rs.next()) {
				deps.add(instantianteDepartment(rs));
			}
			return deps;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	private Department instantianteDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("id"));
		dep.setName(rs.getString("name"));
		return dep;
	}

}
