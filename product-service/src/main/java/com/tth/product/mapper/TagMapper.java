package com.tth.product.mapper;

import com.tth.commonlibrary.dto.response.product.tag.TagResponse;
import com.tth.product.entity.Tag;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TagMapper {

    @Named("toTagResponse")
    TagResponse toTagResponse(Tag tag);

}
