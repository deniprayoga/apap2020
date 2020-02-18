package com.apap.tu03.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.apap.tu03.model.MovieModel;

@Service
public class InMemoryMovieService implements MovieService {
	private List<MovieModel> archiveMovie;
	
	public InMemoryMovieService() {
		archiveMovie = new ArrayList<>();
	}

	@Override
	public void addMovie(MovieModel movie) {
		archiveMovie.add(movie);
	}

	@Override
	public List<MovieModel> getMovieList() {		
		return archiveMovie;
	}

	@Override
	public MovieModel getMovieDetail(String id) {
		for(MovieModel movie : archiveMovie) {
			if (id.equals(movie.getId())) {
				return movie;
			}
		}
		return null;
	}

	@Override
	public String updateMovie(String id, Integer dur) {
		for(MovieModel movie : archiveMovie) {
			if (id.equals(movie.getId())) {
				movie.setDuration(dur);
				return "ID :" + id + ", Duration : " + dur ;
			}
		}
		return "ID not found";
	}

	@Override
	public String deleteMovie(String id) {
		for(int i=0; i<archiveMovie.size();i++) {
			if(id.equals(archiveMovie.get(i).getId())) {
				archiveMovie.remove(i);
				return "Movie with id " + id + " was deleted";
			}
		}
		return "ID not found";
	}
}
