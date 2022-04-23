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
    private String websiteUserName;
    @Id @UniqueElements
    private String url;
    private String websitePassword;
//    private String email;

}
