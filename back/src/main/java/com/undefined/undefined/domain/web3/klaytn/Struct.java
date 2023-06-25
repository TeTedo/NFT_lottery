package com.undefined.undefined.domain.web3.klaytn;

import lombok.Getter;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.StaticArray3;
import org.web3j.abi.datatypes.generated.Uint8;

import java.util.List;

public abstract class Struct implements Type<List<Type<?>>> {
    private Uint _uint;
    private Utf8String _string;
    private Bool _bool;
    private Address _address;
    private Bytes _bytes;
    private StaticArray3<Uint8> _uintArray;

    // Constructor
    public Struct(List<Type<?>> values) {
        if (values.size() != 6) {
            throw new IllegalArgumentException("Invalid number of values provided for MyStruct");
        }

        this._uint = (Uint) values.get(0);
        this._string = (Utf8String) values.get(1);
        this._bool = (Bool) values.get(2);
        this._address = (Address) values.get(3);
        this._bytes = (Bytes) values.get(4);
        this._uintArray = (StaticArray3<Uint8>) values.get(5);
    }
}