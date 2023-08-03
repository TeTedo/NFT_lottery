import { BigNumber, Contract, Event, Signer } from "ethers";
import { ethers, network, upgrades } from "hardhat";
import { expect } from "chai";
import {
  Undefined,
  TestToken,
  TestToken__factory,
  IUndefined,
  Undefined__factory,
} from "../typechain-types/index";
import { parseEther } from "ethers/lib/utils";
import { chains } from "../ common/config";

describe("Raffle", () => {
  // const MAX_TICKET_AMOUNT = BigNumber.from(2).pow(80) // out-of-bounds error
  const MAX_TICKET_AMOUNT = 1_000;
  const MIN_TICKET_PRICE = ethers.utils.parseEther("0.0001");
  const FEE_NUMERATOR = 100; // 0.1% 단위
  let raffleProxy: Undefined;
  let testToken: TestToken;
  let owner: Signer, seller: Signer, buyer1: Signer, buyer2: Signer, buyer3: Signer, vault: Signer;
  let raffleId: string;
  let winnerAddr: string;

  before(async () => {
    if (network.name === "hardhat") {
      [owner, seller, buyer1, buyer2, buyer3, vault] = await ethers.getSigners();

      // deploy raffle contract by proxy
      const raffleFactory = await ethers.getContractFactory("Undefined", owner);
      raffleProxy = (await upgrades.deployProxy(
        raffleFactory,
        [FEE_NUMERATOR, MAX_TICKET_AMOUNT, MIN_TICKET_PRICE],
        { initializer: "initialize", kind: "transparent", unsafeAllow: ["constructor"] }
      )) as Undefined;
      await raffleProxy.deployed();

      // deploy token
      const testTokenFactory = await ethers.getContractFactory("TestToken");
      testToken = (await testTokenFactory.deploy("Test", "TST")) as TestToken;
      await testToken.deployed();
      //
    } else {
      if (!chains[network.name]) throw new Error("wrong network");
      raffleProxy = new ethers.Contract(
        chains[network.name].ca.raffle,
        Undefined__factory.abi,
        ethers.provider
      ) as Undefined;
      testToken = new ethers.Contract(
        chains[network.name].ca.testNft,
        TestToken__factory.abi,
        ethers.provider
      ) as TestToken;
      [owner, seller, buyer1, buyer2, buyer3] = chains[network.name].pk.map(
        (pk) => new ethers.Wallet(pk, ethers.provider)
      );
    }
  });

  it("list nft", async () => {
    const nftCa = testToken.address;
    if (!(await raffleProxy.isListed(nftCa))) {
      const listTx = await raffleProxy
        .connect(owner)
        ["listNft(address,address,uint8)"](nftCa, await owner.getAddress(), 100);
      await listTx.wait();

      const isListed = await raffleProxy.isListed(nftCa);
      expect(isListed).to.eq(true);
    }
  });

  it("mint nft", async () => {
    if ((await testToken.balanceOf(await seller.getAddress())).eq(BigNumber.from("0"))) {
      const tokenId = await testToken.totalSupply();
      const mintTx = await testToken.connect(seller).mint(tokenId);
      await mintTx.wait();
      expect(await testToken.ownerOf(tokenId)).to.equal(await seller.getAddress());
    }
  });

  it("register raffle", async () => {
    const sellerAddr = await seller.getAddress();
    const nftCa = testToken.address;
    const tokenId = await testToken.tokenOfOwnerByIndex(sellerAddr, 0);
    const ticketAmount = await raffleProxy.maxTicketAmount();
    const ticketPrice = await raffleProxy.minTicketPrice();
    // const day = 2;
    const minutes = 1; // > 0

    // approve token from seller to raffle sale proxy contract
    const approveTx = await testToken.connect(seller).approve(raffleProxy.address, tokenId);
    await approveTx.wait();
    console.log("approved");
    console.log(await testToken.getApproved(tokenId));

    // register raffle
    const registerTx = await raffleProxy
      .connect(seller)
      .registerRaffle(nftCa, tokenId, ticketAmount, ticketPrice, minutes);
    const registerReceipt = await registerTx.wait();
    registerReceipt.events?.forEach((event) => {
      raffleId = event.args?.raffleId.toString();
    });

    console.log("@@@@@@@@@@@@@ raffleId: ", raffleId);
    expect((await raffleProxy.raffles(raffleId)).seller).to.equal(sellerAddr);
  });

  it("buy tickets", async () => {
    // const raffleId = 0; // register raffle과 한큐에 진행하지 않으면 따로 입력해야함
    const buyAmount = BigNumber.from("300");
    const ticketPrice = (await raffleProxy.raffles(raffleId)).ticketPrice;

    const buyTx = await raffleProxy
      .connect(buyer1)
      .buyTickets(raffleId, buyAmount, { value: ticketPrice.mul(buyAmount) });
    const receipt = await buyTx.wait();
    receipt.events?.forEach((event) => {
      console.log(event.args?.raffleId);
    });
  });

  it("buy left overs", async () => {
    // const raffleId = // register raffle과 한큐에 진행하지 않으면 따로 입력해야함
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
  });

  it("choose winner", async () => {
    // const raffleId = 67; // register raffle과 동시에 진행하지 않으면 따로 입력해야함
    const randNum = Math.floor(Math.random() * 1_000_000);

    const gasEstimated = await raffleProxy.estimateGas.chooseWinner(raffleId, randNum);
    const chooseTx = await raffleProxy
      .connect(owner)
      .chooseWinner(raffleId, randNum, { gasLimit: gasEstimated.mul(15).div(10) }); // gasEstimated * 1.2
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

  it.skip("claim nft", async () => {
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

  it("clam balance", async () => {
    console.log("before balance: ", await seller.getBalance());
    const claimableBalance = raffleProxy.claimableBalance(await seller.getAddress());
    const tx = await raffleProxy.connect(seller).claimBalance(claimableBalance);
    tx.wait();
    console.log("after balance: ", await seller.getBalance());
  });

  it.skip("claim all nfts", async () => {
    // put buyer1 or buyer2 or buyer3
    const signer = buyer2;
    const signerAddr = await signer.getAddress();

    const before_nftsLength = await raffleProxy.getClaimableNftsLength(signerAddr);
    const before_claimableNfts = [];
    for (let i = 0; i < Number(before_nftsLength); i++) {
      before_claimableNfts.push((await raffleProxy.claimableNft(signerAddr, i)).raffleId);
    }
    console.log("before claimable nfts", before_claimableNfts);

    // claim all
    const claimAllTx = await raffleProxy.connect(signer).claimAllNfts();
    await claimAllTx.wait();

    const after_nftsLength = await raffleProxy.getClaimableNftsLength(signerAddr);
    const after_claimableNfts = [];
    for (let i = 0; i < Number(after_nftsLength); i++) {
      after_claimableNfts.push((await raffleProxy.claimableNft(signerAddr, i)).raffleId);
    }
    console.log("after claimable nfts", after_claimableNfts);
  });

  it.skip("엔에프티 클레임 리스트 한번 싹 정리하는거", async () => {
    const signers = [buyer1, buyer2, buyer3];

    for (const signer of signers) {
      const tx = await raffleProxy.connect(signer).claimAllNfts();
      await tx.wait();
    }
  });
});
