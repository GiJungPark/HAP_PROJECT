package me.gijung.HAP.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Mail {
    private String toMail;
    private String title;
    private String content;
}
