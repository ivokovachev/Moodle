package users;

public class Address {

	private String country; // optional
	private String city; // optional

	private Address() {
	}

	public static Address getAddress(String country, String city) {
		Address a = new Address();

		if (country != null) {
			a.setCountry(country);
		}

		if (city != null) {
			a.setCity(city);
		}

		return a;
	}

	@Override
	public String toString() {
		return this.city + (this.country.length() != 0 && this.city.length() != 0 ? ", " : "") + this.country;
	}

	void setCountry(String country) {
		this.country = country;
	}

	void setCity(String city) {
		this.city = city;
	}

}
