package com.demo.hospital.base;

import com.demo.hospital.user.service.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Locale;

@Service
public class ResponseHandler extends UserAuthentication {

    @Autowired
    private MessageSource messageSource;


    public <T extends Object> ResponseEntity<?> okResponse(T t,HttpStatus statusCode, String message){
        BaseResponse baseResponse=new BaseResponse();

        baseResponse.status_code= statusCode.value();
        baseResponse.message=getMessage(message);

        baseResponse.t=t;

        return new ResponseEntity<>(baseResponse,statusCode);
    }

    public <T extends Object> ResponseEntity<?> okResponse(HttpStatus httpStatus, String message){
        BaseResponse baseResponse=new BaseResponse();

        baseResponse.status_code= httpStatus.value();
        baseResponse.message=getMessage(message);

        return new ResponseEntity<>(baseResponse,httpStatus);
    }

    public String getMessage(String messageId) {
        String message="";

        if(messageId==null || messageId.trim().length()==0){
            return message;
        }
        try {
            message = messageSource.getMessage(messageId,null,Locale.getDefault());
        }
        catch (Exception e){
            return messageId;
        }
        return message;
    }

}
