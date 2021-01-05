package com.pl.restApi.mapper;

import com.pl.restApi.dto.TagDto;
import com.pl.restApi.model.Tag;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper( TagMapper.class );

    TagDto toTagDto(Tag tag);

    @InheritConfiguration
    Tag toTag(TagDto tagDto);
}
