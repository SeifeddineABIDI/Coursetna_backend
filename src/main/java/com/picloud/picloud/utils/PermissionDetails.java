package com.picloud.picloud.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionDetails {

    private String type;
    private String role;
    private String emailAddress;
}
