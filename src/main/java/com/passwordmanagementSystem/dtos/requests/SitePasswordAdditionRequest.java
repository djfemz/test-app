package com.passwordmanagementSystem.dtos.requests;

import lombok.Data;

@Data
public class SitePasswordAdditionRequest {
    private String yourPassword;
    private String name;
    private String url;
    private String siteUsername;
    private String sitePassword;
    private String email;
}
