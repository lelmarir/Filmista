package org.hamisto.filmista;



import javafx.scene.image.Image;

public class TopElement {

	

	private String id;
	private String nome;
	private String overview;
	private String shortOverview;
	private String genre;
	private String runtime;
	private String status;
	private String rating;
	private Image poster;
	private String idImdb;
	
	


	public TopElement() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public  String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getShortOverview() {
		return shortOverview;
	}
	public void setShortOverview(String shortOverview) {
		this.shortOverview = shortOverview;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public Image getPoster() {
		return poster;
	}
	public void setPoster(Image poster) {
		this.poster = poster;
	}
	
	
	public String getIdImdb() {
		return idImdb;
	}



	public void setIdImdb(String idImdb) {
		this.idImdb = idImdb;
	}

}
