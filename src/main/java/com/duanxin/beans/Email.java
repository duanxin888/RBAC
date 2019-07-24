package com.duanxin.beans;

import lombok.*;

import java.util.Set;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName Email
 * @Description Email参数类
 * @date 2019/7/20 10:55
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    private String subject;

    private String message;

    private Set<String> receives;
}
