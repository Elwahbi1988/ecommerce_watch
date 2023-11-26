package com.example.microservice3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteProductDTO {

    private boolean deleted;
    private boolean noContent;
    private boolean badRequest;
    private boolean forbidden;
    private boolean notFound;

}
