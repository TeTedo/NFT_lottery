import { Contract, Event, Signer } from "ethers";
import { ethers, upgrades } from "hardhat";
import { expect } from "chai";
import { Raffle, Raffle__factory, TestToken, TestToken__factory } from "../typechain-types/index";

describe("Raffle", () => {
  const MAX_TICKET_AMOUNT = 1_000;
  const MIN_TICKET_PRICE = ethers.utils.parseEther("1");
  const COMMISSION_PERCENTAGE = 10;
  let raffleProxy: Raffle;
  let testToken: TestToken;
  let owner: Signer, seller: Signer, buyer1: Signer, buyer2: Signer, buyer3: Signer;

  before(async () => {
    [owner, seller, buyer1, buyer2, buyer3] = await ethers.getSigners();

    // deploy raffle contract by proxy
    const raffleFactory = await ethers.getContractFactory("Raffle", owner);
    raffleProxy = (await upgrades.deployProxy(
      raffleFactory,
      [MAX_TICKET_AMOUNT, MIN_TICKET_PRICE, COMMISSION_PERCENTAGE],
      { initializer: "initialize", kind: "transparent", unsafeAllow: ["constructor"] }
    )) as Raffle;
    await raffleProxy.deployed();

    // deploy token
    const testTokenFactory = await ethers.getContractFactory("TestToken");
    testToken = (await testTokenFactory.deploy("Test", "TST")) as TestToken;
    await testToken.deployed();
  });

  it("mint token", async () => {
    const mintTx = await testToken.connect(seller).mint(1);
    await mintTx.wait();
    expect(await testToken.ownerOf(1)).to.equal(await seller.getAddress());
  });

  it("list nft", async () => {
    const nftCa = testToken.address;

    const listTx = await raffleProxy.connect(owner).listNft(nftCa);
    await listTx.wait();

    const isListed = await raffleProxy.isListed(nftCa);
    expect(isListed).to.eq(true);
  });

  it("register raffle", async () => {
    const sellerAddr = await seller.getAddress();
    const nftCa = testToken.address;
    const tokenId = testToken.tokenOfOwnerByIndex(sellerAddr, 0);
    const ticketAmount = 100;
    const ticketPrice = ethers.utils.parseEther("1");
    const day = 2;

    // approve token from seller to raffle sale proxy contract
    const approveTx = await testToken.connect(seller).approve(raffleProxy.address, tokenId);
    await approveTx.wait();

    // register raffle
    const registerTx = await raffleProxy
      .connect(seller)
      .registerRaffle(nftCa, tokenId, ticketAmount, ticketPrice, day);
    await registerTx.wait();

    expect((await raffleProxy.getRaffleInfo(nftCa, tokenId)).seller).to.equal(sellerAddr);
  });

  it("buy tickets", async () => {
    const nftCa = testToken.address;
    const tokenId = testToken.tokenOfOwnerByIndex(raffleProxy.address, 0);
    const buyAmount = 30;

    // buyTickets
    const ticketPrice = (await raffleProxy.getRaffleInfo(nftCa, tokenId)).ticketPrice;
    const buyTx = await raffleProxy
      .connect(buyer1)
      .buyTickets(nftCa, tokenId, buyAmount, { value: ticketPrice.mul(buyAmount) });
    await buyTx.wait();

    // console.log(await raffleProxy.getRaffleInfo(nftCa, tokenId));
  });

  it("choose winner", async () => {
    const nftCa = testToken.address;
    const tokenId = testToken.tokenOfOwnerByIndex(raffleProxy.address, 0);
    let leftTicketAmount = (await raffleProxy.getRaffleInfo(nftCa, tokenId)).leftTicketAmount;

    // buyLeftOvers
    const ticketPrice = (await raffleProxy.getRaffleInfo(nftCa, tokenId)).ticketPrice;
    const buyTx1 = await raffleProxy
      .connect(buyer2)
      .buyTickets(nftCa, tokenId, leftTicketAmount.div(2), {
        value: ticketPrice.mul(leftTicketAmount.div(2)),
      });
    await buyTx1.wait();
    leftTicketAmount = (await raffleProxy.getRaffleInfo(nftCa, tokenId)).leftTicketAmount;
    const buyTx2 = await raffleProxy
      .connect(buyer3)
      .buyTickets(nftCa, tokenId, leftTicketAmount, { value: ticketPrice.mul(leftTicketAmount) });
    await buyTx2.wait();
    // console.log(await raffleProxy.getRaffleInfo(nftCa, tokenId));

    const randNum = Math.floor(Math.random() * 1_000_000);
    const chooseTx = await raffleProxy.connect(owner).chooseWinner(nftCa, tokenId, randNum);
    const chooseReceipt = await chooseTx.wait();
    const events = chooseReceipt.events;
    events?.forEach((event) => {
      console.log("winner", event.args?.at(0));
    });
  });
});
