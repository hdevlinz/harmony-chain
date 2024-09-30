package com.tth.rating.mapper;

import com.tth.commonlibrary.dto.request.rating.RatingRequestCreate;
import com.tth.commonlibrary.dto.request.rating.RatingRequestUpdate;
import com.tth.commonlibrary.dto.response.rating.RatingResponse;
import com.tth.rating.entity.Rating;
import com.tth.rating.mapper.helper.RatingMappingHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {RatingMappingHelper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RatingMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "supplierId", ignore = true)
    Rating toRating(RatingRequestCreate request);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "supplierId", ignore = true)
    Rating updateRating(@MappingTarget Rating rating, RatingRequestUpdate request);

    @Mapping(target = "user", source = "userId", qualifiedByName = "getUserResponse")
    @Mapping(target = "supplier", source = "supplierId", qualifiedByName = "getSupplierResponse")
    RatingResponse toRatingResponse(Rating rating);

}
