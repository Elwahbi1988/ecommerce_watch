package com.example.microservice3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateSubCategoryDTO {
    private boolean updated;
    private boolean noContent;
    private boolean forBidden;
    private boolean notFound;

}
