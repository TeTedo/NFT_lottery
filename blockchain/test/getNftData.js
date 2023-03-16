// const { ethers } = require('hardhat');
const { expect } = require('chai');

describe('GetNftData', async () => {
  let token, contract, owner, account1, account2, tokensOfOwner;

  beforeEach(async () => {
    // deploy TestToken contract
    const Token = await ethers.getContractFactory('TestToken');
    token = await Token.deploy('TestToken', 'TTK', ethers.utils.parseEther('1'));

    // deploy GenNftData contract
    const Contract = await ethers.getContractFactory('GetNftData');
    contract = await Contract.deploy();

    // set accounts for test
    [owner, account1, account2] = await ethers.getSigners();

    // mint tokenId 1, 2 with owner address
    tokensOfOwner = [1, 2];
    for (const tokenId of tokensOfOwner) {
      await token.connect(owner).mint(tokenId);
    }
  });

  //   it('mint Token', async () => {
  //     const returnVal = await token.ownerOf(1);
  //     expect(returnVal).to.equal(owner.address);
  //   });

  //   call TestToken function from GetNftData contract
  it('function ownerOf', async () => {
    const returnVal = await contract.ownerOf(token.address, 1);
    expect(returnVal).to.equal(owner.address);
  });

  it('function tokenURI', async () => {
    const baseTokenURI = 'http://test.baseURI/';
    const returnVal = await contract.tokenURI(token.address, 1);
    expect(returnVal).to.equal(baseTokenURI + '1');
    console.log('@tokenUri: ' + returnVal);
  });

  it('function tokensOfOwner', async () => {
    const returnVal = await contract.tokensOfOwner(token.address, owner.address);
    expect(returnVal).to.deep.equal(tokensOfOwner);
  });
});
