import { ethers, network } from "hardhat";

async function main() {
  const signer = (await ethers.getSigners())[0];
  const factory = await ethers.getContractFactory("TestToken", signer);
  const testToken = await factory.deploy("TestToken", "TTK");
  await testToken.deployed();
  const deployTx = testToken.deployTransaction;
  const txRecipt = await signer.provider?.getTransactionReceipt(deployTx.hash);
  const gasUsed = txRecipt?.gasUsed;

  console.log(
    `deployed at ${network.name} network, token_CA: ${testToken.address}, used ${gasUsed} gas`
  );
}

main().catch((error) => {
  console.log(error);
  process.exitCode = 1;
});

// gas used
// sepolia : 2,707,786
// ca : 0xbE43D87cA1717e4F3e30a8c1791144c7B85F0f0D

// baobob
// ca : 0xBCCE13dEA546B1c574b74241d553D6366F6c77F2
