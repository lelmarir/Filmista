package org.hamisto.filmista;

import java.util.ArrayList;
import java.util.List;

public class Stagione {
	
	ArrayList<SingleEpisode> episodiStagione;
	
	public Stagione(){
		
		super();
	}
	
	public Stagione(Serie serie, String numero){
		
		super();
		this.numero = numero;

		CreateEpisodesDetail episodeDetails = new CreateEpisodesDetail(serie, numero);
		this.episodiStagione = episodeDetails.getEpisodiStagione();
		
	}
	
	
	
	public ArrayList<SingleEpisode> getEpisodiStagione() {
		return episodiStagione;
	}

	public void setEpisodiStagione(ArrayList<SingleEpisode> episodiStagione) {
		this.episodiStagione = episodiStagione;
	}
	String numero;
	public Stagione(String numero) {
		
		this.numero = numero;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	@Override
	public String toString() {
		return this.getNumero();
	}
}
