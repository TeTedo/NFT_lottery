import { config, ethers, network, upgrades } from 'hardhat';
import { RaffleSale, RaffleSale__factory, TestToken } from '../typechain-types/index';

async function main() {
  const MAX_TICKET_AMOUNT = 1_000;
  const MIN_TICKET_PRICE = ethers.utils.parseEther('1');
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

  console.log(`deployed at ${network.name} network CA: ${raffleSaleProxy.address}`);
}

main().catch(error => {
  console.log(error);
  process.exitCode = 1;
});
