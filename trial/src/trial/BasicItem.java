package trial;

public class BasicItem extends Item{
	private int quantity;
	
	BasicItem(String name, int quantity){
		super(name);
		this.quantity = quantity;
	}
	
	int getQuantity() {
		return quantity;
	}
	
	public String toString() {
		return(Integer.toString(quantity) + "x " + getName());
	}
}
