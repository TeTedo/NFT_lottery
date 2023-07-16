// SPDX-License-Identifier: MIT

pragma solidity 0.8.18;

interface IUndefinedConfig {
    struct CreatorInfo {
        address creator;
        uint8 creatorFeeNumerator;
    }

    event SetMaxTicketAmount(uint amount);
    event SetMinTicketPrice(uint price);
    event SetFeeNumerator(uint16 commission);
    event SetFeeTo(address feeTo);
    event ListNft(address nftCa, address creator, uint8 creatorFeeNumerator);
    event DeListNft(address nftCa);
    event SetCreatorInfo(address creator, uint8 creatorFeeNumerator);

    function isListed(address nftCa) external view returns (bool);
    function creatorInfo(address nftCa) external view returns (address creator, uint8 creatorFeeNumerator);
    function feeBox() external view returns (uint);
    function feeNumerator() external view returns (uint16);
    function maxTicketAmount() external view returns (uint16);
    function minTicketPrice() external view returns (uint224);

    function setFeeNumerator(uint16 _feeNumerator) external;
    function setMaxTicketAmount(uint16 amount) external;
    function setMinTicketPrice(uint224 price) external;
    function listNft(address nftCa) external;
    function listNft(address nftCa, address creator, uint8 creatorFeeNumerator) external;
    function deListNft(address nftCa) external;
}
