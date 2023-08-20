// SPDX-License-Identifier: MIT

pragma solidity 0.8.18;

interface IUndefinedConfig {
    event SetMaxTicketAmount(uint amount);
    event SetMinTicketPrice(uint price);
    event SetFeeNumerator(uint commission);
    event ListNft(address nftCa, address creator, uint creatorFeeNumerator);
    event DeListNft(address nftCa);
    event SetCreatorInfo(address creator, uint creatorFeeNumerator);

    function isListed(address nftCa) external view returns (bool);
    function creatorInfo(address nftCa) external view returns (address creator, uint8 creatorFeeNumerator);
    function maxTicketAmount() external view returns (uint16);
    function minTicketPrice() external view returns (uint96);
    function feeNumerator() external view returns (uint16);
    function feeBox() external view returns (uint240);
    function feeTo() external view returns (address);

    function setFeeNumerator(uint16 _feeNumerator) external;
    function setMaxTicketAmount(uint16 amount) external;
    function setMinTicketPrice(uint96 price) external;
    function listNft(address nftCa) external;
    function listNft(address nftCa, address creator, uint8 creatorFeeNumerator) external;
    function deListNft(address nftCa) external;
}
