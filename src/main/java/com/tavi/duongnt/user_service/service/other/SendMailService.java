package com.tavi.duongnt.user_service.service.other;

public interface SendMailService {

    boolean sendMail(String userMail, String header, String content);

    boolean sendHtmlMail(String userMail, String header, String content);
}
