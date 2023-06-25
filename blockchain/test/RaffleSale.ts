import { Contract, Event, Signer } from "ethers";
import { ethers, upgrades } from "hardhat";
import { expect } from "chai";
import { Undefined, TestToken } from "../typechain-types/index";
import { parseEther } from "ethers/lib/utils";

describe("Raffle", () => {
  const MAX_TICKET_AMOUNT = 1_000;
  const MIN_TICKET_PRICE = parseEther("0.01");
  const COMMISSION = 100; // 0.1% 단위
  let raffleProxy: Undefined;
  let testToken: TestToken;
  let owner: Signer, seller: Signer, buyer1: Signer, buyer2: Signer, buyer3: Signer, vault: Signer;
  let raffleId: string;
  let winnerAddr: string;

  before(async () => {
    [owner, seller, buyer1, buyer2, buyer3, vault] = await ethers.getSigners();

    // deploy raffle contract by proxy
    const raffleFactory = await ethers.getContractFactory("Undefined", owner);
    raffleProxy = (await upgrades.deployProxy(
      raffleFactory,
      [MAX_TICKET_AMOUNT, MIN_TICKET_PRICE, COMMISSION, await vault.getAddress()],
      { initializer: "initialize", kind: "transparent", unsafeAllow: ["constructor"] }
    )) as Undefined;
    await raffleProxy.deployed();

    // console.log("@@", await raffleProxy.getEnvInfo());

    // deploy token
    const testTokenFactory = await ethers.getContractFactory("TestToken");
    testToken = (await testTokenFactory.deploy("Test", "TST")) as TestToken;
    await testToken.deployed();
  });

  it("mint nft", async () => {
    const tokenId = 10;
    const mintTx = await testToken.connect(seller).mint(tokenId);
    await mintTx.wait();
    expect(await testToken.ownerOf(tokenId)).to.equal(await seller.getAddress());
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
    const ticketAmount = MAX_TICKET_AMOUNT;
    const ticketPrice = MIN_TICKET_PRICE;
    const day = 2;

    // approve token from seller to raffle sale proxy contract
    const approveTx = await testToken.connect(seller).approve(raffleProxy.address, tokenId);
    await approveTx.wait();

    // register raffle
    const registerTx = await raffleProxy
      .connect(seller)
      .registerRaffle(nftCa, tokenId, ticketAmount, ticketPrice, day);
    const registerReceipt = await registerTx.wait();
    registerReceipt.events?.forEach((event) => {
      raffleId = event.args?.at(0).raffleId.toString();
    });

    expect((await raffleProxy.raffles(raffleId)).seller).to.equal(sellerAddr);
  });

  it("buy tickets", async () => {
    const buyAmount = 30;
    const ticketPrice = (await raffleProxy.raffles(raffleId)).ticketPrice;

    const buyTx = await raffleProxy
      .connect(buyer1)
      .buyTickets(raffleId, buyAmount, { value: ticketPrice.mul(buyAmount) });
    await buyTx.wait();

    // console.log(await raffleProxy.getRaffleInfo(raffleId));
  });

  it("buy left overs", async () => {
    const ticketPrice = (await raffleProxy.raffles(raffleId)).ticketPrice;
    let leftTicketAmount = (await raffleProxy.raffles(raffleId)).leftTickets;

    const buyTx1 = await raffleProxy.connect(buyer2).buyTickets(raffleId, leftTicketAmount.div(2), {
      value: ticketPrice.mul(leftTicketAmount.div(2)),
    });
    await buyTx1.wait();
    leftTicketAmount = (await raffleProxy.raffles(raffleId)).leftTickets;
    const buyTx2 = await raffleProxy
      .connect(buyer3)
      .buyTickets(raffleId, leftTicketAmount, { value: ticketPrice.mul(leftTicketAmount) });
    await buyTx2.wait();
    // console.log(await raffleProxy.getRaffleInfo(nftCa, tokenId));
  });

  it("choose winner", async () => {
    const randNum = Math.floor(Math.random() * 1_000_000);
    const chooseTx = await raffleProxy
      .connect(owner)
      .chooseWinner(raffleId, randNum, ethers.constants.AddressZero, 0);
    const chooseReceipt = await chooseTx.wait();
    const events = chooseReceipt.events;
    let winnerTicketIndex = "";
    events?.forEach((event) => {
      console.log();
      winnerAddr = event.args?.winner;
      winnerTicketIndex = event.args?.winnerTicketIndex.toString();
      console.log("winner: ", winnerAddr);
      console.log("winner ticket index: ", winnerTicketIndex);
    });
    const winnerTicketOwner = await raffleProxy.getTicketOwnerByIndex(raffleId, winnerTicketIndex);
    console.log("winner Ticket owner: ", winnerTicketOwner);
    expect(winnerTicketOwner).to.equal(winnerAddr);
  }); // max gas used = 303789

  it("claim nft", async () => {
    const nftsLength = await raffleProxy.getClaimableNftsLength(winnerAddr);
    const claimableNfts = [];
    for (let i = 0; i < Number(nftsLength); i++) {
      claimableNfts.push(await raffleProxy.claimableNft(winnerAddr, i));
    }
    console.log("claimable nfts", claimableNfts);

    const winnerSigner = await ethers.getSigner(winnerAddr);

    const claimTx = await raffleProxy.connect(winnerSigner).claimNftByIndex(0);
    const claimReceipt = await claimTx.wait();
    const claimEvents = claimReceipt.events;
    claimEvents?.forEach((event) => {
      console.log("claim events", event.args);
    });
  });

  // const result2 = await raffleProxy.getClaimableNfts(winner);
  // console.log(result2);
  // const balanceOfWinner = await testToken.balanceOf(winner);
  // console.log(balanceOfWinner);
});
