package application;

/**
 * public class that has the attributes for the watch
 */
public class watch {
	private String brand;
	private String model;
	private String movement;
	private Double diameter;
	private String category;
	private Double price;
	private String image;
	private String description;
	private String possessed;
	
	public watch() {
		this.brand = "";
		this.model = "";
		this.movement = "";
		this.diameter = 0.0;
		this.category = "";
		this.price = 0.0;
		this.image = "";
		this.description = "";
		this.possessed = "";
	}
	
	public watch(String brand, String model, String movement, Double diameter, String category, Double price, String image, String description, String possessed) {
		this.brand = brand;
		this.model = model;
		this.movement = movement;
		this.diameter = diameter;
		this.category = category;
		this.price = price;
		this.image = image;
		this.description = description;
		this.possessed = possessed;
	}
	/**
	 * returns the attributes into a string
	 * @return
	 */
	public String ToString(){
		return brand+", "+model+", "+movement+", "+diameter+", "+category+", "+price;
	}
	/**
	 * returns the brand
	 * @return
	 */
	public String getBrand() {
		return this.brand;
	}
	/**
	 * returns the model
	 * @return
	 */
	public String getModel() {
		return this.model;
	}
	/**
	 * returns the movement
	 * @return
	 */
	public String getMovement() {
		return this.movement;
	}
	/**
	 * returns the diameter
	 */
	public Double getDiameter() {
		return this.diameter;
	}
	/**
	 * returns the category
	 * @return
	 */
	public String getCategory() {
		return this.category;
	}
	/**
	 * returns the price
	 */
	public Double getPrice() {
		return this.price;
	}
	/**
	 * returns the image
	 * @return
	 */
	public String getImage() {
		return this.image;
	}
	/**
	 * returns the description
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * returns the name of the possessor
	 * @return
	 */
	public String getPossessor() {
		return this.possessed;
	}
}