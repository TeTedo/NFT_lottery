import { ethers, network, upgrades } from "hardhat";
// import { proxies } from "../../.openzeppelin/sepolia.json";
import { proxies } from "../../.openzeppelin/unknown-1001.json"; // baobab

async function main() {
  const signer = (await ethers.getSigners())[0];
  const newImplement = await ethers.getContractFactory("Undefined", signer);
  const upgrade = await upgrades.upgradeProxy(proxies[0], newImplement, {
    unsafeAllow: ["constructor"],
  });
  const deployed = await upgrade.deployed();
  const txHash = deployed.deployTransaction.hash;
  const receipt = await signer.provider?.getTransactionReceipt(txHash);
  const gasUsed = receipt?.gasUsed;

  console.log("upgarade successfully done");
  console.log(
    `upgraded at ${network.name} network, Proxy_CA: ${deployed.address}, used ${gasUsed} gas`
  );
}

main().catch((error) => {
  console.log(error);
  process.exit(1);
});
