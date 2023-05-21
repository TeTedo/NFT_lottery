import { Contract, Event, Signer } from 'ethers';
import { ethers, upgrades } from 'hardhat';
import { expect } from 'chai';
import {
  RaffleSale,
  RaffleSale__factory,
  TestToken,
  TestToken__factory,
} from '../typechain-types/index';

describe('RaffleSale', () => {
  const MAX_TICKET_AMOUNT = 1_000;
  const MIN_TICKET_PRICE = ethers.utils.parseEther('1');
  const COMMISSION_PERCENTAGE = 10;
  let raffleSaleProxy: RaffleSale;
  let testToken: TestToken;
  let owner: Signer, seller: Signer, buyer1: Signer, buyer2: Signer, buyer3: Signer;

  before(async () => {
    [owner, seller, buyer1, buyer2, buyer3] = await ethers.getSigners();

    // deploy raffle contract by proxy
    const raffleSaleFactory = await ethers.getContractFactory('RaffleSale', owner);
    raffleSaleProxy = (await upgrades.deployProxy(
      raffleSaleFactory,
      [MAX_TICKET_AMOUNT, MIN_TICKET_PRICE, COMMISSION_PERCENTAGE],
      { initializer: 'initialize', kind: 'transparent', unsafeAllow: ['constructor'] }
    )) as RaffleSale;
    await raffleSaleProxy.deployed();

    // deploy token
    const testTokenFactory = await ethers.getContractFactory('TestToken');
    testToken = (await testTokenFactory.deploy('Test', 'TST')) as TestToken;
    await testToken.deployed();
  });

  it('mint token', async () => {
    const mintTx = await testToken.connect(seller).mint(1);
    await mintTx.wait();
    expect(await testToken.ownerOf(1)).to.equal(await seller.getAddress());
  });

  it('list nft', async () => {
    const nftCa = testToken.address;

    const listTx = await raffleSaleProxy.connect(owner).listNft(nftCa);
    await listTx.wait();

    const listedNft = await raffleSaleProxy.getListedNft();
    expect(listedNft).to.contains(nftCa);
  });

  it('register raffle', async () => {
    const sellerAddr = await seller.getAddress();
    const nftCa = testToken.address;
    const tokenId = testToken.tokenOfOwnerByIndex(sellerAddr, 0);
    const ticketAmount = 100;
    const ticketPrice = ethers.utils.parseEther('1');
    const day = 2;

    // approve token from seller to raffle sale proxy contract
    const approveTx = await testToken.connect(seller).approve(raffleSaleProxy.address, tokenId);
    await approveTx.wait();

    // register raffle
    const registerTx = await raffleSaleProxy
      .connect(seller)
      .registerRaffle(nftCa, tokenId, ticketAmount, ticketPrice, day);
    await registerTx.wait();

    expect((await raffleSaleProxy.getRaffleInfo(nftCa, tokenId)).seller).to.equal(sellerAddr);
  });

  it('buy tickets', async () => {
    const nftCa = testToken.address;
    const tokenId = testToken.tokenOfOwnerByIndex(raffleSaleProxy.address, 0);
    const buyAmount = 30;

    // buyTickets
    const ticketPrice = (await raffleSaleProxy.getRaffleInfo(nftCa, tokenId)).ticketPrice;
    const buyTx = await raffleSaleProxy
      .connect(buyer1)
      .buyTickets(nftCa, tokenId, buyAmount, { value: ticketPrice.mul(buyAmount) });
    await buyTx.wait();

    // console.log(await raffleSaleProxy.getRaffleInfo(nftCa, tokenId));
  });

  it('choose winner', async () => {
    const nftCa = testToken.address;
    const tokenId = testToken.tokenOfOwnerByIndex(raffleSaleProxy.address, 0);
    let leftTicketAmount = (await raffleSaleProxy.getRaffleInfo(nftCa, tokenId)).lefTicketAmount;

    // buyLeftOvers
    const ticketPrice = (await raffleSaleProxy.getRaffleInfo(nftCa, tokenId)).ticketPrice;
    const buyTx1 = await raffleSaleProxy
      .connect(buyer2)
      .buyTickets(nftCa, tokenId, leftTicketAmount.div(2), {
        value: ticketPrice.mul(leftTicketAmount.div(2)),
      });
    await buyTx1.wait();
    leftTicketAmount = (await raffleSaleProxy.getRaffleInfo(nftCa, tokenId)).lefTicketAmount;
    const buyTx2 = await raffleSaleProxy
      .connect(buyer3)
      .buyTickets(nftCa, tokenId, leftTicketAmount, { value: ticketPrice.mul(leftTicketAmount) });
    await buyTx2.wait();
    // console.log(await raffleSaleProxy.getRaffleInfo(nftCa, tokenId));

    const randNum = Math.floor(Math.random() * 1_000_000);
    const chooseTx = await raffleSaleProxy.connect(owner).chooseWinner(nftCa, tokenId, randNum);
    const chooseReceipt = await chooseTx.wait();
    const events = chooseReceipt.events;
    events?.forEach(event => {
      console.log('winner', event.args?.at(0));
    });
  });
});
