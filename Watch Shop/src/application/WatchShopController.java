package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * controls the functions to do the login, to do the new user and also to do the new watch
 */
public class WatchShopController {
	
	static String account_name;
	
	static boolean account_admin;
	
	public static Connection conn;
	
	static List<watch> watchlist = new ArrayList<>();
	
	/**
	 * this function is called in WatchShopView to do the login. It does a query to extract form the database the information of the user
	 * @param txt_address
	 * @param txt_password
	 * @return
	 * @throws SQLException
	 */
	static boolean login(String txt_address, String txt_password) throws SQLException 
    {
    	
    	try 
    	{ 
    		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/watchshop", "root", "");
    		PreparedStatement ps = conn.prepareStatement("SELECT * FROM user");
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) 
        	{	
    			String name = rs.getString("name");
    			Boolean admin = rs.getBoolean("admin");
        		String mail = rs.getString("mail");
        	    String password = rs.getString("password");
        	    if (txt_address.equals(mail)) 
            	{
            		if(txt_password.equals(password)) 
            		{
            			account_name = name;
            			account_admin = admin;
            			
            			return true;
            		}
            	}
        	}
    	}
    	catch (SQLException ex) 
    	{
    		ex.printStackTrace();
    	}
		return false;
    }
	/**
	 * this function is called in the newuserpage to create a new user. it does a query that insert in the database the new user
	 * @param newname
	 * @param newsurname
	 * @param newaddress
	 * @param newpassword
	 * @return
	 * @throws SQLException
	 */
	static boolean newuser(String newname, String newsurname, String newaddress, String newpassword) throws SQLException 
	{
		if (login(newaddress, newpassword)) 
		{
			return false;
		}
		System.out.println(newname);
		PreparedStatement ps = conn.prepareStatement("INSERT INTO user (name, surname, mail, admin, password, balance) VALUES(?, ?, ?, ?, ?, ?)");
		ps.setString(1, newname);
		ps.setString(2, newsurname);
		ps.setString(3, newaddress);
		ps.setInt(4, 0);
		ps.setString(5, newpassword);
		ps.setInt(6, 0);
		try 
		{
			ps.executeUpdate();
		}
		catch(java.sql.SQLIntegrityConstraintViolationException ex) {}
		return true;
	}
	/**
	 * this function is called in addnewwatchpage to add a new watch in the shop by the admin. it doeas a query to insert in the database the new watch
	 * @param newbrand
	 * @param newmodel
	 * @param newmovement
	 * @param newdiameter
	 * @param newcategory
	 * @param newprice
	 * @param newimage
	 * @param newdescription
	 * @return
	 * @throws SQLException
	 */
	static boolean newwatch(String newbrand, String newmodel, String newmovement, String newdiameter, String newcategory, String newprice, String newimage, String newdescription) throws SQLException 
	{
		PreparedStatement ps = conn.prepareStatement("INSERT INTO watch (brand, model, movement, diameter, category, price, image, description, possessed) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
		ps.setString(1, newbrand);
		ps.setString(2, newmodel);
		ps.setString(3, newmovement);
		ps.setDouble(4, Double.parseDouble(newdiameter));
		ps.setString(5, newcategory);
		ps.setDouble(6, Double.parseDouble(newprice));
		ps.setString(7, newimage);
		ps.setString(8, newdescription);
		ps.setString(9, " ");
		try 
		{
			ps.executeUpdate();
		}
		catch(java.sql.SQLIntegrityConstraintViolationException ex) {}
		return true;
	}
	/**
	 * this function does a query to select all the watches and put them in a list of watch
	 */
	static List<watch> setwatch() throws SQLException 
	{
		watchlist.clear();
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/watchshop", "root", ""))
		{
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM watch");
    		ResultSet rs = ps.executeQuery();
    		
    		while(rs.next())
    		{
    			String brand = rs.getString("brand");
    			String model = rs.getString("model");
    			String movement = rs.getString("movement");
    			Double diameter = rs.getDouble("diameter");
    			String category = rs.getString("category");
    			Double price = rs.getDouble("price");
    			String image = rs.getString("image");
    			String description = rs.getString("description");
    			String possessed = rs.getString("possessed");
    			
    			watch newatch = new watch(brand, model, movement, diameter, category, price, image, description, possessed);
    			watchlist.add(newatch);
    		}
		}
		catch (SQLException ex) 
    	{
    		ex.printStackTrace();
    	}
		return watchlist;
	}
	
}