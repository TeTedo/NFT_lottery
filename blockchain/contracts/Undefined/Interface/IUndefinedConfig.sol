// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

interface IUndefinedConfig {
    event SetMaxTicketAmount(uint amount);
    event SetMinTicketPrice(uint price);
    event SetFeeNumerator(uint16 commission);
    event ListNft(address nftCa);
    event DeListNft(address nftCa);

    function isListed(address nftCa) external view returns (bool);
    function feeTo() external view returns (address);
    function maxTicketAmount() external view returns (uint120);
    function minTicketPrice() external view returns (uint120);
    function feeNumerator() external view returns (uint16);

    function setMaxTicketAmount(uint120 amount) external;
    function setMinTicketPrice(uint120 price) external;
    function setFeeNumerator(uint16 _feeNumerator) external;
    function listNft(address nftCa) external;
    function deListNft(address nftCa) external;
}
