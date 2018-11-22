package com.ah.workshopmongo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ah.workshopmongo.domain.Post;

@Repository
public interface PostRepository extends MongoRepository<Post,String> {

	@Query("{ 'title': { $regex: ?0, $options: 'i' } }")
	List<Post> searchTitle(String text); // ?0 == primeiro parametro
	
	List<Post> findByTitleContainingIgnoreCase(String text);
	
	/*
	 * Buscar posts contendo um dado string em qualquer lugar (no título, corpo ou comentários)
	 * e em um dado intervalo de datas
	 */
	
	//@Query("{ $and: [ { 'date': { $gte: ?1 } }, { 'date': { $lte: ?2 } }, { $or: [ { 'title': { $regex: ?0, $options: 'i' } }, { 'body': { $regex: ?0, $options: 'i' } }, { 'commets.text': { $regex: ?0, $options: 'i' } } ] } ] }")
	@Query("{ $and: [ { date: {$gte: ?1} }, { date: { $lte: ?2} } , { $or: [ { 'title': { $regex: ?0, $options: 'i' } }, { 'body': { $regex: ?0, $options: 'i' } }, { 'comments.text': { $regex: ?0, $options: 'i' } } ] } ] }")
	List<Post> fullSearch(String text, Date minDate, Date maxDate );
}
