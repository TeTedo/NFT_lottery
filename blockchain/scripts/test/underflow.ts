import { ethers } from "hardhat";

async function main() {
  const provider = ethers.provider;
  const signer = (await ethers.getSigners())[0];
  const contractFactory = await ethers.getContractFactory("Test", signer);
  const contract = await contractFactory.deploy();
  await contract.deployed();

  const result = await contract.test();
  console.log(result);
}

main().catch((error) => {
  console.log(error);
});
