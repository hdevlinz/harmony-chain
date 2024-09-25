package com.tth.product.mapper;

import com.tth.product.dto.response.tag.TagResponse;
import com.tth.product.entity.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagResponse toTagResponse(Tag tag);

}
