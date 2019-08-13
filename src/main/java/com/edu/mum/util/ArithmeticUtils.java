package com.edu.mum.util;

import com.edu.mum.domain.Review;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.stream.Collectors;

public class ArithmeticUtils {


    public static String getAvgRating(Collection<Review> reviews){
        double reviewSum =reviews.stream().collect(Collectors.summingDouble(Review::getRating));
        int ratingCount =reviews.size();
        double avgReview = reviewSum/ratingCount;
        DecimalFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(avgReview);

    }
}
