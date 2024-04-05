package com.springboot.courses.entity;

public enum MessageErrorCustomer {
    EMAIL {
        @Override
        public String noteMessageError() {
            return "Email has been registered before";
        }
    },
    USERNAME {
        @Override
        public String noteMessageError() {
            return "Username has been registered before";
        }
    },
    PHONE_NUMBER {
        @Override
        public String noteMessageError() {
            return "Phone number has been registered before";
        }
    };

    public abstract String noteMessageError();
}
