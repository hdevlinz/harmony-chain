package com.tth.rating.service.impl;

import com.tth.rating.repository.RatingRepository;
import com.tth.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

//    @Override
//    public List<Rating> findAllWithFilter(Map<String, String> params) {
//        return this.ratingRepository.findAllWithFilter(params);
//    }

}
