package application;
/**
*
* The class {@code product} provides a simple product model.
*
**/
public class product {
	private String name;
	private double price;
	private int id;
	private int quantity;
	/**
	 * Class default constructor.
	 *
	 */
	public product() {
		this.name = "";
		this.price = 0.0;
		this.id = 0;
		this.quantity = 0;
	}
	/**
	 * Class constructor.
	 *
	 * @param name  the name.
	 * @param price  the price.
	 * @param id  the id.
	 * @param quantity  the quantity.
	 */
	public product(String name, double price, int id, int quantity) {
		this.name=name;
		this.price=price;
		this.id=id;
		this.quantity = quantity;
	}
	
	public String ToString(){
		return this.name+","+this.price+","+this.id+","+ this.quantity;
	}
	/**
	 * Gets the name.
	 *
	 * @return the content.
	 *
	 **/
	public String getName() {
		return this.name;
	}
	/**
	 * Gets the price.
	 *
	 * @return the content.
	 *
	 **/
	public double getPrice() {
		return this.price;
	}
	/**
	 * Gets the id.
	 *
	 * @return the content.
	 *
	 **/
	public int getId() {
		return this.id;
	}
	/**
	 * Gets the quantity.
	 *
	 * @return the content.
	 *
	 **/
	public int getQuantity() {
		return this.quantity;
	}
	/**
	 * Remove some quantity to the item.
	 * @param the quantity to remove.
	 * 
	 *
	 **/
	public void Buy(int i) {
		this.quantity -= i;
		return;
	}
	/**
	 * Add some quantity to the item.
	 *
	 * @param the quantity to add.
	 *
	 **/
	public void Refund(int i) {
		this.quantity += i;
		return;
	}
	/**
	 * Set the item's quantity.
	 *
	 * @param the quantity to set.
	 *
	 **/
	public int setQuantity(int i) {
		return this.quantity = i;
	}
}
