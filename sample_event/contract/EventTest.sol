// SPDX-License-Identifier: MIT

pragma solidity ^0.8.18;

contract EventTest {
    event Test(uint _uint, string _string, uint blockNumber);

    function test() external {
        emit Test(block.timestamp, 'hi', block.number);
    }
}
