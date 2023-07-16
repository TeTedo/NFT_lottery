const { expect } = require("chai");
const { ethers } = require("hardhat");

describe("GlobalStates Contract", function () {
  let globalStatesContract;

  beforeEach(async function () {
    const GlobalStates = await ethers.getContractFactory("GlobalStates");
    globalStatesContract = await GlobalStates.deploy(1000, 10);
    // Deploy contract with maxTicketLimit = 1000, minTicketPrice = 10 ether
    await globalStatesContract.deployed();
  });

  it("Should set the right values for maxTicketLimit and minTicketPrice", async function () {
    const maxTicketLimit = await globalStatesContract.maxTicketLimit();
    const minTicketPrice = await globalStatesContract.minTicketPrice();

    expect(maxTicketLimit).to.equal(1000);
    expect(minTicketPrice).to.equal(ethers.utils.parseEther("10"));
  });

  it("Should allow owner to change maxTicketLimit", async function () {
    await globalStatesContract.changeMaxTicketLimit(2000);
    const maxTicketLimit = await globalStatesContract.maxTicketLimit();
    expect(maxTicketLimit).to.equal(2000);
  });
  it("Should not allow non-owner to change maxTicketLimit", async function () {
    const [_, nonOwner] = await ethers.getSigners();
    await expect(globalStatesContract.connect(nonOwner).changeMaxTicketLimit(5)).to.be.revertedWith(
      "Ownable: caller is not the owner"
    );
  });

  it("Should allow owner to change minTicketPrice", async function () {
    await globalStatesContract.changeMinTicketPrice(5);
    const minTicketPrice = await globalStatesContract.minTicketPrice();
    expect(minTicketPrice).to.equal(ethers.utils.parseEther("5"));
  });

  it("Should not allow non-owner to change minTicketPrice", async function () {
    const [_, nonOwner] = await ethers.getSigners();
    await expect(
      globalStatesContract.connect(nonOwner).changeMinTicketPrice(5000)
    ).to.be.revertedWith("Ownable: caller is not the owner");
  });
});
