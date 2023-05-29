// SPDX-License-Identifier: MIT

pragma solidity ^0.8.18;

import "@openzeppelin/contracts/interfaces/IERC721Enumerable.sol";
import "@openzeppelin/contracts/interfaces/IERC721Metadata.sol";
import "hardhat/console.sol";

contract GetNftData {
    struct BalanceOfNft {
        address nftCa;
        uint128 balance;
    }
    struct TokensOfOwner {
        address tokenCa;
        uint128[] tokenIds;
    }
    struct UriOfTokens {
        address tokenCa;
        string[] tokenUris;
    }

    function getOwnerOf(
        address nftCa,
        uint256 tokenId
    ) external view returns (address) {
        IERC721Enumerable token = IERC721Enumerable(nftCa);

        return token.ownerOf(tokenId);
    }

    // 마이페이지에 노출시키는 유저 nft 리스트 뽑는 방법
    // 1. 아래 getBalanceOfListedNft 함수를 호출하는데 파라미터로 상장된 nft CA 리스트를 배열로 보낸다
    // 2. [{nftCa, balance}, {nftCa, balance}, {nftCa, balance}...] 이런식으로 각각 nft의 CA와 balance가 배열로 리턴됨
    // 3. 이 배열을 filter 해서 balance가 0 이상인것만 뽑는다. -> arr.filter(each=>each.balance.gt(0))
    // 4. 필터링한 배열을 그대로 parameter로 넣어서 getTokensOfOwnerBatch 함수를 호출한다.

    function getBalanceOfListedNft(
        address[] calldata listedNftList,
        address owner
    ) external view returns (BalanceOfNft[] memory) {
        BalanceOfNft[] memory balanceOfNft = new BalanceOfNft[](
            listedNftList.length
        );
        for (uint i = 0; i < listedNftList.length; i++) {
            IERC721Enumerable nft = IERC721Enumerable(listedNftList[i]);
            uint256 balance = nft.balanceOf(owner);
            balanceOfNft[i] = BalanceOfNft(listedNftList[i], uint128(balance));
        }
        return balanceOfNft;
    }

    function getTokensOfOwnerBatch(
        BalanceOfNft[] calldata balanceOfNftList,
        address owner
    ) external view returns (TokensOfOwner[] memory) {
        TokensOfOwner[] memory tokensOfOwner = new TokensOfOwner[](
            balanceOfNftList.length
        );

        for (uint i = 0; i < balanceOfNftList.length; i++) {
            address nftCa = balanceOfNftList[i].nftCa;
            uint128 balance = balanceOfNftList[i].balance;
            IERC721Enumerable nft = IERC721Enumerable(nftCa);
            uint128[] memory tokenIds = new uint128[](balance);
            for (uint256 index = 0; index < balance; index++) {
                tokenIds[index] = uint128(
                    nft.tokenOfOwnerByIndex(owner, index)
                );
            }
            tokensOfOwner[i] = TokensOfOwner(nftCa, tokenIds);
        }

        return tokensOfOwner; // [{tokenCa, tokenIds[]}, {tokenCa, tokenIds[]}, ...]
    }

    // getTokensOfOwnerWithListedNft 이함수는
    // 위에 두 함수기능을 합친거다.(필터링 제외)
    // 차라리 이 함수를 페이지네이션으로 쪼개서
    // 프론트에서 promiseall로 호출하는게 더 성능이 나을지도...
    // EVM에서 배열을 return 할때 배열의 길이가 길어지면
    // 처리속도가 배열길이에 따라 비례적이 아니라 기하급수적으로 올라간다.
    // 그래서 최대한 잘게쪼개서 여러번 호출하는게 좋다.
    function getTokensOfOwnerWithListedNft(
        address[] calldata listedNftList,
        address owner
    ) external view returns (TokensOfOwner[] memory) {
        TokensOfOwner[] memory tokensOfOwner = new TokensOfOwner[](
            listedNftList.length
        );
        for (uint i = 0; i < listedNftList.length; i++) {
            address nftCa = listedNftList[i];
            IERC721Enumerable nft = IERC721Enumerable(nftCa);
            uint256 balance = nft.balanceOf(owner);
            uint128[] memory tokenIds = new uint128[](balance);
            for (uint256 index = 0; index < balance; index++) {
                tokenIds[index] = uint128(
                    nft.tokenOfOwnerByIndex(owner, index)
                );
            }
            tokensOfOwner[i] = TokensOfOwner(nftCa, tokenIds);
        }
        return tokensOfOwner;
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
