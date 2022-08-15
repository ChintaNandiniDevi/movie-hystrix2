package com.cap.moviecatalogservice.resources;

import com.cap.moviecatalogservice.models.CatalogItem;
import com.cap.moviecatalogservice.models.Movie;
import com.cap.moviecatalogservice.models.Rating;
import com.cap.moviecatalogservice.models.UserRating;
import com.cap.moviecatalogservice.services.MovieInfo;
import com.cap.moviecatalogservice.services.UserRatingInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;


    @RequestMapping("/{userId}")
    //@HystrixCommand(fallbackMethod = "getFallbackCatalog")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        UserRating ratings= userRatingInfo.getUserRating(userId);
        return ratings.getUserRatings().stream().map(rating -> movieInfo.getCatalogItem(rating))
                .collect(Collectors.toList());
    }


}

/*
            Movie movie= webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/"+rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
 */