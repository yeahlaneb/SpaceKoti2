package com.tb.javaecommerce.web.exception;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class FieldsAndReason {
    String fieldName;
    String reason;
}
