import { config, ethers, network, upgrades } from 'hardhat';
import { RaffleSale, RaffleSale__factory, TestToken } from '../typechain-types/index';

async function main() {
  const MAX_TICKET_AMOUNT = 1_000;
  const MIN_TICKET_PRICE = ethers.utils.parseEther('0.0001');
  const COMMISSION_PERCENTAGE = 10;

  const signer = (await ethers.getSigners())[0];
  const contractFactory = await ethers.getContractFactory('RaffleSale', signer);
  const raffleSaleProxy = (await upgrades.deployProxy(
    contractFactory,
    [MAX_TICKET_AMOUNT, MIN_TICKET_PRICE, COMMISSION_PERCENTAGE],
    {
      initializer: 'initialize',
      unsafeAllow: ['constructor'],
    }
  )) as RaffleSale;
  await raffleSaleProxy.deployed();
  const deployTx = raffleSaleProxy.deployTransaction;
  const txReceipt = await signer.provider?.getTransactionReceipt(deployTx.hash);
  const gasUSed = txReceipt?.gasUsed;

  console.log(
    `deployed at ${network.name} network, Proxy_CA: ${raffleSaleProxy.address}, use ${gasUSed} gas`
  );
}

main().catch(error => {
  console.log(error);
  process.exitCode = 1;
});

// ** gas used **
// sepolia: {
//   logic_contract: 3,180,375,
//   proxy_admin_contract: 443,289,
//   proxy_contract: 750,751,
// }
