package com.ah.workshopmongo.resources;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ah.workshopmongo.domain.Post;
import com.ah.workshopmongo.resources.util.URL;
import com.ah.workshopmongo.services.PostService;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

	@Autowired
	private PostService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Post> findById(@PathVariable String id) {
		Post obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	// ex: http://localhost:8080/posts/titlesearch?text=bom%20dia
	
	@RequestMapping(value="/titlesearch", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> findByTitle(@RequestParam(value="text",defaultValue="") String text) {
		text = URL.decodeParam(text);
		List<Post> list = service.findByTitle(text);
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value="/fullsearch", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> fullSearch
	        ( @RequestParam(value="text",defaultValue="") String text
			, @RequestParam(value="minDate",defaultValue="") String minDate
			, @RequestParam(value="maxDate",defaultValue="") String maxDate
			) {
		text = URL.decodeParam(text);
		Date min = URL.convertDate(minDate, new Date(0L)); // 0L == 01/01/1970
		Date max = URL.convertDate(maxDate, new Date());   // atual date
		List<Post> list = service.fullSearch(text,min,max);
		return ResponseEntity.ok().body(list);
	}
	
}
