package com.example.microservice3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductDTO {
    private boolean updated;
    private boolean noContent;
    private boolean forbidden;
    private boolean notFound;
    private boolean badRequest;
}
