pragma solidity ^0.8.0;

import "@openzeppelin/contracts/token/ERC721/IERC721Enumerable.sol";

contract NftContract {

    function getNftContractAddresses(address _user) external view returns (address[] memory) {
        uint256 nftCount = IERC721Enumerable(_user).balanceOf(_user);
        address[] memory nftContracts = new address[](nftCount);

        for (uint256 i = 0; i < nftCount; i++) {
            uint256 tokenId = IERC721Enumerable(_user).tokenOfOwnerByIndex(_user, i);
            address nftContract = address(IERC721Enumerable(_user).ownerOf(tokenId));
            nftContracts[i] = nftContract;
        }
        return nftContracts;
    }
}
