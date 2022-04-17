package com.passwordmanagementSystem.dtos.requests;

import lombok.Data;

@Data
public class SitePasswordAdditionRequest {
    private String uniquePassword;
    private String name;
    private String url;
    private String siteUsername;
    private String sitePassword;
    private String email;
}
