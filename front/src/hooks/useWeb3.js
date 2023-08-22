import { useState } from "react";
import Web3 from "web3";

// MetaMask가 설치되어 있으면 window.ethereum이 존재할 것입니다.
const web3 = window.ethereum ? new Web3(window.ethereum) : null;

function useConnectMetaMask() {
  const [accounts, setAccounts] = useState([]);
  const [isConnected, setIsConnected] = useState(false);

  async function connect() {
    try {
      if (web3) {
        const accs = await window.ethereum.request({
          method: "eth_requestAccounts",
        });
        setAccounts(accs);
        setIsConnected(true);
      } else {
        alert("Please install MetaMask!");
      }
    } catch (error) {
      console.error("Error on connecting to MetaMask:", error);
      setIsConnected(false);
    }
  }

  return { connect, accounts, isConnected };
}

export default useConnectMetaMask;
