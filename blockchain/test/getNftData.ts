// const { ethers } = require('hardhat');
const { expect } = require("chai");
import { Signer } from "ethers";
import { GetNftData, TestToken, OwnableKIP17 } from "../typechain-types";
import { ethers } from "hardhat";

describe("GetNftData", async () => {
  let token: TestToken,
    contract: GetNftData,
    owner: Signer,
    account1: Signer,
    account2: Signer,
    tokensOfOwner: number[],
    baseTokenURI: string;

  beforeEach(async () => {
    // set accounts for test
    [owner, account1, account2] = await ethers.getSigners();

    // deploy GetNftData contract
    const Contract = await ethers.getContractFactory("GetNftData");
    contract = await Contract.deploy();
  });

  // ==================================ERC721Eumerable=======================================
  describe("ERC721Eumerable", () => {
    beforeEach(async () => {
      // deploy Token contract
      const Token = await ethers.getContractFactory("TestToken");
      token = await Token.deploy("TestToken", "TTK");
      baseTokenURI = "http://ERC721Enuerable.baseURI/";

      // mint tokenId 1, 2 with owner address
      tokensOfOwner = [1, 2];

      for (const tokenId of tokensOfOwner) {
        await token.connect(owner).mint(tokenId);
      }
    });

    it("function ownerOf", async () => {
      const returnVal = await contract.getOwnerOf(token.address, 1);
      expect(returnVal).to.equal(await owner.getAddress());
    });

    it("function tokenURI", async () => {
      const returnVal = await contract.getTokenUri(token.address, 1);
      console.log("@@@@@@@@tokenURI: tokenURI", returnVal);
      expect(returnVal).to.equal(baseTokenURI + "1");
    });

    it("function tokensOfOwner", async () => {
      const returnVal = await contract.getTokensOfOwner(token.address, await owner.getAddress());
      // BigInt가 요소로 들어가있는 배열이 리턴됨 따라서 비교할때 deep-equal로 비교함
      // https://hardhat.org/hardhat-chai-matchers/docs/reference 여기서 Numbers부분 참고
      expect(returnVal).to.deep.equal(tokensOfOwner);
    });

    it("batch test", async () => {
      const tokensArr: TestToken[] = [];
      const TEST_AMOUNT = 10;
      for (let i = 1; i <= TEST_AMOUNT; i++) {
        const Token = await ethers.getContractFactory("TestToken");
        const token = await Token.deploy(`TestToken${i}`, `TTK${i}`);
        tokensArr.push(token);
      }
      const tokenIdsToMintArr = new Array(TEST_AMOUNT).fill(0).map(() => {
        const ranNum = Math.floor(Math.random() * 10);
        const res = [];
        for (let i = 1; i <= ranNum; i++) {
          res.push(i);
        }
        return res;
      });

      // mint
      for (let i = 0; i < tokensArr.length; i++) {
        const tokenIds = tokenIdsToMintArr[i];
        for (let k = 0; k < tokenIds.length; k++) {
          tokensArr[i].connect(owner).mint(tokenIds[k]);
        }
      }

      const tokenCaList = tokensArr.map((token) => token.address);
      const listedNfts = tokenCaList;
      const balanceOfNfts = await contract.connect(owner).getBalanceOfListedNft(listedNfts);
      const filtered = balanceOfNfts.filter((each) => each.balance.gt(0));
      const filteredTokenIdsToMintArr = tokenIdsToMintArr.filter((each) => each.length > 0);
      const returnVal = await contract.connect(owner).getTokensOfOwnerBatch(filtered);
      returnVal.forEach((val, ind) => {
        expect(val.tokenCa).to.equal(filtered[ind].nftCa);
        console.log(val.tokenCa);
        expect(val.tokenIds).to.deep.equal(filteredTokenIdsToMintArr[ind]);
        console.log(val.tokenIds.toString());
      });
    });
  });

  // ==================================OwnableKIP17=======================================
  describe("OwnableKIP17", () => {
    let token: OwnableKIP17;
    beforeEach(async () => {
      // deploy Token contract
      const Token = await ethers.getContractFactory("OwnableKIP17");
      token = await Token.deploy("BellyGomToken", "BGT");
      baseTokenURI = "http://OwnableKIP17.baseURI/";

      // mint tokenId 1, 2 with owner address
      tokensOfOwner = [1, 2];

      for (const tokenId of tokensOfOwner) {
        await token
          .connect(owner)
          .mintWithTokenURI(await owner.getAddress(), tokenId, baseTokenURI + tokenId);
      }
    });

    it("function ownerOf", async () => {
      const returnVal = await contract.getOwnerOf(token.address, 1);
      expect(returnVal).to.equal(await owner.getAddress());
    });

    it("function tokenURI", async () => {
      const returnVal = await contract.getTokenUri(token.address, 1);
      expect(returnVal).to.equal(baseTokenURI + "1");
      console.log("@@@@@@@@tokenURI: ", returnVal);
    });

    it("function tokensOfOwner", async () => {
      const returnVal = await contract.getTokensOfOwner(token.address, await owner.getAddress());
      expect(returnVal).to.deep.equal(tokensOfOwner);
    });
  });
});
