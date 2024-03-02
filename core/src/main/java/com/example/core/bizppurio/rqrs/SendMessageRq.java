package com.example.core.bizppurio.rqrs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRq {

    /*text(20)*//*비즈뿌리오 계정*/
    private String account;
    /*text(20)*//*고객사에서 부여한 키*/
    private String refkey;
    /*text(3)*//*메시지 타입*/
    private String type;
    /*text(20)*//*발신 번호*/
    private String from;
    /*text(20)*//*수신 번호*/
    private String to;
    /*text(20)*//*메시지 데이터*/
    private Object content;

//    /*text(20)*//*정산용 부서 코드*/
//    private String userinfo;
//
//    /*text(10)*//*발송 시간*/
//    private String sendtime;
//    /*text(20)*//*국가 코드*/
//    private String country;
//    /*text(20)*//*대체 전송 메시지 유형*/
//    private String resend;
//    /*text(20)*//*대체 전송 메시지 데이터*/
//    private String recontent;
//    /*text(20)*//*특부가 사업자 식별코드*/
//    private String resellercode;
//
//    public SendMessageRq(String account, String refkey, String type, String from, String to, Object content,
//                         String userinfo) {
//        this.account = account;
//        this.refkey = refkey;
//        this.type = type;
//        this.from = from;
//        this.to = to;
//        this.content = content;
//        this.userinfo = userinfo;
//    }
}
