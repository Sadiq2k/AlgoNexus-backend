package com.authService.Model.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ForgotPasswordResponse {


    private String token;
    private String errorMessage;

    public ForgotPasswordResponse(String token, String errorMessage) {
        this.token = token;
        this.errorMessage = errorMessage;
    }

    public static ForgotPasswordResponseBuilder builder() {
        return new ForgotPasswordResponseBuilder();
    }

    public static class ForgotPasswordResponseBuilder {
        private String token;
        private String errorMessage;

        public ForgotPasswordResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public ForgotPasswordResponseBuilder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public ForgotPasswordResponse build() {
            return new ForgotPasswordResponse(token, errorMessage);
        }
    }
}
