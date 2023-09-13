import { ethers } from "ethers";
import { abi } from "../abi/ERC721Enumerable.json";

export class ERC721 {
  private static interface = new ethers.Interface(abi);

  // 메서드 호출하는 곳에서 에러핸들링 필요

  static async tokenURI(
    provider: ethers.BrowserProvider,
    ca: string,
    tokenId: ethers.BigNumberish
  ): Promise<string> {
    const data = this.interface.encodeFunctionData("tokenURI", [tokenId]);
    const uri = await provider.call({ to: ca, data });
    return uri;
  }

  static async approve(
    tokenCa: string,
    tokenId: ethers.BigNumberish,
    raffleCa: string,
    signer: ethers.JsonRpcSigner
  ): Promise<ethers.TransactionReceipt | null> {
    const data = this.interface.encodeFunctionData("approve", [raffleCa, tokenId]);
    const tx = await signer.sendTransaction({ to: tokenCa, data });
    return await tx.wait();
  }
}
