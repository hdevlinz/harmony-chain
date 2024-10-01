package com.tth.rating.service.impl;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.request.rating.RatingRequestCreate;
import com.tth.commonlibrary.dto.request.rating.RatingRequestUpdate;
import com.tth.commonlibrary.dto.response.profile.supplier.SupplierResponse;
import com.tth.commonlibrary.dto.response.rating.RatingResponse;
import com.tth.commonlibrary.enums.ErrorCode;
import com.tth.commonlibrary.exception.AppException;
import com.tth.rating.entity.Rating;
import com.tth.rating.mapper.RatingMapper;
import com.tth.rating.repository.RatingRepository;
import com.tth.rating.repository.httpclient.UserProfileClient;
import com.tth.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final UserProfileClient profileClient;

    @Override
    public PageResponse<RatingResponse> findRatingsBySupplierId(String supplierId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<RatingResponse> results = this.ratingRepository.findAllBySupplierId(supplierId, pageable)
                .map(this.ratingMapper::toRatingResponse);

        return PageResponse.of(results);
    }

    @Override
    public RatingResponse createRating(RatingRequestCreate request) {
        SupplierResponse supplier = this.profileClient.getSupplier(request.getSupplierId()).getResult();
        if (supplier == null) {
            throw new AppException(ErrorCode.SUPPLIER_NOT_FOUND);
        }

        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        Rating rating = this.ratingMapper.toRating(request);
        rating.setUserId(user.getName());
        rating.setSupplierId(request.getSupplierId());
        this.ratingRepository.save(rating);

        return this.ratingMapper.toRatingResponse(rating);
    }

    @Override
    public RatingResponse updateRating(String ratingId, RatingRequestUpdate request) {
        Rating rating = this.ratingRepository.findById(ratingId)
                .orElseThrow(() -> new AppException(ErrorCode.RATING_NOT_FOUND));

        rating = this.ratingMapper.updateRating(rating, request);
        this.ratingRepository.save(rating);

        return this.ratingMapper.toRatingResponse(rating);
    }

    @Override
    public void deleteRating(String ratingId) {
        this.ratingRepository.deleteById(ratingId);
    }
}
