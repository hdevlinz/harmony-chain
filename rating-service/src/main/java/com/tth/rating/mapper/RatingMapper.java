package com.tth.rating.mapper;

import com.tth.commonlibrary.dto.request.rating.RatingRequestCreate;
import com.tth.commonlibrary.dto.request.rating.RatingRequestUpdate;
import com.tth.commonlibrary.dto.response.rating.RatingResponse;
import com.tth.rating.entity.Rating;
import com.tth.rating.mapper.helper.MappingHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {MappingHelper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RatingMapper {

    @Named("toRating")
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "supplierId", ignore = true)
    Rating toRating(RatingRequestCreate request);

    @Named("updateRating")
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "supplierId", ignore = true)
    Rating updateRating(@MappingTarget Rating rating, RatingRequestUpdate request);

    @Named("toRatingResponse")
    @Mapping(target = "user", source = "userId", qualifiedByName = "getUserResponseByUserId")
    @Mapping(target = "supplier", source = "supplierId", qualifiedByName = "getSupplierResponseBySupplierId")
    RatingResponse toRatingResponse(Rating rating);

}
