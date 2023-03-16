// const { ethers } = require('hardhat');
const { expect } = require('chai');

describe('GetNftData', async () => {
  let token,
    contract,
    testToken,
    bellyGomToken,
    owner,
    account1,
    account2,
    tokensOfOwner,
    baseTokenURI;

  // ==================================ERC721Eumerable=======================================
  describe('ERC721Eumerable', () => {
    beforeEach(async () => {
      // deploy Token contract
      const Token = await ethers.getContractFactory('TestToken');
      token = await Token.deploy('TestToken', 'TTK', ethers.utils.parseEther('1'));

      // deploy GetNftData contract
      const Contract = await ethers.getContractFactory('GetNftData');
      contract = await Contract.deploy();

      // set accounts for test
      [owner, account1, account2] = await ethers.getSigners();

      // mint tokenId 1, 2 with owner address
      baseTokenURI = 'http://test.baseURI/';
      tokensOfOwner = [1, 2];

      for (const tokenId of tokensOfOwner) {
        await token.connect(owner).mint(tokenId);
      }
    });

    it('function ownerOf', async () => {
      const returnVal = await contract.ownerOf(token.address, 1);
      expect(returnVal).to.equal(owner.address);
    });

    it('function tokenURI', async () => {
      const returnVal = await contract.tokenURI(token.address, 1);
      expect(returnVal).to.equal(baseTokenURI + '1');
    });

    it('function tokensOfOwner', async () => {
      const returnVal = await contract.tokensOfOwner(token.address, owner.address);
      // BigInt가 요소로 들어가있는 배열이 리턴됨 따라서 비교할때 deep-equal로 비교함
      // https://hardhat.org/hardhat-chai-matchers/docs/reference 여기서 Numbers부분 참고
      expect(returnVal).to.deep.equal(tokensOfOwner);
    });
  });

  // ==================================OwnableKIP17=======================================
  describe('OwnableKIP17', () => {
    beforeEach(async () => {
      // deploy Token contract
      const Token = await ethers.getContractFactory('OwnableKIP17');
      token = await Token.deploy('BellyGomToken', 'BGT');

      // deploy GetNftData contract
      const Contract = await ethers.getContractFactory('GetNftData');
      contract = await Contract.deploy();

      // set accounts for test
      [owner, account1, account2] = await ethers.getSigners();

      // mint tokenId 1, 2 with owner address
      baseTokenURI = 'http://test.baseURI/';
      tokensOfOwner = [1, 2];

      for (const tokenId of tokensOfOwner) {
        await token.connect(owner).mintWithTokenURI(owner.address, tokenId, baseTokenURI + tokenId);
      }
    });

    it('function ownerOf', async () => {
      const returnVal = await contract.ownerOf(token.address, 1);
      expect(returnVal).to.equal(owner.address);
    });

    it('function tokenURI', async () => {
      const returnVal = await contract.tokenURI(token.address, 1);
      expect(returnVal).to.equal(baseTokenURI + '1');
    });

    it('function tokensOfOwner', async () => {
      const returnVal = await contract.tokensOfOwner(token.address, owner.address);
      expect(returnVal).to.deep.equal(tokensOfOwner);
    });
  });
});
