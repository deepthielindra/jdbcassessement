package com.jdbcAssessement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CustomerDaoImple implements CustomerDao {

	
	private Connection connection;

	public CustomerDaoImple() throws IOException {
		connection = ConnectionFactory.getConnection();
	}

	@Override
	public void addCustomer(Customer customer){
		try {
			PreparedStatement pstmt = connection
					.prepareStatement
					("insert into customer1(id,name,phone,email,dob,address,purchasecap) values (?,?,?,?,?,?,?)");
			pstmt.setInt(1,customer.getId());
			pstmt.setString(2,customer.getName());
			pstmt.setString(3, customer.getPhone());
			pstmt.setString(4, customer.getEmail());
			pstmt.setDate(5, (java.sql.Date) new Date(customer.getDob().getTime()));
			pstmt.setString(6, customer.getAddress());
			pstmt.setDouble(7, customer.getpurchasecap());
			pstmt.executeUpdate();

		} catch (SQLException e) {
		   e.printStackTrace();
		}

		
	}

	@Override
	public void delCustomer(int id) {
		Optional<Customer> customer=getCustomerById(id);
		try {
			PreparedStatement pstmt = connection
					.prepareStatement("delete from customer1 where id=?");
			pstmt.setInt(1,id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCustomer(int id, double purchasecap) {
		Optional<Customer> customer=getCustomerById(id);
		try {
			PreparedStatement pstmt = connection
					.prepareStatement("update customer1 set purchasecap=? where id=?");
			pstmt.setDouble(1, purchasecap);
			pstmt.setInt(2,id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Customer> getAllCustomer() {
		List<Customer> customers = new ArrayList<>();
		Customer customer = null;
		try {
			java.sql.Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from customer1");

			while (rs.next()) {
				

				customer = new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("phone"), rs.getString("email"),
						rs.getDate("dob"),rs.getString("address"), rs.getDouble("purchasecap"));

				customers.add(customer);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customers;
		
	}

	@Override
	public Optional<Customer> getCustomerById(int id) {
		Customer customer = null;
		try {
			PreparedStatement pstmt = connection.prepareStatement("select * from customer where id=?");
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				customer = new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("phone"), rs.getString("email"),
						rs.getDate("dob"),rs.getString("address"), rs.getDouble("purchasecap"));

				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(customer);
	}

	

	@Override
	public List<Customer> getSelectedCustomer(double purchase_capacity) {
		        List<Customer> customers = new ArrayList<>();
				Customer customer = null;
				try {
					PreparedStatement pstmt = connection.prepareStatement("select * from customer where purchase_capacity>?");
					pstmt.setDouble(1, purchase_capacity);

					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						customer = new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("phone"), rs.getString("email"),
								rs.getDate("dob"),rs.getString("address"), rs.getDouble("purchasecap"));
						customers.add(customer);
						
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
				return customers;
			}

			
	}
