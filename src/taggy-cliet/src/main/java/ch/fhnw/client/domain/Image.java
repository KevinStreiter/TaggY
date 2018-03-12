package ch.fhnw.client.domain;

public class Image {
	
	private String name;
	private String data;
	
	public Image(){
	}
	
	public Image(String name, String data){
		this.name = name;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return data;
	}

	public void setDate(String date) {
		this.data = date;
	}

	@Override
	public String toString() {
		return "Image [name=" + name + ", date=" + data + "]";
	}
	

}
