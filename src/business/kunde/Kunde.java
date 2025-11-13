package business.kunde;

public class Kunde {

	private int hausnummer;
	private String vorname;
	private String nachname;
	private String telefonnummer;
	private String email;

	public Kunde(Integer kundennummer, String vorname2, String nachname2, String telefonnummer2, String email2,
			Integer plannummer) {
		// Wir benutzen plannummer als hausnummer-Feld, da dies offenbar die Hausnummer darstellt.
		if (plannummer != null) {
			this.hausnummer = plannummer;
		} else {
			this.hausnummer = 0;
		}
		this.vorname = vorname2;
		this.nachname = nachname2;
		this.telefonnummer = telefonnummer2;
		this.email = email2;
	}

	public int getHausnummer() {
		return hausnummer;
	}

	public void setHausnummer(int hausnummer) {
		this.hausnummer = hausnummer;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getTelefonnummer() {
		return telefonnummer;
	}

	public void setTelefonnummer(String telefonnummer) {
		this.telefonnummer = telefonnummer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
