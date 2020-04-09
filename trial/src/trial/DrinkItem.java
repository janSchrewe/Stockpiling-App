package trial;

public class DrinkItem extends Item{
	private int quantity;
	
	DrinkItem(String name, int millilitres){
		super(name);
		this.quantity = millilitres;
	}
	
	int getMillilitres() {
		return quantity;
	}
	
	public String toString() {
		return(Integer.toString(quantity) + "ml of " + getName());
	}
}