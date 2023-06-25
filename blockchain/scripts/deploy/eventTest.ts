import { ethers } from "hardhat";

async function main() {
  const signer = (await ethers.getSigners())[0];
  const C = await ethers.getContractFactory("EventTest", signer);
  const c = await C.deploy();
  await c.deployed();

  console.log(c.address);
  // 0x381c7d85673f230de554097372e0FC4F14b5B0a6
}

main().catch((error) => {
  console.log(error);
});
