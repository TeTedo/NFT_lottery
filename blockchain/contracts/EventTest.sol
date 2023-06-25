// SPDX-License-Identifier: MIT

pragma solidity ^0.8.18;

contract EventTest {
    event Test(uint _uint, string _string, uint blockNumber);

    function test() external {
        emit Test(block.timestamp, 'hi', block.number);
    }

    // event Test(
    //     uint _uint,
    //     string _string,
    //     bool _bool,
    //     address _address,
    //     bytes _bytes,
    //     uint[] _uintArray,
    //     Struct _struct,
    //     uint blockNumber
    // );

    // struct Struct {
    //     uint _uint;
    //     string _string;
    //     bool _bool;
    //     address _address;
    //     bytes _bytes;
    //     uint8[3] _uintArray;
    // }

    // function test() external {
    //     uint _uint = 7878787878;
    //     string memory _string = "hahahahah";
    //     bool _bool = true;
    //     address _address = address(this);
    //     bytes memory _bytes = "kk";
    //     uint[] memory _uintArray = new uint[](3);
    //     for (uint i = 0; i < _uintArray.length; i++) {
    //         _uintArray[i] = i;
    //     }
    //     Struct memory _struct = Struct(
    //         99999,
    //         "struct_string",
    //         true,
    //         address(this),
    //         "struct_bytes",
    //         [1, 2, 3]
    //     );
    //     emit Test(
    //         _uint,
    //         _string,
    //         _bool,
    //         _address,
    //         _bytes,
    //         _uintArray,
    //         _struct,
    //         block.number
    //     );
    // }
}
