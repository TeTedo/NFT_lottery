import React, { useEffect } from "react";
import { ChainID, chains } from "common/config/config";
import ethers from "ethers";
import { abi } from "../common/abi/ERC721Enumerable.json";

const useERC721 = (chainID: ChainID) => {
  class ERC721 {
    private static interface = new ethers.Interface(abi);
    private static raffleCa = chains[chainID].ca.raffle;

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
      signer: ethers.ethers.JsonRpcSigner,
      ca: string,
      tokenId: ethers.BigNumberish
    ): Promise<ethers.TransactionReceipt | null> {
      const data = this.interface.encodeFunctionData("approve", [this.raffleCa, tokenId]);
      const tx = await signer.sendTransaction({ to: ca, data });
      return await tx.wait();
    }
  }

  return ERC721;
};

export default useERC721;
