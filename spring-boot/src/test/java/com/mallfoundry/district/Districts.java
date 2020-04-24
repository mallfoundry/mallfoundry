package com.mallfoundry.district;

public class Districts {
    static class District {

        private String code;

        private String name;

        private String parentCode;

        private District() {
        }

        public District(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentCode() {
            return parentCode;
        }

        public void setParentCode(String parentCode) {
            this.parentCode = parentCode;
        }
    }

    public static District cityOf(String code, String name) {
        var city = new District(code, name);
        if (code.startsWith("11") && !code.endsWith("0000")) {
            city.setParentCode("110000");
        } else if (code.startsWith("12") && !code.endsWith("0000")) {
            city.setParentCode("120000");
        } else if (code.startsWith("31") && !code.endsWith("0000")) {
            city.setParentCode("310000");
        } else if (code.startsWith("50") && !code.endsWith("0000")) {
            city.setParentCode("500000");
        } else if (!code.endsWith("0000") && code.endsWith("00")) {
            city.setParentCode(String.format("%s0000", code.substring(0, 2)));
        }
        return city;
    }

    public static District countyOf(String code, String name) {
        var county = new District(code, name);
        if ("419001".equals(code)) {
            county.setParentCode("411700");
        } else {
            county.setParentCode(String.format("%s00", code.substring(0, 4)));
        }
        return county;
    }

}
