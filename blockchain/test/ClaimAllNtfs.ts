import { BigNumber, Contract, Event, Signer } from "ethers";
import { ethers, network, upgrades } from "hardhat";
import { expect, assert } from "chai";
import {
  Undefined,
  TestToken,
  TestToken__factory,
  IUndefined,
  Undefined__factory,
} from "../typechain-types/index";
import { parseEther } from "ethers/lib/utils";
import { chains } from "../ common/config";

describe("ClaimAllNfts", () => {
  const MAX_TICKET_AMOUNT = 1_000;
  const MIN_TICKET_PRICE = ethers.utils.parseEther("0.0001");
  const FEE_NUMERATOR = 100; // 0.1% 단위
  const CLAIMABLE_NFTS_AMOUNT = 20;
  let raffleProxy: Undefined;
  let testToken: TestToken;
  let owner: Signer, seller: Signer, buyer1: Signer, buyer2: Signer, buyer3: Signer, vault: Signer;
  let claimer: Signer;
  let raffleId: string;
  let winnerAddr: string;

  before("setting", async () => {
    if (network.name === "hardhat") {
      [owner, seller, buyer1, buyer2, buyer3, vault] = await ethers.getSigners();

      // deploy raffle contract by proxy
      const raffleFactory = await ethers.getContractFactory("Undefined", owner);
      raffleProxy = (await upgrades.deployProxy(
        raffleFactory,
        [FEE_NUMERATOR, MAX_TICKET_AMOUNT, MIN_TICKET_PRICE, await owner.getAddress()],
        {
          initializer: "initialize",
          kind: "transparent",
          unsafeAllow: ["constructor"],
        }
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

  describe("list nft", async () => {
    it("", async () => {
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
  });

  describe("loop", async () => {
    beforeEach(`stop loop when claimable amount is more than${CLAIMABLE_NFTS_AMOUNT}`, async () => {
      if (winnerAddr) {
        const claimableNfts = await raffleProxy.getClaimableNftsLength(winnerAddr);
        if (claimableNfts.gte(BigNumber.from(CLAIMABLE_NFTS_AMOUNT))) {
          claimer = await ethers.getSigner(winnerAddr);
          assert(false, `${winnerAddr} got 5 claimable nfts`);
        }
      }
    });

    new Array(100).fill(0).forEach(() => {
      it("", async () => {
        // mint nft
        if ((await testToken.balanceOf(await seller.getAddress())).eq(BigNumber.from("0"))) {
          const tokenId = await testToken.totalSupply();
          const mintTx = await testToken.connect(seller).mint(tokenId);
          await mintTx.wait();
          expect(await testToken.ownerOf(tokenId)).to.equal(await seller.getAddress());
        }

        const sellerAddr = await seller.getAddress();
        const nftCa = testToken.address;
        const tokenId = await testToken.tokenOfOwnerByIndex(sellerAddr, 0);
        const ticketAmount = await raffleProxy.maxTicketAmount();
        const ticketPrice = await raffleProxy.minTicketPrice();
        // const day = 2;
        const minutes = 1; // > 0

        // approve
        const approveTx = await testToken.connect(seller).approve(raffleProxy.address, tokenId);
        await approveTx.wait();

        // register raffle
        const registerTx = await raffleProxy
          .connect(seller)
          .registerRaffle(nftCa, tokenId, ticketAmount, ticketPrice, minutes);
        const registerReceipt = await registerTx.wait();
        registerReceipt.events?.forEach((event) => {
          raffleId = event.args?.raffleId.toString();
        });

        expect((await raffleProxy.raffles(raffleId)).seller).to.equal(sellerAddr);

        // buy tickets
        const buyAmount = BigNumber.from("300");
        const buyTx = await raffleProxy
          .connect(buyer1)
          .buyTickets(raffleId, buyAmount, { value: ticketPrice.mul(buyAmount) });
        const receipt = await buyTx.wait();
        receipt.events?.forEach((event) => {});

        let leftTicketAmount = (await raffleProxy.raffles(raffleId)).leftTickets;
        const buyTx1 = await raffleProxy
          .connect(buyer2)
          .buyTickets(raffleId, leftTicketAmount / 2, {
            value: ticketPrice.mul(leftTicketAmount / 2),
          });
        await buyTx1.wait();
        leftTicketAmount = (await raffleProxy.raffles(raffleId)).leftTickets;
        const buyTx2 = await raffleProxy.connect(buyer3).buyTickets(raffleId, leftTicketAmount, {
          value: ticketPrice.mul(leftTicketAmount),
        });
        await buyTx2.wait();

        const randNum = Math.floor(Math.random() * 1_000_000);

        const gasEstimated = await raffleProxy.estimateGas.chooseWinner(raffleId, randNum);
        const chooseTx = await raffleProxy.connect(owner).chooseWinner(raffleId, randNum, {
          gasLimit: gasEstimated.mul(15).div(10),
        });
        const chooseReceipt = await chooseTx.wait();
        const events = chooseReceipt.events;
        let winnerTicketIndex = "";
        events?.forEach((event) => {
          winnerAddr = event.args?.winner;
          winnerTicketIndex = event.args?.winnerTicketIndex.toString();
        });
        const winnerTicketOwner = await raffleProxy.getTicketOwnerByIndex(
          raffleId,
          winnerTicketIndex
        );
        expect(winnerTicketOwner).to.equal(winnerAddr);
      });
    });
  });

  describe("claim", () => {
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

      const nftsLength2 = await raffleProxy.getClaimableNftsLength(winnerAddr);
      const claimableNfts2 = [];
      for (let i = 0; i < Number(nftsLength2); i++) {
        claimableNfts2.push(await raffleProxy.claimableNft(winnerAddr, i));
      }
      console.log("claimable nfts", claimableNfts2);
    });
    it("claim all nfts", async () => {
      //   const claimerAddr = await claimer.getAddress();
      const claimerAddr = winnerAddr;

      const before_nftsLength = await raffleProxy.getClaimableNftsLength(claimerAddr);
      const before_claimableNfts = [];
      for (let i = 0; i < Number(before_nftsLength); i++) {
        before_claimableNfts.push((await raffleProxy.claimableNft(claimerAddr, i)).raffleId);
      }
      console.log("before claimable nfts", before_claimableNfts);

      // claim all
      const claimAllTx = await raffleProxy.connect(claimer).claimAllNfts();
      await claimAllTx.wait();

      const after_nftsLength = await raffleProxy.getClaimableNftsLength(claimerAddr);
      const after_claimableNfts = [];
      for (let i = 0; i < Number(after_nftsLength); i++) {
        after_claimableNfts.push((await raffleProxy.claimableNft(claimerAddr, i)).raffleId);
      }
      console.log("after claimable nfts", after_claimableNfts);
    });
  });
});
