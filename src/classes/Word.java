package classes;

public class Word{
	private String text = "empty word";
	private int amount = -1;
	
	/* конструктор со словом (использовать для добавления в список нового слова) */
	public Word(String text) {
		this.text=text;
		this.amount=1;
	}
	
	/* конструктор со словом и количеством его повторений */
	public Word(String text, int amount) {
		this.text=text;
		this.amount=amount;
	}
	
	/* стандартный конструктор */
	public Word() {
		
	}
	
	/* стандартные геттеры и сеттеры */
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
