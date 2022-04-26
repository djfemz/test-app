package com.passwordmanagementSystem.model;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WebsitePassword {
     @UniqueElements
    private String websiteUserName;

    private String url;
    private String websitePassword;


}
