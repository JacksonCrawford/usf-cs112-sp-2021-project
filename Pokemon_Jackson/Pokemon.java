//package cs112lectures;

public class Pokemon implements Comparable<Pokemon> {
  // Can only be accessed by the inherited class
  private String name;
  private String color;
  private boolean hasTrainer;
  public String moveDirection;
  public int age;

  // Constructor
  public Pokemon(String nParam, String cParam) {
    this.name = nParam;
    this.color = cParam;
    this.hasTrainer = false;
    this.age = 0;
  }

  public Pokemon(String nParam, String cParam, boolean hParam, int aParam) {
	  this.name = nParam;
	  this.color = cParam;
	  this.hasTrainer = hParam;
	  this.age = aParam;
  }
  
  // Default constructor
  public Pokemon() {
	  this.name = "";
	  this.color = "";
	  this.hasTrainer = false;
  }
  
  // Accessor
  public String getName() {
    return this.name;
  }
  
  public String getColor() {
	  return this.color;
  }
  
  public boolean hasTrainer() {
	  return this.hasTrainer;
  }
  
  public int getAge() {
	  return this.age;
  }
  
  // Mutator
  public void setName(String nParam) {
    this.name = nParam;
  }
  
  public void setColor(String cParam) {
	  this.color = cParam;
  }
  
  public void setHasTrainer(boolean hParam) {
	  this.hasTrainer = hParam;
  }
  
  // Virtual method
  public void move(String direction) {
	  //System.out.println("Moving " + direction);
	  this.moveDirection = direction;
  }
  

  // Method
  public String toString() {
    return "I am a Pokemon: " + this.name + " : " + this.color + " : " + this.hasTrainer();
  }
  
  // Equals function
  public boolean equals(Pokemon obj) {
	  if(obj.hasTrainer()) {
		  Pokemon other = (Pokemon) obj;
		  if(this.name == other.getName() &&
				  this.color == other.getColor()) {
			  return true;
		  }
	  }
	  return false;
  }
  
  // Returns age difference
  @Override
  public int compareTo(Pokemon obj) {
		return this.age - obj.getAge();
  }
  
  
}


