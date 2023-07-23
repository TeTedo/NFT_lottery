import { ethers, network, upgrades } from "hardhat";
import { Undefined } from "../../typechain-types/index";

async function main() {
  const MAX_TICKET_AMOUNT = 1_000;
  const MIN_TICKET_PRICE = ethers.utils.parseEther("0.0001");
  const COMMISSION = 100; // 0.1% 단위
  const COMMISSON_VAULt = "";

  const signer = (await ethers.getSigners())[0];
  const contractFactory = await ethers.getContractFactory("Undefined", signer);
  const raffleProxy = (await upgrades.deployProxy(
    contractFactory,
    [COMMISSION, MAX_TICKET_AMOUNT, MIN_TICKET_PRICE],
    {
      initializer: "initialize",
      unsafeAllow: ["constructor"],
    }
  )) as Undefined;
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

// 배포되는 순서
// 1. 로직 컨트렉트
// 2. 프록시 어드민
// 3. 프록시

// ** gas used **
// sepolia: {
//   logic_contract: 4,224,827,
//   proxy_admin_contract: 443,289,
//   proxy_contract: 707,599
// }
// baobab: {
//   logic_contract: 5,910,359 | 0.147758975 KLAY
//   proxy_admin_contract: 596,715 | 0.014917875 KLAY
//   proxy_contract: 750,751 | 1,055,787 | 0.026394675KLAY
// }
