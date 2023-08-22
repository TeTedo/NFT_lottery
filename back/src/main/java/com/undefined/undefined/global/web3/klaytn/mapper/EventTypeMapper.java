package com.undefined.undefined.global.web3.klaytn.mapper;

public class EventTypeMapper {

    public static String toAddress(String address) {
        return "0x" + address.substring(26);
    }

    public static Long toLongId(String hexData) {
        return Long.parseLong(hexData.substring(2),16);
    }

}
