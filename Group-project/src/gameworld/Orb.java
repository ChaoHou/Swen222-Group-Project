package gameworld;

public class Orb implements Collectable{
	public static final int BLUE=0;
	public static final int GREEN=1;
	public static final int RED=2;
	
	//colour of the orb.
	private int colour;
	
	public Orb(int colour){
		this.colour=colour;
	}
	
	public int getColor(){
		return this.colour;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + colour;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orb other = (Orb) obj;
		if (colour != other.colour)
			return false;
		return true;
	}
}
