// SPDX-License-Identifier: MIT

pragma solidity ^0.8.18;

import "@openzeppelin/contracts/interfaces/IERC721Enumerable.sol";
import "@openzeppelin/contracts/interfaces/IERC721Metadata.sol";

contract GetNftData {
    function ownerOf(
        address _tokenCA,
        uint256 _tokenId
    ) external view returns (address) {
        IERC721Enumerable token = IERC721Enumerable(_tokenCA);

        return token.ownerOf(_tokenId);
    }

    function tokensOfOwner(
        // Enumerable을 안쓴 NFT 컨트렉트는 이거 못함...
        address _tokenCA,
        address _owner
    ) external view returns (uint[] memory) {
        IERC721Enumerable token = IERC721Enumerable(_tokenCA);
        uint balance = token.balanceOf(_owner);
        uint[] memory tokensOfOwnerList = new uint[](balance);

        for (uint256 index = 0; index < balance; index++) {
            tokensOfOwnerList[index] = token.tokenOfOwnerByIndex(_owner, index);
        }

        return tokensOfOwnerList;
    }

    // meta_data

    function name(address _tokenCA) external view returns (string memory) {
        IERC721Metadata token = IERC721Metadata(_tokenCA);

        return token.name();
    }

    function symbol(address _tokenCA) external view returns (string memory) {
        IERC721Metadata token = IERC721Metadata(_tokenCA);

        return token.symbol();
    }

    function tokenURI(
        address _tokenCA,
        uint256 _tokenId
    ) external view returns (string memory) {
        IERC721Metadata token = IERC721Metadata(_tokenCA);

        return token.tokenURI(_tokenId);
    }
}
