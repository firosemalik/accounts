package com.anz.accounts.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ApiError {
    private String errorId;
    private String message;
    private String detail;
}
