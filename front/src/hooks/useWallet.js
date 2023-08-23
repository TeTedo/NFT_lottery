import { Provider, ethers, formatEther, getAddress } from "ethers";
import React, { useEffect, useState } from "react";

const useMetaMask = () => {
  const [account, setAccount] = useState("");
  const [balance, setBalance] = useState("0");
  const [chainId, setChainId] = useState(0);
  const [provider, setProvider] = useState(null);

  useEffect(() => {
    if (!window.ethereum) {
      alert("Install MetaMastk");
      return;
    }

    window.ethereum.on("accountsChanged", async (accounts) => {
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

    window.ethereum.on("disconnect", (error) => {});

    window.ethereum.on("chainChanged", async (chainId) => {
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
    } catch (error) {
      if (error.code === 4001) alert("connect to MetaMask");
      else alert(error);
    }
  }

  return { account, balance, chainId, provider, connect };
};

export default useMetaMask;
