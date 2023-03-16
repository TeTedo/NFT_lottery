// const { ethers } = require('hardhat');
const { expect } = require('chai');

describe('GetNftData', async () => {
  let testToken, contract, bellyGomToken, owner, account1, account2, tokensOfOwner, baseTokenURI;

  describe('test', () => {
    it('testit', () => {
      console.log('hi');
    });
  });

  beforeEach(async () => {
    // deploy TestToken contract
    const TestToken = await ethers.getContractFactory('TestToken');
    testToken = await TestToken.deploy('TestToken', 'TTK', ethers.utils.parseEther('1'));
    const BellyGomToken = await ethers.getContractFactory('OwnableKIP17');
    bellyGomToken = await BellyGomToken.deploy('BellyGomToken', 'BGT');

    // deploy GenNftData contract
    const Contract = await ethers.getContractFactory('GetNftData');
    contract = await Contract.deploy();

    // set accounts for test
    [owner, account1, account2] = await ethers.getSigners();

    // mint tokenId 1, 2 with owner address
    baseTokenURI = 'http://test.baseURI/';
    tokensOfOwner = [1, 2];

    for (const tokenId of tokensOfOwner) {
      await testToken.connect(owner).mint(tokenId);
    }
    for (const tokenId of tokensOfOwner) {
      await bellyGomToken
        .connect(owner)
        .mintWithTokenURI(owner.address, tokenId, baseTokenURI + tokenId);
    }
  });

  //   call TestToken function from GetNftData contract
  it('function ownerOf', async () => {
    const returnVal1 = await contract.ownerOf(testToken.address, 1);
    const returnVal2 = await contract.ownerOf(bellyGomToken.address, 2);
    expect(returnVal1).to.equal(owner.address);
    expect(returnVal2).to.equal(owner.address);
  });

  it('function tokenURI', async () => {
    const returnVal1 = await contract.tokenURI(testToken.address, 1);
    const returnVal2 = await contract.tokenURI(bellyGomToken.address, 2);
    expect(returnVal1).to.equal(baseTokenURI + '1');
    expect(returnVal2).to.equal(baseTokenURI + '2');
  });

  it('function tokensOfOwner', async () => {
    const returnVal1 = await contract.tokensOfOwner(testToken.address, owner.address);
    const returnVal2 = await contract.tokensOfOwner(testToken.address, owner.address);
    // BigInt가 요소로 들어가있는 배열이 리턴됨 따라서 비교할때 deep-equal로 비교함
    // https://hardhat.org/hardhat-chai-matchers/docs/reference 여기서 Numbers부분 참고
    expect(returnVal1).to.deep.equal(tokensOfOwner);
    expect(returnVal2).to.deep.equal(tokensOfOwner);
  });
});
