import { BigNumberish, ethers } from "ethers";

export class MyNFT {
  private chunkedListedNfts: string[][];

  /**
   *
   * @param undefinedUtil undefined util contract instance
   * @param listedNfts array of listed nfts contract address
   * @param chunkSize chunk size for batch call on contract
   */
  constructor(
    private undefinedUtil: ethers.Contract,
    private listedNfts: string[],
    chunkSize: number = 20
  ) {
    this.chunkedListedNfts = this._split(chunkSize);
  }

  async nftsOf(address: string) {
    const promises = this.chunkedListedNfts.map(() =>
      this.undefinedUtil.getTokensOfOwnerWithListedNft(this.listedNfts, address)
    );

    const nfts = (await Promise.all(promises)).reduce((acc, cur) => (acc = acc.concat(cur)), []);
    return nfts;
  }

  bindURI(nftsOfOwner: any[]): Promise<
    {
      tokenCa: string;
      tokenId: BigNumberish;
      tokenUri: string;
    }[]
  > {
    const promises = nftsOfOwner.map(async (nft) => {
      const tokenUri = await this.undefinedUtil.getTokenUri(nft.tokenCa, nft.tokenId);
      return { tokenCa: nft.tokenCa, tokenId: nft.tokenId, tokenUri };
    });

    return Promise.all(promises);
  }

  private _split(chunkSize: number = 20) {
    if (chunkSize <= 0) chunkSize = 20;
    const res = [];

    for (let i = 0; i < this.listedNfts.length; i += chunkSize) {
      const chunk = this.listedNfts.slice(i, i + chunkSize);
      res.push(chunk);
    }

    return res;
  }
}
