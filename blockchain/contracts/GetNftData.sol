// SPDX-License-Identifier: MIT

pragma solidity ^0.8.18;

import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";

contract GetNftData {
    function ownerOf(
        address tokenCA,
        uint256 tokenId
    ) external view returns (address) {
        ERC721Enumerable Token = ERC721Enumerable(tokenCA);

        return Token.ownerOf(tokenId);
    }

    function tokenURI(
        address tokenCA,
        uint256 tokenId
    ) external view returns (string memory) {
        ERC721Enumerable Token = ERC721Enumerable(tokenCA);

        return Token.tokenURI(tokenId);
    }

    function tokensOfOwner(
        // Enumerable을 안쓴 NFT 컨트렉트는 이거 못함...
        address tokenCA,
        address owner
    ) external view returns (uint[] memory) {
        ERC721Enumerable Token = ERC721Enumerable(tokenCA);
        uint balance = Token.balanceOf(owner);
        uint[] memory tokensOfOwnerList = new uint[](balance);

        for (uint index = 0; index < balance; index++) {
            tokensOfOwnerList[index] = Token.tokenOfOwnerByIndex(owner, index);
        }

        return tokensOfOwnerList;
    }
}
