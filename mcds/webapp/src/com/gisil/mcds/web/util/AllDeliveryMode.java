package com.gisil.mcds.web.util;

public enum AllDeliveryMode {
	ALL {

        @Override
        public String toDbLiteral() {
            return "ALL";
        }
    }
    ,
    SMS {

        @Override
        public String toDbLiteral() {
            return "SMS";
        }
    }
    ,
    BLUETOOTH {

        @Override
        public String toDbLiteral() {
            return "BLUETOOTH";
        }
    };

    public abstract String toDbLiteral();
    
    public String toString() {
        return toDbLiteral();
    }

}
