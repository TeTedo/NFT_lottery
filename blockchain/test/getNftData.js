const { expect } = require('chai');
const { ethers } = require('hardhat');

describe('GetNftData', async () => {
  let token, contract, owner, account1, account2;

  beforeEach(async () => {
    // deploy TestToken contract
    const Token = await ethers.getContractFactory('TestToken');
    token = await Token.deploy('TestToken', 'TTK', ethers.utils.parseEther('1'));

    // deploy GenNftData contract
    const Contract = await ethers.getContractFactory('GetNftData');
    contract = await Contract.deploy();

    // set accounts for test
    [owner, account1, account2] = await ethers.getSigners();
  });

  it('mint Token', async () => {
    await token.connect(owner).mint(1);
    expect(await token.ownerOf(1)).to.equal(owner.address);
  });

  //   call TestToken function from GetNftData contract
  it('function ownerOf', async () => {
    await token.connect(owner).mint(1);
    expect(await contract.ownerOf(token.address, 1)).to.equal(owner.address);
  });
});
