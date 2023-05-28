// SPDX-License-Identifier: MIT

pragma solidity ^0.8.18;

import "@openzeppelin/contracts/interfaces/IERC721Enumerable.sol";
import "@openzeppelin/contracts/interfaces/IERC721Metadata.sol";

contract GetNftData {
    struct TokensOfOwner {
        address tokenCa;
        uint256[] tokenIds;
    }

    struct UriOfTokens {
        address tokenCa;
        string[] tokenUris;
    }

    function getOwnerOf(
        address tokenCa,
        uint256 tokenId
    ) external view returns (address) {
        IERC721Enumerable token = IERC721Enumerable(tokenCa);

        return token.ownerOf(tokenId);
    }

    function getTokensOfOwner(
        // Enumerable을 안쓴 NFT 컨트렉트는 이거 못함...
        address tokenCa,
        address owner
    ) public view returns (uint256[] memory) {
        IERC721Enumerable token = IERC721Enumerable(tokenCa);
        uint balance = token.balanceOf(owner);
        if (balance == 0) return new uint256[](0);

        uint256[] memory tokensOfOwner = new uint256[](balance);
        for (uint256 index = 0; index < balance; index++) {
            tokensOfOwner[index] = token.tokenOfOwnerByIndex(owner, index);
        }

        return tokensOfOwner;
    }

    function getTokensOfOwnerBatch(
        address[] calldata tokenCaList,
        address owner
    ) external view returns (TokensOfOwner[] memory) {
        TokensOfOwner[] memory tokensOfOwner = new TokensOfOwner[](
            tokenCaList.length
        );

        for (uint i = 0; i < tokenCaList.length; i++) {
            uint256 count = 0;
            address tokenCa = tokenCaList[i];
            uint256[] memory tokenIds = getTokensOfOwner(tokenCa, owner);
            if (tokenIds.length == 0) continue;
            tokensOfOwner[count] = TokensOfOwner(tokenCa, tokenIds);
            count++;
        }

        return tokensOfOwner; // [{tokenCa, tokenIds[]}, {tokenCa, tokenIds[]}, ...]
    }

    // meta_data
    function getName(address tokenCa) external view returns (string memory) {
        IERC721Metadata token = IERC721Metadata(tokenCa);

        return token.name();
    }

    function getSymbol(address tokenCa) external view returns (string memory) {
        IERC721Metadata token = IERC721Metadata(tokenCa);

        return token.symbol();
    }

    function getTokenUri(
        address tokenCa,
        uint256 tokenId
    ) external view returns (string memory) {
        IERC721Metadata token = IERC721Metadata(tokenCa);
        return token.tokenURI(tokenId);
    }

    function getTokenUriBatch(
        address tokenCa,
        uint256[] calldata tokenIds
    ) external view returns (string[] memory) {
        IERC721Metadata token = IERC721Metadata(tokenCa);
        string[] memory tokenUris = new string[](tokenIds.length);

        for (uint i = 0; i < tokenIds.length; i++) {
            tokenUris[i] = token.tokenURI(tokenIds[i]);
        }

        return tokenUris;
    }
}
