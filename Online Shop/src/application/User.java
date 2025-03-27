package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * The class {@code User} provides a simple user model.
 *
 **/

public final class User implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final String firstName;
	private final String lastName;
	private final String address;
	private final transient String password;
	private List<product> my_orders = new ArrayList<>();

	/**
	 * Class constructor.
	 *
	 * @param f  the first name.
	 * @param l  the last name.
	 * @param a  the address.
	 * @param p  the password.
	 */
	public User(final String f, final String l, final String a, final String p)
	{
		this.firstName = f;
		this.lastName  = l;
		this.address   = a;
		this.password  = p;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the content.
	 *
	 **/
	public String getFirstName()
	{
		return this.firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the content.
	 *
	 **/
	public String getLastName()
	{
		return this.lastName;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address.
	 *
	 **/
	public String getAddress()
	{
		return this.address;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password.
	 *
	 **/
	public String getPassword()
	{
		return this.password;
	}

	public List<product> getMyOrder()
	{
		return this.my_orders;
	}



	public void newMyordersList(List<product> p)
	{
		if(this.my_orders.size() == 0) 
		{
			this.my_orders = p;
		}
		else 
		{
			for(int j = 0; j<p.size();j++) 
			{
				this.my_orders.add(p.get(j));
			}
			organize();
		}
		
	}
	public void organize() 
	{
		for(int i = 0; i<this.my_orders.size();i++) 
		{
			for(int j = 0; j<this.my_orders.size();j++) 
			{
				if(this.my_orders.get(i).getId() == (this.my_orders.get(j).getId()) && i != j) 
				{
					this.my_orders.get(i).Refund(this.my_orders.get(j).getQuantity());
					this.my_orders.remove(j);
				}
			}
			
		}
	}
	
}