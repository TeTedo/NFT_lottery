// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

// import "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";

contract TestToken is ERC721Enumerable {
    string private _baseTokenURI = "http://ERC721Enuerable.baseURI/";

    constructor(
        string memory name_,
        string memory symbol_
    ) ERC721(name_, symbol_) {}

    function _baseURI() internal view override returns (string memory) {
        return _baseTokenURI;
    }

    function getCA() external view returns (address) {
        return (address(this));
    }

    function mint(uint256 tokenId) external {
        _safeMint(msg.sender, tokenId);
    }
}
