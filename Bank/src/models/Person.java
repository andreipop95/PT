package models;

import java.util.Observable;
import java.util.Observer;

public class Person implements Observer, java.io.Serializable{
	
	private String name;
	private int personId;

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + personId;
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
		Person other = (Person) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (personId != other.personId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", personId=" + personId + "]";
	}

	@Override
	public void update(Observable acc, Object sum) { // called when a change occurs into an account
		System.out.println("Notified person: " + name + ", Acccount modified: " + acc.toString() + "sum: " + sum);
	}	

}
