// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

// import "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";

contract TestToken is ERC721Enumerable {
    uint256 public price;
    string private _baseTokenURI = "http://test.baseURI/";

    constructor(
        string memory name_,
        string memory symbol_,
        uint256 price_
    ) ERC721(name_, symbol_) {
        price = price_;
    }

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
