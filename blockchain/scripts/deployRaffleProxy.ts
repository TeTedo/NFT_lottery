import { ethers, network, upgrades } from "hardhat";
import { Raffle } from "../typechain-types/index";

async function main() {
  const MAX_TICKET_AMOUNT = 1_000;
  const MIN_TICKET_PRICE = ethers.utils.parseEther("0.0001");
  const COMMISSION_PERCENTAGE = 10;

  const signer = (await ethers.getSigners())[0];
  const contractFactory = await ethers.getContractFactory("Raffle", signer);
  const raffleProxy = (await upgrades.deployProxy(
    contractFactory,
    [MAX_TICKET_AMOUNT, MIN_TICKET_PRICE, COMMISSION_PERCENTAGE],
    {
      initializer: "initialize",
      unsafeAllow: ["constructor"],
    }
  )) as Raffle;
  const deployed = await raffleProxy.deployed();
  const txHash = deployed.deployTransaction.hash;
  const txReceipt = await signer.provider?.getTransactionReceipt(txHash);
  const gasUsed = txReceipt?.gasUsed;

  console.log(
    `deployed at ${network.name} network, Proxy_CA: ${deployed.address}, use ${gasUsed} gas`
  );
}

main().catch((error) => {
  console.log(error);
  process.exit(1);
});

// ** gas used **
// sepolia: {
//   logic_contract: 3,798,979,
//   proxy_admin_contract: 443,289,
//   proxy_contract: 750,751 | 750,742,
// }
