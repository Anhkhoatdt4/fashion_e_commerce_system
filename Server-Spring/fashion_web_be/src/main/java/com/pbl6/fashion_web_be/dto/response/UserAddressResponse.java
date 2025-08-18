package com.pbl6.fashion_web_be.dto.response;

import lombok.*;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressResponse {
    private UUID addressId;
    private UUID userId;
    private String recipientName;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String stateProvince;
    private String postalCode;
    private String country;
    private Boolean isDefault;
    private String addressType;
}
