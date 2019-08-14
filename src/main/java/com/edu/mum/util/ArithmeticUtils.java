package com.edu.mum.util;

import com.edu.mum.domain.Post;
import com.edu.mum.domain.Review;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ArithmeticUtils {


    public static Double getAvgRating(Collection<Review> reviews){
        double reviewSum =reviews.stream().collect(Collectors.summingDouble(Review::getRating));
        int ratingCount =reviews.size() >0 ? reviews.size() :1;
        double avgReview = reviewSum/ratingCount;
        DecimalFormat formatter = new DecimalFormat("#0.00");
        return Double.parseDouble(formatter.format(avgReview));

    }

    public static Map<Long, Double> getAvgRatingMap(Collection<Post> posts){
        Map<Long,Double> avgRatingMap = new HashMap<>();
        double avgRatig =0.00;
        int ratingCount;
        for (Post p: posts) {
            for (Review r: p.getReviews()) {
                avgRatig += r.getRating();
            }
            ratingCount = p.getReviews().size();
            ratingCount = ratingCount>0? ratingCount :1;
            avgRatingMap.put(p.getId(),avgRatig/ratingCount);
            avgRatig =0.00;
        }
        return avgRatingMap;
    }


}
