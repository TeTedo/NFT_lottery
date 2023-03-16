// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "@openzeppelin/contracts/token/ERC721/ERC721.sol";

contract GetNftData {
    function ownerOf(
        address TokenCA,
        uint256 tokenId
    ) external view returns (address) {
        ERC721 Token;
        Token = ERC721(TokenCA);
        return Token.ownerOf(tokenId);
    }

    function tokenURI(
        address TokenCA,
        uint256 tokenId
    ) external view returns (string memory) {
        ERC721 Token;
        Token = ERC721(TokenCA);
        return Token.tokenURI(tokenId);
    }
}
