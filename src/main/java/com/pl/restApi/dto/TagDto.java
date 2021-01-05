package com.pl.restApi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TagDto implements Serializable {

    private String id;
    private String tagName;
}
