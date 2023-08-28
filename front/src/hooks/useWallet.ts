import { Provider, ethers, formatEther, getAddress } from "ethers";
import { useEffect, useState } from "react";

declare global {
  interface Window {
    ethereum: any; // 간단한 타입 정의
  }
}
type UseMetaMaskResult = {
  account: string;
  balance: string;
  chainId: number;
  provider: Provider | null;
  connect: () => Promise<void>;
};
const useMetaMask = (): UseMetaMaskResult => {
  const [account, setAccount] = useState<string>("");
  const [balance, setBalance] = useState<string>("0");
  const [chainId, setChainId] = useState<number>(0);
  const [provider, setProvider] = useState<Provider | null>(null);

  useEffect(() => {
    if (!window.ethereum) {
      alert("Install MetaMastk");
      return;
    }

    window.ethereum.on("accountsChanged", async (accounts: string[]) => {
      if (accounts.length === 0) {
        setAccount("");
        setBalance("0");
      } else {
        setAccount(getAddress(accounts[0]));
        const balance = await window.ethereum.request({
          method: "eth_getBalance",
          params: [accounts[0]],
        });
        setBalance(formatEther(balance));
      }
    });

    window.ethereum.on("disconnect", (error: any) => {});

    window.ethereum.on("chainChanged", async (chainId: string) => {
      await init();
    });

    return () => {
      delete window.ethereum._events;
    };
  }, []);

  async function init() {
    const chainId = await window.ethereum.request({ method: "eth_chainId" });
    const provider = new ethers.BrowserProvider(window.ethereum);
    setProvider(provider);
    const parsedChainId = parseInt(chainId, 16);
    setChainId(parsedChainId);
    const accounts = await window.ethereum.request({ method: "eth_accounts" });
    if (accounts.length === 0) {
      setAccount("");
      setBalance("0");
    } else {
      setAccount(getAddress(accounts[0]));
      const balance = await window.ethereum.request({
        method: "eth_getBalance",
        params: [accounts[0]],
      });
      setBalance(formatEther(balance));
    }
  }

  async function connect() {
    if (!window.ethereum) {
      alert("Install MetaMastk");
      return;
    }
    try {
      await window.ethereum.request({ method: "eth_requestAccounts" });
      await init();
    } catch (error: any) {
      if (error.code === 4001) alert("connect to MetaMask");
      else alert(error);
    }
  }

  return { account, balance, chainId, provider, connect };
};

export default useMetaMask;
