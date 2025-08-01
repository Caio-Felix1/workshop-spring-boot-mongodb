package com.nelioalves.workshopmongo.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelioalves.workshopmongo.domain.Post;
import com.nelioalves.workshopmongo.repository.PostRepository;
import com.nelioalves.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	public Post findById(String id) {
		Optional<Post> post = postRepository.findById(id);
		return post.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public List<Post> findByTitle(String text) {
		return postRepository.searchTitle(text);
	}
	
	public List<Post> fullSearch(String text, Instant minDate, Instant maxDate) {
		// calcula 23 horas, 59 minutos, 59 segundos e 999 milissegundos. Assim, obtém-se o fim do dia:
		// 23 * 60 * 60 * 1000 + 59 * 60 * 1000 + 59 * 1000 + 999 = 86399999
		maxDate = maxDate.plusMillis(86_399_999);
		return postRepository.fullSearch(text, minDate, maxDate);
	}
}
