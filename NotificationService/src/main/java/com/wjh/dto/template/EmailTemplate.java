package com.wjh.dto.template;

import lombok.Data;

@Data
public class EmailTemplate {
    protected String subject;
    protected String message;
}
