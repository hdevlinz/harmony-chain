package com.tth.rating.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.request.rating.RatingRequestCreate;
import com.tth.commonlibrary.dto.request.rating.RatingRequestUpdate;
import com.tth.commonlibrary.dto.response.rating.RatingResponse;

public interface RatingService {

    PageResponse<RatingResponse> findRatingsBySupplierId(String supplierId, int page, int size);

    RatingResponse createRating(RatingRequestCreate request);

    RatingResponse updateRating(String ratingId, RatingRequestUpdate request);

    void deleteRating(String ratingId);

}
